package com.restorun.backendapplication.service;

import com.restorun.backendapplication.model.Notification;
import com.restorun.backendapplication.repository.NotificationRepository;
import org.aspectj.weaver.ast.Not;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
@Service
@Transactional
public class NotificationService {
    private final NotificationRepository notificationRepository;
    @Autowired
    public NotificationService(NotificationRepository notificationRepository) {
        this.notificationRepository = notificationRepository;
    }

    @Transactional(readOnly = true)
    public Optional<Notification> retrieveNotificationById(Long id) {
        return notificationRepository.findById(id);
    }

    public boolean saveNotification(Notification notification) {
        notificationRepository.save(notification);
        return true;
    }

    public boolean deleteNotification(Optional<Notification> notification) {
        if (notification.isPresent()){
            notificationRepository.delete(notification.get());
            return true;
        }
        return true;
    }
    public boolean updateNotification(Notification notification) {
        return notificationRepository.findById(notification.getId())
                .map(existingNotification -> {
                    existingNotification.setMessage(existingNotification.getMessage());
                    existingNotification.setReservation(existingNotification.getReservation());
                    existingNotification.setSentAt(existingNotification.getSentAt());
                    notificationRepository.save(existingNotification);
                    return true; // Indicates success
                })
                .orElseThrow(() -> new RuntimeException("Notification not found with id: " + notification.getId()));
    }

    public List<Notification> retrieveAllNotifications(){return notificationRepository.findAll();}
}
