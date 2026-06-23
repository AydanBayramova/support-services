package az.finalproject.mspayment.service;

import az.finalproject.mspayment.dto.PaymentResponse;
import az.finalproject.mspayment.dto.WalletResponse;
import az.finalproject.mspayment.entity.CourierWallet;
import az.finalproject.mspayment.entity.Payment;
import az.finalproject.mspayment.enums.PaymentStatus;
import az.finalproject.mspayment.event.OrderFinishedEvent;
import az.finalproject.mspayment.exception.InsufficientBalanceException;
import az.finalproject.mspayment.exception.WalletNotFoundException;
import az.finalproject.mspayment.mapepr.PaymentMapper;
import az.finalproject.mspayment.repository.CourierWalletRepository;
import az.finalproject.mspayment.repository.PaymentRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class PaymentService {
    private final CourierWalletRepository walletRepository;
    private final PaymentRepository paymentRepository;
    private final PaymentMapper paymentMapper;

    private static final BigDecimal COMMISSION_RATE = new BigDecimal("0.80");

    public void processCourierPayment(OrderFinishedEvent event) {
        if (paymentRepository.existsByOrderId(event.orderId())) {
            log.warn("Payment already processed for order: {}. Skipping.", event.orderId());

        }
        CourierWallet courierWallet = walletRepository.findByCourierId(event.courierId())
                .orElseGet(() -> createInitialWallet(event.courierId()));
        BigDecimal courierEarning = event.deliveryFee().multiply(COMMISSION_RATE)
                .setScale(2, RoundingMode.HALF_UP);

        courierWallet.setBalance(courierWallet.getBalance().add(courierEarning));
        courierWallet.setTotalTurnover(courierWallet.getTotalTurnover().add(courierEarning));
        walletRepository.save(courierWallet);

        Payment payment = Payment.builder()
                .orderId(event.orderId())
                .courierId(event.courierId())
                .totalAmount(event.totalAmount())
                .courierEarning(courierEarning)
                .status(PaymentStatus.COMPLETED)
                .build();

        paymentRepository.save(payment);
        log.info("Successfully processed payment for courier {}. Earning: {} AZN",
                event.courierId(), courierEarning);

    }

    public CourierWallet createInitialWallet(UUID courierId) {
        log.info("Creating first-time wallet for courier: {}", courierId);
        return CourierWallet.builder()
                .courierId(courierId)
                .balance(BigDecimal.ZERO)
                .totalTurnover(BigDecimal.ZERO)
                .build();
    }


    public WalletResponse getCourierWallet(UUID courierId) {
        CourierWallet wallet = walletRepository.findByCourierId(courierId)
                .orElseThrow(() -> new WalletNotFoundException("Wallet not found for courier: " + courierId));
        return paymentMapper.toWalletResponse(wallet);
    }

    public BigDecimal getDailyEarnings(UUID courierId) {
        LocalDateTime startOfDay = LocalDateTime.now().with(LocalDateTime.MIN);
        return paymentRepository.findAllByCourierIdAndCreatedAtAfter(courierId, startOfDay)
                .stream()
                .map(Payment::getCourierEarning)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    @Transactional
    public void withdrawMoney(UUID courierId, BigDecimal amount) {
        CourierWallet wallet = walletRepository.findByCourierId(courierId)
                .orElseThrow(() -> new WalletNotFoundException("Wallet not found"));

        if (wallet.getBalance().compareTo(amount) < 0) {
            throw new InsufficientBalanceException("Not enough balance!");
        }

        wallet.setBalance(wallet.getBalance().subtract(amount));
        walletRepository.save(wallet);
        log.info("Courier {} withdrew {} AZN", courierId, amount);
    }

    public BigDecimal getTotalSystemTurnover() {
        return paymentRepository.findAll().stream()
                .map(Payment::getTotalAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
}
