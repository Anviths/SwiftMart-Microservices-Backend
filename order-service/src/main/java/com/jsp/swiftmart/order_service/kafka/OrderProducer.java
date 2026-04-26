package com.jsp.swiftmart.order_service.kafka;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class OrderProducer {


    @Autowired
    private  KafkaTemplate<String, OrderEvent> kafkaTemplate;

    public void sendOrderEvent(OrderEvent event) {

        kafkaTemplate.send("order-topic", event);
        System.out.println("order-topic");
    }
}
