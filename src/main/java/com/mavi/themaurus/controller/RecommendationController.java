package com.mavi.themaurus.controller;

import com.mavi.themaurus.service.OpenAiService;
import com.mavi.themaurus.service.RateLimiterService;
import io.github.bucket4j.Bucket;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin("*")
@RequestMapping("/recommend")
@AllArgsConstructor
public class RecommendationController {

    private final OpenAiService openAiService;

    private final RateLimiterService rateLimiterService;

    @GetMapping("")
    public ResponseEntity<String> getRecommendedMovies(@RequestParam String query, HttpServletRequest request) {

        if (query == null || query.isBlank()) {
            return ResponseEntity.badRequest().build();
        }

        //rate limiting check
        String ip = request.getRemoteAddr();

        Bucket bucket = rateLimiterService.resolveBucket(ip);

        if (!bucket.tryConsume(1)) {
            return ResponseEntity.status(HttpStatus.TOO_MANY_REQUESTS).build();
        }

        return ResponseEntity.ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(openAiService.getResponse(query));
    }
}
