package com.mavi.themaurus.controller;

import com.mavi.themaurus.service.OpenAiService;
import com.openai.models.beta.realtime.ResponseCreateEvent;
import com.openai.models.responses.Response;
import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin("*")
@RequestMapping("/recommend")
@AllArgsConstructor
public class RecommendationController {

    private final OpenAiService service;

    @GetMapping("")
    public ResponseEntity<String> getRecommendedMovies(@RequestParam String query) {
        System.out.println("Calling AI service for recommendation for movie: " + query);

        if (query == null || query.isBlank()) {
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(service.getResponse(query));
    }
}
