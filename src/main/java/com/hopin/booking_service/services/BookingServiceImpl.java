package com.hopin.booking_service.services;

import com.hopin.booking_service.dto.CreateBookingDto;
import com.hopin.booking_service.dto.CreateBookingResponseDto;
import com.hopin.booking_service.dto.DriverLocationDto;
import com.hopin.booking_service.dto.NearByDriversRequestDto;
import com.hopin.booking_service.repository.BookingRepository;
import com.hopin.booking_service.repository.DriverRepository;
import com.hopin.booking_service.repository.PassengerRepository;
import com.hopin.commonservice.models.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class BookingServiceImpl implements  BookingService{

    @Value("${LOCATION_SERVICE_URL}")
    private String locationServiceUrl;

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    PassengerRepository passengerRepo;

    @Autowired
    BookingRepository bookingRepository;

    @Autowired
    DriverRepository driverRepository;

    @Autowired
    DriverSearchService driverSearchService;

    @Override
    public CreateBookingResponseDto createBooking(CreateBookingDto bookingDto) throws InterruptedException {

        //later on implement caching here
        Passenger passenger = passengerRepo.findById(bookingDto.getPassengerId())
                .orElse(null);

        Booking newBooking =Booking.builder()
                .startLocation(bookingDto.getStartLocation())
                .endLocation(bookingDto.getEndLocation())
                .bookingStatus(BookingStatus.ASSIGNING_DRIVER)
                .passenger(passenger).build();
//booking save
       Booking booking= bookingRepository.save(newBooking);


//fetching driver list
        driverSearchService.searchNearbyDrivers(bookingDto);
//fetching dirvier list end----

        CreateBookingResponseDto bookingResponseDto=CreateBookingResponseDto.builder()
                .bookingId(booking.getId())
                .bookingStatus(booking.getBookingStatus()).build();
//              .driverList(driverList).build();

        System.out.println("returning the booking response");
        return bookingResponseDto;
    }
}
