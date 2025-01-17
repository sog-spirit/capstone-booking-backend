package com.capstone.core.notification;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/notification")
@AllArgsConstructor
public class NotificationController {
    @MessageMapping("/send")
    public String handleMessage(String message) {
        return "Received: " + message;
    }
}
