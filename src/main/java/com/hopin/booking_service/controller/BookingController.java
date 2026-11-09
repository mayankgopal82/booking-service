package com.hopin.booking_service.controller;

import com.hopin.booking_service.dto.CreateBookingDto;
import com.hopin.booking_service.dto.CreateBookingResponseDto;
import com.hopin.booking_service.dto.UpdateBookingRequestDto;
import com.hopin.booking_service.dto.UpdateBookingResponseDto;
import com.hopin.booking_service.services.BookingService;
import com.hopin.booking_service.services.BookingServiceImpl;
import com.hopin.commonservice.models.Booking;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @PatchMapping("/{bookingId}")
    public ResponseEntity<UpdateBookingResponseDto> updateBooking(@RequestBody UpdateBookingRequestDto updateBookingRequestDto, @PathVariable Long bookingId) {
        //TO_DO- add exception handling -
//       int rowAffected = bookingService.updateBooking(updateBookingRequestDto, bookingId);
//       UpdateBookingResponseDto response = UpdateBookingResponseDto.builder().driverId(updateBookingRequestDto.getDriverId())
//                .status(updateBookingRequestDto.getStatus()).build();
//
//        if(rowAffected==1){
//            return new ResponseEntity<>(response,HttpStatus.OK);
//        }else{
//            return  new ResponseEntity<>(null,HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//        return null;z
        return new ResponseEntity<>(bookingService.updateBooking(updateBookingRequestDto),HttpStatus.OK);
    }


}
