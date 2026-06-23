package az.finalproject.mstracking.event;

import java.util.UUID;

public record CourierLocationEvent(
        UUID orderId,
        UUID courierId,
        Double latitude,
        Double longitude
) {}
