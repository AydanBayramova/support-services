package az.finalproject.mspayment.repository;

import az.finalproject.mspayment.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public interface PaymentRepository extends JpaRepository<Payment, UUID> {

    boolean existsByOrderId(UUID orderId);

    List<Payment> findAllByCourierIdOrderByCreatedAtDesc(UUID courierId);

    List<Payment> findAllByCourierIdAndCreatedAtAfter(UUID courierId, LocalDateTime date);
}
