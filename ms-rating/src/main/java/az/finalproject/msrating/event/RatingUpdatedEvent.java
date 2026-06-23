package az.finalproject.msrating.event;

import java.util.UUID;

public record RatingUpdatedEvent(
        UUID targetId,
        String targetType,
        Double newAverageScore,
        Long reviewCount
) {}
