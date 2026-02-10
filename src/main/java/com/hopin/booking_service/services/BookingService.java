package com.hopin.booking_service.services;

import com.hopin.booking_service.dto.CreateBookingDto;
import com.hopin.booking_service.dto.CreateBookingResponseDto;
import com.hopin.commonservice.models.Booking;
import org.springframework.stereotype.Service;

@Service
public interface BookingService {

    public CreateBookingResponseDto createBooking(CreateBookingDto bookingDto) throws InterruptedException;


}
