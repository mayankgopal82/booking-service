package com.hopin.booking_service.services;

import com.hopin.booking_service.dto.*;
import com.hopin.booking_service.exception.DriverNotFoundException;
import com.hopin.booking_service.repository.BookingRepository;
import com.hopin.booking_service.repository.DriverRepository;
import com.hopin.booking_service.repository.PassengerRepository;
import com.hopin.commonservice.models.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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
                thenAccept((drivers)->{
                    RideRequestDto rideRequestDto = RideRequestDto.builder().bookingId(booking.getId()).
                            passengerId(passenger.getId()).startLocation(bookingDto.getStartLocation()).endLocation(bookingDto.getEndLocation())
                            .driverIds(drivers).build();
                    callSocketServiceRideRequest(rideRequestDto);
                });
//fetching dirvier list end----

        CreateBookingResponseDto bookingResponseDto=CreateBookingResponseDto.builder()
                .bookingId(booking.getId())
                .bookingStatus(booking.getBookingStatus()).build();

        System.out.println("returning the booking response");
        //publish event to the passenger - getting you ride booked ........
        return bookingResponseDto;
    }

    public void callSocketServiceRideRequest(RideRequestDto rideRequestDto){

        HttpEntity<RideRequestDto> entity = new HttpEntity<>(rideRequestDto, (HttpHeaders) null);
        restTemplate.exchange("localhost/api/socket/ride-requests",HttpMethod.POST,entity,void.class);

    }

    @Transactional
    public  UpdateBookingResponseDto updateBooking(UpdateBookingRequestDto updateBookingRequestDto)  {

        //verify the status value from the request body -
        String updateStatus=updateBookingRequestDto.getStatus();
        if(updateStatus.equalsIgnoreCase(String.valueOf(BookingStatus.ACCEPTED))){
//           Optional<Booking> booking= bookingRepository.findById(updateBookingRequestDto.getBookingId());
          Booking booking=bookingRepository.findByIdForUpdate(updateBookingRequestDto.getBookingId()).
                  orElseThrow(()-> new RuntimeException("Booking  not found"));
           if(booking.getBookingStatus()==BookingStatus.ACCEPTED){
               throw  new IllegalArgumentException("Booking Already Accepted");
           }
        }

        Long driverId = updateBookingRequestDto.getDriverId();
        Long bookingId = updateBookingRequestDto.getBookingId();
            Driver driver = driverRepository.findById(driverId).
                    orElseThrow(() -> new DriverNotFoundException("driver not found"));

            bookingRepository.updateBookingStatusAndDriverById(BookingStatus.ACCEPTED, driver, bookingId);
        ///publish event to driver and passenger -
//           through kafka

        return UpdateBookingResponseDto.builder().status(BookingStatus.valueOf(updateBookingRequestDto.getStatus()))
                        .driverId(updateBookingRequestDto.getDriverId()).build();

    }

}

