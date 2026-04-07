package com.hopin.booking_service.consumer;

import com.hopin.booking_service.dto.UpdateBookingRequestDto;
import com.hopin.booking_service.services.BookingService;
import com.hopin.booking_service.services.BookingServiceImpl;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import tools.jackson.databind.ObjectMapper;

@Service
public class KafkaConsumer {


    private final ObjectMapper mapper;
    BookingService bookingService;

    //constructor injection -
    public KafkaConsumer(ObjectMapper objectMapper, BookingService bookingService){
        this.mapper=objectMapper;
        this.bookingService = bookingService;
    }

    @KafkaListener(topics = "sample-topic",groupId = "my-group2")
    public void Consume(String message){
        System.out.println("Inside bookingService"+message);
         //convert this json string back to java object using json library-jackson
        UpdateBookingRequestDto updateBookingRequestDto = mapper.readValue(message,UpdateBookingRequestDto.class);

        bookingService.updateBooking(updateBookingRequestDto);
    }

}
