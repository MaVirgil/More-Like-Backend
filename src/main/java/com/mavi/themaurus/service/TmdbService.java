package com.mavi.themaurus.service;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
@Getter
@Setter
public class TmdbService {

    @Value("${tmdb-key}")
    private String TMDB_KEY;

    private final WebClient webClient;

    public TmdbService(WebClient.Builder builder) {
        System.out.println("building webclient with api key: ");
        this.webClient = builder.baseUrl("https://api.themoviedb.org/3/").build();
    }

    public Mono<String> findMovieByImdbId(String imdbId) {

        String request = "/find/" + imdbId + "?external_source=imdb_id&language=en-US&api_key=" + TMDB_KEY;
        return webClient.get()
                .uri(request)
                .header("accept: application/json")
                .retrieve()
                .bodyToMono(String.class);
    }

    public Mono<String> searchMovies(String query, int page) {

        page = Math.max(page, 1);

        String request = "/search/movie?query=" + query + "&include_adult=false&language=en-US&page=" + page + "&api_key=" + TMDB_KEY;

        System.out.println("Executing get request: " + request);

        return webClient.get()
                .uri(request)
                .header("accept: application/json")
                .retrieve()
                .bodyToMono(String.class);
    }
}
