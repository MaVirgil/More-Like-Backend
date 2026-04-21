package com.mavi.themaurus.controller;

import com.mavi.themaurus.service.TmdbService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/movie")
@AllArgsConstructor
@CrossOrigin("*")
public class MovieController {

    private final TmdbService tmdbService;

    @GetMapping("/find/{imdbId}")
    public ResponseEntity<Mono<String>> findMovie(@PathVariable String imdbId) {

        if (imdbId == null || imdbId.isBlank()) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(tmdbService.findMovieByImdbId(imdbId));
    }

    @GetMapping("/search")
    public ResponseEntity<Mono<String>> searchMovies(@RequestParam String query, @RequestParam Integer page) {

        if (query == null || query.isBlank()) {
            return ResponseEntity.badRequest().build();
        }

        if (page == null || page <= 0) {
            page = 1;
        }

        return ResponseEntity.ok(tmdbService.searchMovies(query, page));
    }
}
