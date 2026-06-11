package az.finalproject.msnotification.service;

import az.finalproject.msnotification.entity.NotificationLog;
import az.finalproject.msnotification.enums.NotificationStatus;
import az.finalproject.msnotification.enums.NotificationType;
import az.finalproject.msnotification.repository.NotificationLogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static az.finalproject.msnotification.enums.NotificationStatus.FAILED;
import static az.finalproject.msnotification.enums.NotificationStatus.SENT;

@Service
@RequiredArgsConstructor
public class NotificationService {
    private final List<NotificationStrategy> strategies;
    private final NotificationLogRepository repository;

    public void processNotification(UUID userId, String recipient, String message, String subject, NotificationType type) {


        NotificationStrategy strategy = strategies.stream()
                .filter(s -> s.getType() == type)
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Strategy not found for type: " + type));

        try {

            strategy.send(recipient, message, subject);
            saveLog(userId, recipient, subject, message, SENT);
        } catch (Exception e) {
            saveLog(userId, recipient, subject, message, FAILED);
            throw e;
        }
    }

    private void saveLog(UUID userId, String email, String title, String msg, NotificationStatus status) {
        repository.save(NotificationLog.builder()
                .recipientId(userId)
                .recipientEmail(email)
                .title(title)
                .message(msg)
                .status(status)
                .sentAt(LocalDateTime.now())
                .build());
    }
}
