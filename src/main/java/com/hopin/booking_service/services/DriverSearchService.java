package com.hopin.booking_service.services;

import com.hopin.booking_service.dto.CreateBookingDto;
import com.hopin.booking_service.dto.DriverLocationDto;
import com.hopin.booking_service.dto.NearByDriversRequestDto;
import com.hopin.booking_service.repository.DriverRepository;
import com.hopin.commonservice.models.Driver;
import com.hopin.commonservice.models.ExactLocation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class DriverSearchService {

    @Value("${LOCATION_SERVICE_URL}")
    private String locationServiceUrl;

    @Autowired
    RestTemplate restTemplate;

    @Autowired
    DriverRepository driverRepository;

    @Async
    public void searchNearbyDrivers(CreateBookingDto bookingDto) throws InterruptedException {

        System.out.println("Inside the searchNearbyDriver- waiting");

        Thread.sleep(20000);
        System.out.println("Inside the searchNearbyDriver- resume after thread sleep");

        ExactLocation pickupLocation=bookingDto.getStartLocation();

        NearByDriversRequestDto driversRequestDto = NearByDriversRequestDto.builder()
                .latitude(bookingDto.getStartLocation().getLatitude())
                .longitude(bookingDto.getStartLocation().getLongitude())
                .build();

//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(MediaType.APPLICATION_JSON);
//
//        HttpEntity<NearByDriversRequestDto> entity = new HttpEntity<>(driversRequestDto,headers);
//
//        //rest call to fetch nearby drivers' list
//        ResponseEntity<DriverLocationDto[]> response=restTemplate.
//                exchange(locationServiceUrl, HttpMethod.POST,entity, DriverLocationDto[].class);
//
//        List<DriverLocationDto> driverLocations = Arrays.asList(response.getBody());
//        List<Driver> driverList= new ArrayList<>();
//        for(DriverLocationDto driverLocationDto : driverLocations ){
//            Driver driver = driverRepository.getReferenceById(driverLocationDto.getDriverId());
//            driverList.add(driver);
//        }
    }

}
