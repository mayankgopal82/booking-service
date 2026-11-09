package com.hopin.booking_service.services;

import com.hopin.booking_service.dto.DriverSummaryDto;
import com.hopin.booking_service.repository.DriverRepository;
import com.hopin.commonservice.models.Driver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DriverService {

    @Autowired
    DriverRepository driverRepo;

    /*To practice N+1 problem*/
    public List<DriverSummaryDto> getDriversSummary(){

          List<Driver> allDrivers = driverRepo.findAll();

          return allDrivers.stream().map((driver)-> DriverSummaryDto.builder().driverId(driver.getId())
                  .name(driver.getName()).bookingCount(driver.getBooking().size())
                  .bookings(driver.getBooking()).build()).toList();

    }
}
