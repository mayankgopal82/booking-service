package com.hopin.booking_service.services;

import com.hopin.booking_service.dto.*;
import com.hopin.booking_service.repository.BookingRepository;
import com.hopin.booking_service.repository.DriverRepository;
import com.hopin.booking_service.repository.PassengerRepository;
import com.hopin.commonservice.models.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

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

       Booking booking= bookingRepository.save(newBooking);


//fetching driver list - async
        driverSearchService.searchNearbyDrivers(bookingDto).
                thenAccept(this::callSocketServiceRideRequest);
//fetching dirvier list end----

        CreateBookingResponseDto bookingResponseDto=CreateBookingResponseDto.builder()
                .bookingId(booking.getId())
                .bookingStatus(booking.getBookingStatus()).build();

        System.out.println("returning the booking response");
        return bookingResponseDto;
    }

    public void callSocketServiceRideRequest(List<Long> driverList){

        HttpEntity<List<Long>> entity = new HttpEntity<>(driverList, (HttpHeaders) null);
        restTemplate.exchange("localhost/api/socket/ride-requests",HttpMethod.POST,entity,void.class);

    }

    public UpdateBookingResponseDto updateBooking(UpdateBookingRequestDto updateBookingRequestDto, Long bookingId){

        Long driverId = updateBookingRequestDto.getDriverId();
        Driver driver = driverRepository.findById(driverId).
                orElseThrow(()-> new RuntimeException("driver not found"));

        bookingRepository.updateBookingStatusAndDriverById(BookingStatus.ACCEPTED,driver,bookingId);

        return UpdateBookingResponseDto.builder().status(updateBookingRequestDto.getStatus())
                .driverId(updateBookingRequestDto.getDriverId()).build();
    }

}
