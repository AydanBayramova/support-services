package az.finalproject.mspayment.repository;

import az.finalproject.mspayment.entity.CourierWallet;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface CourierWalletRepository extends JpaRepository<CourierWallet, UUID> {
    Optional<CourierWallet> findByCourierId(UUID courierId);

}