package az.finalproject.msrating.controller;


import az.finalproject.msrating.dto.RatingRequest;
import az.finalproject.msrating.dto.RatingResponse;
import az.finalproject.msrating.service.RatingService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/ratings")
@RequiredArgsConstructor
public class RatingController {

    private final RatingService ratingService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void addRating(@Valid @RequestBody RatingRequest request) {
        ratingService.addRating(request);
    }

    @GetMapping("/target/{targetId}")
    public RatingResponse getTargetRating(@PathVariable UUID targetId) {
        return ratingService.getTargetRating(targetId);
    }
}
