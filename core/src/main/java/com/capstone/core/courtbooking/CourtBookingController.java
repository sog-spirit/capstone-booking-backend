package com.capstone.core.courtbooking;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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

    @GetMapping
    @RequestMapping("/list")
    ResponseEntity<Object> getCourtBookingList(@RequestHeader(name = "Authorization", required = true) String jwtToken,
            @RequestParam Long centerId, @RequestParam Long courtId) {
        return courtBookingService.getCourtBookingList(jwtToken, centerId, courtId);
    }

    @GetMapping
    @RequestMapping("/list/user-order")
    ResponseEntity<Object> getUserCourtBookingList(@RequestHeader(name = "Authorization", required = true) String jwtToken) {
        return courtBookingService.getUserCourtBookingList(jwtToken);
    }
}