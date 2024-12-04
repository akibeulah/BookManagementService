package com.beulah.bookmanagementweb.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Map;
import java.util.Optional;

@Service
public class BookService {
    private final WebClient webClient;

    public BookService(@Value("${api.base.url}") String baseUrl) {
        this.webClient = WebClient.create(baseUrl);
    }

    public Mono<Map> getBooks(String query, Integer page, Integer perPage) {
        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/books")
                        .queryParamIfPresent("query", Optional.ofNullable(query))
                        .queryParamIfPresent("page", Optional.ofNullable(page))
                        .queryParamIfPresent("perPage", Optional.ofNullable(perPage))
                        .build())
                .retrieve()
                .bodyToMono(Map.class);
    }

    public Mono<Map> getBook(Long id) {
        return webClient.get()
                .uri("/books/" + id)
                .retrieve()
                .bodyToMono(Map.class);
    }

    public Mono<Map> createBook(Map<String, Object> book) {
        return webClient.post()
                .uri("/books")
                .bodyValue(book)
                .retrieve()
                .bodyToMono(Map.class);
    }

    public Mono<Map> updateBook(Long id, Map<String, Object> book) {
        return webClient.put()
                .uri("/books/" + id)
                .bodyValue(book)
                .retrieve()
                .bodyToMono(Map.class);
    }

    public Mono<Void> deleteBook(Long id) {
        return webClient.delete()
                .uri("/books/" + id)
                .retrieve()
                .bodyToMono(Void.class);
    }
}
