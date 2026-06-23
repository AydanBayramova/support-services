package az.finalproject.msrating.dto;

import java.util.UUID;

public record RatingResponse(
        UUID targetId,
        Double averageScore,
        Long totalReviews
) {}