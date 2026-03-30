package com.hopin.booking_service.repository;

import com.hopin.commonservice.models.Booking;
import com.hopin.commonservice.models.BookingStatus;
import com.hopin.commonservice.models.Driver;
import jakarta.persistence.LockModeType;
import jakarta.persistence.QueryHint;
import org.springframework.data.jpa.repository.*;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface BookingRepository extends JpaRepository<Booking,Long> {

//    @Modifying(clearAutomatically = true)
//    @Transactional
    @Query("update Booking b set b.bookingStatus =:status ,b.driver =:driver where b.id =:id ")
    int updateBookingStatusAndDriverById(@Param("status")BookingStatus status, @Param("driver") Driver driver, @Param("id")Long id);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("Select b from Booking b where b.id =:id")
    @QueryHints(@QueryHint(name = "jakarta.persistence.lock.timeout", value = "5000"))
    Optional<Booking> findByIdForUpdate(@Param("id")Long id);
}
