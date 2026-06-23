package az.finalproject.mspayment.dto;

import java.math.BigDecimal;
import java.util.UUID;

public record WalletResponse(
        UUID courierId,
        BigDecimal balance,
        BigDecimal totalTurnover
) {}
