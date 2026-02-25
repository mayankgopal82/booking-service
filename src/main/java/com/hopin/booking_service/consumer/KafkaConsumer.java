package com.hopin.booking_service.consumer;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class KafkaConsumer {

    @KafkaListener(topics = "sample-topic",groupId = "my-group2")
    public void Consume(String message){
        System.out.println("Inside bookingService"+message);
    }
}
