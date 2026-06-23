package az.finalproject.msrating.client;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public record OrderResponse(
        UUID id,
        String orderNumber,
        UUID customerId,
        UUID merchantId,
        UUID courierId,
        BigDecimal totalAmount,
        BigDecimal deliveryFee,
        String status,
        String deliveryAddressSnapshot,
        LocalDateTime createdAt
) {}
