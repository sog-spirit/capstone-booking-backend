package com.capstone.core.courtbooking;

import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.capstone.core.courtbooking.data.request.AddNewCourtBookingRequestData;
import com.capstone.core.courtbooking.data.request.CenterOwnerCourtCourtBookingListRequestData;
import com.capstone.core.courtbooking.data.request.UserCenterCourtBookingListRequestData;
import com.capstone.core.courtbooking.data.request.UserCenterListFromUserOrderRequestData;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/court-booking")
@AllArgsConstructor
public class CourtBookingController {
    private CourtBookingService courtBookingService;

    @PostMapping
    @Transactional
    ResponseEntity<Object> addNewCourtBooking(@RequestHeader(name = "Authorization", required = true) String jwtToken,
            @RequestBody @Valid AddNewCourtBookingRequestData addNewCourtBookingRequestData) {
        return courtBookingService.addNewCourtBooking(jwtToken, addNewCourtBookingRequestData);
    }

    @GetMapping(value = "/user/center/list")
    ResponseEntity<Object> getUserCenterCourtBookingList(@RequestHeader(name = "Authorization", required = true) String jwtToken,
            UserCenterCourtBookingListRequestData requestData) {
        return courtBookingService.getUserCenterCourtBookingList(jwtToken, requestData);
    }

    @GetMapping(value = "/user/list")
    ResponseEntity<Object> getUserCourtBookingList(@RequestHeader(name = "Authorization", required = true) String jwtToken) {
        return courtBookingService.getUserCourtBookingList(jwtToken);
    }

    @GetMapping(value = "/center-owner/list")
    ResponseEntity<Object> getCenterOwnerCourtBookingList(@RequestHeader(name = "Authorization", required = true) String jwtToken) {
        return courtBookingService.getCenterOwnerCourtBookingList(jwtToken);
    }

    @GetMapping(value = "/center-owner/court/list")
    ResponseEntity<Object> getCenterOwnerCourtCourtBookingList(@RequestHeader(name = "Authorization", required = true) String jwtToken,
            CenterOwnerCourtCourtBookingListRequestData requestData) {
        return courtBookingService.getCenterOwnerCourtCourtBookingList(jwtToken, requestData);
    }

    @GetMapping(value = "/user/list/center-list")
    ResponseEntity<Object> getUserCenterListFromUserOrder(@RequestHeader(name = "Authorization", required = true) String jwtToken,
            UserCenterListFromUserOrderRequestData requestData) {
        return courtBookingService.getUserCenterListFromUserOrder(jwtToken, requestData);
    }
}