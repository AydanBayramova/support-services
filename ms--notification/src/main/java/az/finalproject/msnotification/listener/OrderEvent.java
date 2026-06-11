package az.finalproject.msnotification.listener;


import java.util.UUID;

public record OrderEvent(
        UUID orderId,
        UUID customerId,
        UUID courierId,
        String status
) {}