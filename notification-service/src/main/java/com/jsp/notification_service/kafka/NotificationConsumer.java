package com.jsp.notification_service.kafka;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class NotificationConsumer {

    @KafkaListener(topics = "order-topic", groupId = "notification-group-v2",containerFactory = "kafkaListenerContainerFactory")
    public void consume(OrderEvent event) {
        System.out.println("Notification Service Received: " + event);
    }
}
