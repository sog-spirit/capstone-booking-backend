package com.capstone.core.bookingorderstatus;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/booking-order-status")
@AllArgsConstructor
public class BookingOrderStatusController {
    private BookingOrderStatusService bookingOrderStatusService;

    @GetMapping("/list")
    ResponseEntity<Object> getBookingOrderStatus() {
        return bookingOrderStatusService.getBookingOrderStatus();
    }
}
