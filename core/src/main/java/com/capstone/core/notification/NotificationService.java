package com.capstone.core.notification;

import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import com.capstone.core.table.UserTable;
import com.capstone.core.user.UserRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class NotificationService {
    private NotificationRepository notificationRepository;
    private SimpMessagingTemplate messagingTemplate;
    private UserRepository userRepository;

    public void sendNotificationToUser(Long userId, String message) {
        UserTable user = userRepository.getReferenceById(userId);
        String jsonResponse = "{\"id\":" + user.getId() + ",\"message\":\"" + message + "\"}";
        messagingTemplate.convertAndSendToUser(user.getUsername(), "/queue/notification", jsonResponse);
    }
}
