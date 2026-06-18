package az.finalproject.mspayment.exception;


import lombok.Builder;
import java.time.LocalDateTime;

@Builder
public record ErrorResponse(
        String errorCode,
        String message,
        LocalDateTime timestamp
) {}