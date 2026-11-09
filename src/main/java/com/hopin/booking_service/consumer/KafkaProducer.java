//package com.hopin.booking_service.consumer;
//
//import com.hopin.commonservice.models.Booking;
//import com.hopin.commonservice.models.BookingEvent;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.kafka.core.KafkaTemplate;
//import org.springframework.stereotype.Service;
//
//@Service
//public class KafkaProducer {
//
//
//    @Autowired
//    KafkaTemplate<String,Object> kafkaTemplate;
//
//    @Autowired
//    BookingEvent bookingEvent;
//
//    public void publishMessage(BookingEvent bookingEvent){
//
//      kafkaTemplate.send("/",bookingEvent);
//    }
//
//}
