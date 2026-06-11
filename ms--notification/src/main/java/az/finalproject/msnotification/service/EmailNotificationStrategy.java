package az.finalproject.msnotification.service;

import az.finalproject.msnotification.enums.NotificationType;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class EmailNotificationStrategy implements NotificationStrategy {

    private final JavaMailSender mailSender;

    @Override
    public void send(String recipient, String message, String subject) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();

        mailMessage.setFrom("bayramoffaaydan1@gmail.com");
        mailMessage.setTo(recipient);
        mailMessage.setSubject(subject);
        mailMessage.setText(message);

        mailSender.send(mailMessage);
    }

    @Override
    public NotificationType getType() {
        return NotificationType.EMAIL;
    }
}