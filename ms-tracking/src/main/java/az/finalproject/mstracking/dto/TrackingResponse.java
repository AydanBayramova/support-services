package az.finalproject.mstracking.dto;


import java.time.LocalDateTime;

public record TrackingResponse(
        Double latitude,
        Double longitude,
        LocalDateTime recordedAt
) {}
