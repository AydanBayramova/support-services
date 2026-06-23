package az.finalproject.mspayment.controller;

import az.finalproject.mspayment.dto.WalletResponse;
import az.finalproject.mspayment.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/payments")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;

    @GetMapping("/wallets/{courierId}")
    public WalletResponse getWallet(@PathVariable UUID courierId) {
        return paymentService.getCourierWallet(courierId);
    }

    @GetMapping("/wallets/{courierId}/daily")
    public BigDecimal getDailyEarnings(@PathVariable UUID courierId) {
        return paymentService.getDailyEarnings(courierId);
    }

    @PostMapping("/wallets/{courierId}/withdraw")
    public void withdraw(@PathVariable UUID courierId, @RequestParam BigDecimal amount) {
        paymentService.withdrawMoney(courierId, amount);
    }

    @GetMapping("/system/turnover")
    public BigDecimal getTotalSystemTurnover() {
        return paymentService.getTotalSystemTurnover();
    }
}