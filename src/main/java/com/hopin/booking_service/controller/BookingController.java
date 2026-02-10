package com.hopin.booking_service.controller;

import com.hopin.booking_service.dto.CreateBookingDto;
import com.hopin.booking_service.dto.CreateBookingResponseDto;
import com.hopin.booking_service.services.BookingService;
import com.hopin.booking_service.services.BookingServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/booking")
public class BookingController {

    @Autowired
    BookingService bookingService;
    @PostMapping
    public ResponseEntity<CreateBookingResponseDto> createBooking(@RequestBody CreateBookingDto bookingRequestDto) throws InterruptedException {

        CreateBookingResponseDto responseDto = bookingService.createBooking(bookingRequestDto);

        return new ResponseEntity<>(responseDto, HttpStatus.CREATED);

    }

}
