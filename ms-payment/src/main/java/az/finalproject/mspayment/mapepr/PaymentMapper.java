package az.finalproject.mspayment.mapepr;

import az.finalproject.mspayment.dto.PaymentResponse;
import az.finalproject.mspayment.dto.WalletResponse;
import az.finalproject.mspayment.entity.CourierWallet;
import az.finalproject.mspayment.entity.Payment;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PaymentMapper {
    WalletResponse toWalletResponse(CourierWallet wallet);
    PaymentResponse toPaymentResponse(Payment payment);
    List<PaymentResponse> toPaymentResponseList(List<Payment> payments);
}
