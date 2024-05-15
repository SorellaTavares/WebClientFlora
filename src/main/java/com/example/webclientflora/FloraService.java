package com.example.webclientflora;

import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

public class FloraService {
    static WebClient client = WebClient.create("http://localhost:8080");

    public static void createFlora(String name, String sciName) {
        Flora flora = new Flora(name, sciName);

        Mono<String> result = client.post()
                .uri("/rs/florapost/{name}/{sciname}", name, sciName)
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(flora))
                .retrieve()
                .bodyToMono(String.class);
        
        result.subscribe(response -> System.out.println("Response: " + response),
                error -> {
            System.err.println("Error: " + error);
                }
        );

    }

    static void updateFlora(String name, String newName, String newSciName){
        String uri = "/rs/floraput/{name}/{sciName}";

        uri = uri.replace("{name}", name).replace("{sciName}", newSciName);

        Flora updatedFlora = new Flora(newName ,newSciName);

        Mono<String> result = client.put()
                .uri(uri)
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromValue(updatedFlora))
                .retrieve()
                .bodyToMono(String.class);

        result.subscribe(response -> System.out.println("Response: " + response),
                error -> {
            System.err.println("Error: " + error);
                }
        );

    }

    static void deleteFlora(String name){
        Mono<String> result = client.delete()
                .uri("/rs/floradelete/{name}", name)
                .retrieve()
                .bodyToMono(String.class);

        result.subscribe(
                response -> System.out.println("Response: " + response),
                error -> System.err.println("Error: " + error)

        );

    }
}
