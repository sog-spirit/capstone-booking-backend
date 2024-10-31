package com.capstone.core.courtbooking;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.capstone.core.courtbooking.data.AddNewCourtBookingRequestData;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/court-booking")
@AllArgsConstructor
public class CourtBookingController {
    private CourtBookingService courtBookingService;

    @PostMapping
    ResponseEntity<Object> addNewCourtBooking(@RequestHeader(name = "Authorization", required = true) String jwtToken,
            @RequestBody @Valid AddNewCourtBookingRequestData addNewCourtBookingRequestData) {
        return courtBookingService.addNewCourtBooking(jwtToken, addNewCourtBookingRequestData);
    }
}
