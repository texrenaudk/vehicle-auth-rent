package com.renaudk.proxy;


import com.renaudk.model.User;
import com.renaudk.model.UserDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Map;

@Component
public class UserProxy {

    private final WebClient webclient;
    private final String url;

    public UserProxy(WebClient webclient,@Value("${USER_SERVICE_URL}") String url) {
        this.webclient = webclient;
        this.url = url;
    }

    public Mono<User[]> getAllUsers(){
       return  webclient.get()
               .uri(url + "users")
               .retrieve()
               .bodyToMono(User[].class)
               //.doOnSuccess(user -> auditService.auditAction("USERS_READ", "Success"))  lorsqu'on veut auditer à travers kafka
        //     .doOnError(error -> auditService.auditAction("USERS_READ",  "Failed: " + error.getMessage())); lorsqu'on doit gerer en meme temps les erreurs
                      ;
    }
    public Mono<User> createUser(UserDto userDto){
        return webclient.post()
                .uri(url + "users")
                .bodyValue(userDto)
                .retrieve()
                .bodyToMono(User.class);
    }
    public Mono<User> deleteUser(Long id){
        return webclient.delete()
                .uri(url + "users/{id}", Map.of("id", id))
                .retrieve()
                .onStatus(status -> status.isError(), clientResponse ->
                        Mono.error(new RuntimeException("Error from User Service: " + clientResponse.statusCode()))
                )
                .bodyToMono(User.class);
    }
    public Mono<User> updateUser(Long id, UserDto userDto){
        return webclient.patch()
                .uri(uriBuilder -> uriBuilder.path("users/{id}").build(id))
                .bodyValue(userDto)
                .retrieve()
//                .onStatus(status -> status.isError(), clientResponse ->
//                        Mono.error(new RuntimeException("Error from User Service: " + clientResponse.statusCode()))
//                )
                .bodyToMono(User.class);
    }

}
