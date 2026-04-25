package com.mavi.themaurus.controller;

import com.mavi.themaurus.service.TmdbService;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
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

        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(tmdbService.findByImdbId(imdbId));
    }

    @GetMapping("/search")
    public ResponseEntity<Mono<String>> searchMovies(@RequestParam String title, @RequestParam Integer page) {

        if (title == null || title.isBlank()) {
            return ResponseEntity.badRequest().build();
        }

        if (page == null || page <= 0) {
            page = 1;
        }

        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(tmdbService.search(title, page));
    }
}
