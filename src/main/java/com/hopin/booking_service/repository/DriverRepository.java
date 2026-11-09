package com.hopin.booking_service.repository;

import com.hopin.commonservice.models.Driver;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DriverRepository extends JpaRepository<Driver,Long> {

//   @Query("select Driver d JOIN FETCH Booking b")
//   public  Optional<String > findDriverWithBookings();
}
