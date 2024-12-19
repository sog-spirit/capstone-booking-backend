package com.capstone.core.bookingorderstatus;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.capstone.core.bookingorderstatus.projection.BookingOrderStatusProjection;
import com.capstone.core.table.BookingOrderStatusTable;

@Repository
public interface BookingOrderStatusRepository extends JpaRepository<BookingOrderStatusTable, Long> {
    List<BookingOrderStatusProjection> findBookingOrderStatusBy(); 
}
