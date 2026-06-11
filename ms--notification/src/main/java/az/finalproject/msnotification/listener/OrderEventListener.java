package az.finalproject.msnotification.listener;


import az.finalproject.msnotification.enums.NotificationType;
import az.finalproject.msnotification.service.NotificationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class OrderEventListener {

    private final NotificationService notificationService;

    @RabbitListener(queues = "notification-service-queue")
    public void handleOrderEvent(OrderEvent event) {
        log.info("Notification Service received order event: {} with status {}", event.orderId(), event.status());

        String subject = "Delivery App - Order Update";
        String message = switch (event.status()) {
            case "ASSIGNED" -> "Great news! Your order " + event.orderId() + " has been assigned to a courier.";
            case "DELIVERED" -> "Your order " + event.orderId() + " was delivered. Enjoy your meal!";
            case "CANCELLED" -> "Your order " + event.orderId() + " was unfortunately cancelled.";
            default -> "There is an update on your order " + event.orderId();
        };

        notificationService.processNotification(
                event.customerId(),
                "aydanb.dev@gmail.com", // Test üçün sabit email
                message,
                subject,
                NotificationType.EMAIL
        );
    }
}
