package com.capstone.core.courtbooking;

import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.capstone.core.courtbooking.data.request.AddNewCourtBookingRequestData;
import com.capstone.core.courtbooking.data.request.CenterOwnerCourtBookingCenterListRequestData;
import com.capstone.core.courtbooking.data.request.CenterOwnerCourtBookingCourtListRequestData;
import com.capstone.core.courtbooking.data.request.CenterOwnerCourtBookingListRequestData;
import com.capstone.core.courtbooking.data.request.CenterOwnerCourtBookingUserListRequestData;
import com.capstone.core.courtbooking.data.request.CenterOwnerCourtCourtBookingListRequestData;
import com.capstone.core.courtbooking.data.request.UserCenterCourtBookingListRequestData;
import com.capstone.core.courtbooking.data.request.UserCenterListFromUserOrderRequestData;
import com.capstone.core.courtbooking.data.request.UserCourtBookingCenterListRequestData;
import com.capstone.core.courtbooking.data.request.UserCourtBookingCourtListRequestData;
import com.capstone.core.courtbooking.data.request.UserCourtBookingListRequestData;

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
    ResponseEntity<Object> getUserCourtBookingList(@RequestHeader(name = "Authorization", required = true) String jwtToken,
            UserCourtBookingListRequestData requestData) {
        return courtBookingService.getUserCourtBookingList(jwtToken, requestData);
    }

    @GetMapping(value = "/center-owner/list")
    ResponseEntity<Object> getCenterOwnerCourtBookingList(@RequestHeader(name = "Authorization", required = true) String jwtToken,
            CenterOwnerCourtBookingListRequestData requestData) {
        return courtBookingService.getCenterOwnerCourtBookingList(jwtToken, requestData);
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

    @GetMapping(value = "/center-owner/filter/list/center")
    ResponseEntity<Object> getCenterOwnerCourtBookingCenterList(@RequestHeader(name = "Authorization", required = true) String jwtToken,
            CenterOwnerCourtBookingCenterListRequestData requestData) {
        return courtBookingService.getCenterOwnerCourtBookingCenterList(jwtToken, requestData);
    }

    @GetMapping(value = "/center-owner/filter/list/court")
    ResponseEntity<Object> getCenterOwnerCourtBookingCourtList(@RequestHeader(name = "Authorization", required = true) String jwtToken,
            CenterOwnerCourtBookingCourtListRequestData requestData) {
        return courtBookingService.getCenterOwnerCourtBookingCourtList(jwtToken, requestData);
    }

    @GetMapping(value = "/center-owner/filter/list/user")
    ResponseEntity<Object> getCenterOwnerCourtBookingUserList(@RequestHeader(name = "Authorization", required = true) String jwtToken,
            CenterOwnerCourtBookingUserListRequestData requestData) {
        return courtBookingService.getCenterOwnerCourtBookingUserList(jwtToken, requestData);
    }

    @GetMapping(value = "/user/filter/list/center")
    ResponseEntity<Object> getUserCourtBookingCenterList(@RequestHeader(name = "Authorization", required = true) String jwtToken,
            UserCourtBookingCenterListRequestData requestData) {
        return courtBookingService.getUserCourtBookingCenterList(jwtToken, requestData);
    }

    @GetMapping(value = "/user/filter/list/court")
    ResponseEntity<Object> getUserCourtBookingCourtList(@RequestHeader(name = "Authorization", required = true) String jwtToken,
            UserCourtBookingCourtListRequestData requestData) {
        return courtBookingService.getUserCourtBookingCourtList(jwtToken, requestData);
    }
}