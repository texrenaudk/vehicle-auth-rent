package com.renaudk.controller;


import com.renaudk.exception.CustomIncommingException;
import com.renaudk.exception.ErrorResponse;
import com.renaudk.model.User;
import com.renaudk.model.UserDto;
import com.renaudk.proxy.UserProxy;
import lombok.RequiredArgsConstructor;
import org.antlr.v4.runtime.misc.MultiMap;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/v1/users")
@RequiredArgsConstructor
public class GatewayUserController {
    private final UserProxy userProxy;

    @GetMapping
    public Mono<User[]> getUsers(){
        return userProxy.getAllUsers();
    }

    @PostMapping
    public Mono<User> createUser(@RequestBody UserDto userDto){
       return userProxy.createUser(userDto);
    }

    @DeleteMapping("/{userId}")
    public Mono<User> deleteUser(@PathVariable Long userId){
        return userProxy.deleteUser(userId);
    }


    @PatchMapping("/{userId}")
    // Le type de retour est maintenant propre, Mono<ResponseEntity<User>>, 
    // car le cas d'erreur est géré globalement.
    public Mono<ResponseEntity<User>> updateUser(
            @PathVariable("userId") Long userId,
            @RequestBody UserDto userDto) {

        return handleProxyError(userProxy.updateUser(userId, userDto))
                // En cas de succès, on enveloppe le User dans un 200 OK.
                .map(ResponseEntity::ok);

        // AUCUN .onErrorResume ici, tout est géré par la fonction handleProxyError 
        // et le GlobalErrorWebFluxHandler.
    }

    private <T> Mono<T> handleProxyError(Mono<T> proxyMono) {
        return proxyMono.onErrorMap(WebClientResponseException.class, ex -> {
            HttpStatus status = HttpStatus.resolve(ex.getStatusCode().value());

            // Lancer l'exception personnalisée pour que le gestionnaire global la prenne en charge.
            return new CustomIncommingException(
                    status != null ? status : HttpStatus.INTERNAL_SERVER_ERROR,
                    ex.getResponseBodyAsString() ,
                    "Erreur lors de la communication avec le service utilisateur."
            );
        });
    }

   /* @PatchMapping("/{userId}")
    public Mono<ResponseEntity<User>> updateUser(@PathVariable Long userId, @RequestBody UserDto userDto){

        return userProxy.updateUser(userId, userDto)
                // 1. Si le proxy réussit, mappez l'utilisateur au 200 OK.
                .map(ResponseEntity::ok)

                // 2. Gestion des exceptions lancées par le UserProxy
                .onErrorResume(RuntimeException.class, error -> {
                    String message = error.getMessage();

                    if (message != null && message.startsWith("Error from User Service:")) {
                        String statusPart = message.substring("Error from User Service: ".length()).trim();
                        try {
                            // On extrait la partie numérique ("500") et on la convertit en entier
                            int statusCode = Integer.parseInt(statusPart.split(" ")[0]);

                            // On utilise HttpStatus.resolve() qui prend un code numérique et retourne l'HttpStatus correspondant
                            HttpStatus status = HttpStatus.resolve(statusCode);

                            if (status != null) {
                                // Log de l'erreur interceptée
                                System.err.println("Successfully intercepted error from upstream service: " + status.value() + " for user " + userId);

                                // Renvoie une ResponseEntity avec le statut d'erreur du service utilisateur
                                return Mono.just(ResponseEntity.status(status).build());
                            }

                            // Si resolve() ne trouve rien (ce qui est rare pour un code HTTP)
                            System.err.println("Could not resolve HttpStatus for code: " + statusCode);
                            return Mono.just(ResponseEntity.status(HttpStatus.BAD_GATEWAY).build());

                        } catch (NumberFormatException e) {
                            // Si la première partie n'est pas un nombre
                            System.err.println("Could not parse status code from proxy error message: " + message);
                            return Mono.just(ResponseEntity.status(HttpStatus.BAD_GATEWAY).build());
                        }
                    }

                    // Pour toute autre RuntimeException inattendue
                    System.err.println("Unexpected RuntimeException in controller: " + error.getMessage());
                    return Mono.just(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build());
                });
    }   */
}
