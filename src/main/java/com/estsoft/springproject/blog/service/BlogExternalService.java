package com.estsoft.springproject.blog.service;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class BlogExternalService {
    private final WebClient webClient;

    public BlogExternalService(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl("https://jsonplaceholder.typicode.com/posts").build();
    }

    public Mono<String> getDataFromApi() {
        return this.webClient.get()
                .uri("/data")
                .retrieve()
                .bodyToMono(String.class);
    }

    public Mono<String> postDataToApi(String data) {
        return this.webClient.post()
                .uri("/submit")
                .header("Content-Type", "application/json")
                .bodyValue(data)
                .retrieve()
                .bodyToMono(String.class);
    }
}
