package az.finalproject.mspayment.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(WalletNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handleWalletNotFound(WalletNotFoundException ex) {
        log.error("Wallet not found: {}", ex.getMessage());
        return buildErrorResponse("WALLET_NOT_FOUND", ex.getMessage());
    }


    @ExceptionHandler(InsufficientBalanceException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleInsufficientBalance(InsufficientBalanceException ex) {
        log.error("Insufficient balance error: {}", ex.getMessage());
        return buildErrorResponse("INSUFFICIENT_BALANCE", ex.getMessage());
    }

    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponse handleGeneralException(Exception ex) {
        log.error("Unexpected system error: ", ex);
        return buildErrorResponse("INTERNAL_SERVER_ERROR", "An unexpected error occurred in payment system.");
    }


    private ErrorResponse buildErrorResponse(String code, String message) {
        return ErrorResponse.builder()
                .errorCode(code)
                .message(message)
                .timestamp(LocalDateTime.now())
                .build();
    }
}