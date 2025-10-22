package com.renaudk.proxy;

import com.renaudk.exception.CustomIncommingException;
import com.renaudk.model.User;
import com.renaudk.model.Vehicle;
import com.renaudk.model.VehicleDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.Map;
import java.util.Optional;

@Component
public class VehicleProxy {
    private final WebClient webClient;
    private final String url;

    public VehicleProxy(WebClient webClient, @Value("${VEHICLE_SERVICE_URL}") String url) {
        this.webClient = webClient;
        this.url = url;
    }

    public Mono<Vehicle[]> getAllVehicle() {
        return webClient.get()
                .uri(url + "vehicles")
                .retrieve()
                .bodyToMono(Vehicle[].class)
                .transform(this::handleProxyError);
    }

    public Mono<Boolean> isVehicleExist(String vehicleId) {
        return getAllVehicle()
                .flatMap(vehicles -> {
                    boolean exists = Arrays.stream(vehicles)
                            .anyMatch(vehicle -> vehicle.getId().equals(vehicleId));
                    return Mono.just(exists);
                })
                .transform(this::handleProxyError);
    }

    public Mono<Vehicle> AssociationUserVehicle(String vehicleId, Long userId) {
        return isVehicleExist(vehicleId)
                .flatMap(exists -> {
                    if (exists) {
                        return webClient.post()
                                .uri(url + "{vehicleId}/users/{userId}", Map.of("vehicleId", vehicleId, "userId", userId))
                                .retrieve()
                                .bodyToMono(Vehicle.class)
                                .transform(this::handleProxyError);
                    } else {
                        return Mono.error(new CustomIncommingException(
                                HttpStatus.NOT_FOUND,
                                "Véhicule non trouvé",
                                "Le véhicule avec l'ID " + vehicleId + " n'existe pas"
                        ));
                    }
                });
    }

    public Mono<Vehicle> createVehicle(VehicleDto vehicleDto) {
        return webClient.post()
                .uri(url + "vehicles")
                .bodyValue(vehicleDto)
                .retrieve()
                .bodyToMono(Vehicle.class)
                .transform(this::handleProxyError);
    }

    private <T> Mono<T> handleProxyError(Mono<T> proxyMono) {
        return proxyMono.onErrorMap(WebClientResponseException.class, ex -> {
            HttpStatus status = HttpStatus.resolve(ex.getStatusCode().value());
            return new CustomIncommingException(
                    status != null ? status : HttpStatus.INTERNAL_SERVER_ERROR,
                    ex.getResponseBodyAsString(),
                    "Erreur lors de la communication avec le service utilisateur."
            );
        });
    }
}