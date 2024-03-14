package com.restorun.backendapplication.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.restorun.backendapplication.model.Notification;
import com.restorun.backendapplication.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
@RestController
@RequestMapping("/api/notification")
public class NotificationController {
    private final NotificationService notificationService;

    // Autowire might be unnecessary.
    @Autowired
    public NotificationController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @GetMapping("/retrieveNotificationById")
    public ResponseEntity<Notification> retrieveNotificationById(@RequestBody Long id) {
        Optional<Notification> notification = notificationService.retrieveNotificationById(id);
        return notification.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());

    }

    @PostMapping("/saveNotification")
    public ResponseEntity<String> saveNotification(@RequestBody String notification) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        Notification notificationObj = mapper.readValue(notification, Notification.class);
        boolean saved = notificationService.saveNotification(notificationObj);

        if (!saved) {
            return ResponseEntity.badRequest().build();
        }

        return ResponseEntity.ok("Notification saved successfully");
    }

    @DeleteMapping("/deleteNotification")
    public ResponseEntity<String> deleteNotification(@RequestBody Long id) {
        Optional<Notification> notification = notificationService.retrieveNotificationById(id);
        if (notification.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        boolean deleted = notificationService.deleteNotification(notification);
        if (!deleted) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok("Notification deleted successfully");

    }


    // return all notifications in the system
    @GetMapping("/retrieveAllNotifications")
    public ResponseEntity<List<Notification>> retrieveAllNotifications() {
        List<Notification> notifications = notificationService.retrieveAllNotifications();
        if (notifications.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(notifications);
    }
}
