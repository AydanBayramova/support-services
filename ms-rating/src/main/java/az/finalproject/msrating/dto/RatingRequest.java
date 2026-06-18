package az.finalproject.msrating.dto;

import az.finalproject.msrating.enums.RatingTarget;

import java.util.UUID;

public record RatingRequest(
        UUID orderId,
        UUID customerId,
        UUID targetId,
        RatingTarget targetType,
        Integer score,
        String comment
) {}
