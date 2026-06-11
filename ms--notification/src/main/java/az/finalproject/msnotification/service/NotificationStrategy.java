package az.finalproject.msnotification.service;

import az.finalproject.msnotification.enums.NotificationType;


public interface NotificationStrategy {
    void send(String recipient, String message, String subject);
    NotificationType getType();
}
