package com.capstone.core.bookingorderstatus;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.capstone.core.bookingorderstatus.projection.BookingOrderStatusProjection;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class BookingOrderStatusService {
    private BookingOrderStatusRepository bookingOrderStatusRepository;

    ResponseEntity<Object> getBookingOrderStatus() {
        List<BookingOrderStatusProjection> statusList = bookingOrderStatusRepository.findBookingOrderStatusBy();
        return new ResponseEntity<>(statusList, HttpStatus.OK);
    }
}
