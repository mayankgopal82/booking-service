package com.hopin.booking_service.repository;

import com.hopin.commonservice.models.Booking;
import com.hopin.commonservice.models.BookingStatus;
import com.hopin.commonservice.models.Driver;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface BookingRepository extends JpaRepository<Booking,Long> {

    @Modifying(clearAutomatically = true)
    @Transactional
    @Query("update Booking b set b.bookingStatus =:status ,b.driver =:driver where b.id =:id ")
    int updateBookingStatusAndDriverById(@Param("status")BookingStatus status, @Param("driver") Driver driver, @Param("id")Long id);

}
