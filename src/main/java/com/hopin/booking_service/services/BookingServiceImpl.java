package com.hopin.booking_service.services;

import com.hopin.booking_service.dto.CreateBookingDto;
import com.hopin.booking_service.dto.CreateBookingResponseDto;
import com.hopin.booking_service.dto.DriverLocationDto;
import com.hopin.booking_service.dto.NearByDriversRequestDto;
import com.hopin.booking_service.repository.BookingRepository;
import com.hopin.booking_service.repository.DriverRepository;
import com.hopin.booking_service.repository.PassengerRepository;
import com.hopin.commonservice.models.Booking;
import com.hopin.commonservice.models.Driver;
import com.hopin.commonservice.models.ExactLocation;
import com.hopin.commonservice.models.Passenger;
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

    @Override
    public CreateBookingResponseDto createBooking(CreateBookingDto bookingDto) {

        //later on implement caching here
        Passenger passenger = passengerRepo.getReferenceById(bookingDto.getPassengerId());

        Booking newBooking =Booking.builder()
                .startLocation(bookingDto.getStartLocation())
                .endLocation(bookingDto.getEndLocation()).build();
//              .bookingStatus(hardcoded)
//              .passenger
//booking save
       Booking booking= bookingRepository.save(newBooking);



        ExactLocation pickupLocation=bookingDto.getStartLocation();

        NearByDriversRequestDto driversRequestDto = NearByDriversRequestDto.builder()
                .latitude(bookingDto.getStartLocation().getLatitude())
                .longitude(bookingDto.getStartLocation().getLongitude())
                .build();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<NearByDriversRequestDto> entity = new HttpEntity<>(driversRequestDto,headers);

       ResponseEntity<DriverLocationDto[]> response=restTemplate.
               exchange(locationServiceUrl, HttpMethod.POST,entity, DriverLocationDto[].class);

       List<DriverLocationDto> driverLocations = Arrays.asList(response.getBody());
        List<Driver> driverList= new ArrayList<>();
        for(DriverLocationDto driverLocationDto : driverLocations ){
            Driver driver = driverRepository.getReferenceById(driverLocationDto.getDriverId());
            driverList.add(driver);
        }

//        CreateBookingResponseDto bookingResponseDto=CreateBookingResponseDto.builder()
//                .bookingId(booking.getId())
////             .bookingStatus(booking.getStatus())
//                .driverList(driverList).build();
        CreateBookingResponseDto bookingResponseDto=CreateBookingResponseDto.builder()
                .bookingId(booking.getId()).build();
//             .bookingStatus(booking.getStatus())


        return bookingResponseDto;
    }
}
