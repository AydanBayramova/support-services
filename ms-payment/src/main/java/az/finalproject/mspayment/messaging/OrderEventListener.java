package az.finalproject.mspayment.messaging;

import az.finalproject.mspayment.event.OrderFinishedEvent;
import az.finalproject.mspayment.service.PaymentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class OrderEventListener {

    private final PaymentService paymentService;

    @RabbitListener(queues = "payment-service-queue")
    public void handleOrderFinishedEvent(OrderFinishedEvent event) {
        log.info("Received payment event for order: {} with amount: {}", event.orderId(), event.totalAmount());


        if ("DELIVERED".equals(event.status())) {
            paymentService.processCourierPayment(event);
        } else {
            log.warn("Order {} was not delivered, skipping payment calculation.", event.orderId());
        }
    }
}
