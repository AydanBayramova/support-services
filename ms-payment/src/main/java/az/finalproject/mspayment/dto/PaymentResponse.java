package az.finalproject.mspayment.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public record PaymentResponse(
        UUID id,
        UUID orderId,
        BigDecimal courierEarning,
        String status,
        LocalDateTime createdAt
) {}
