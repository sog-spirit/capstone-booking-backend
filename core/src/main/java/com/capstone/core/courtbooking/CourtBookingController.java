package com.capstone.core.courtbooking;

import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.capstone.core.courtbooking.data.request.AddNewCourtBookingRequestData;
import com.capstone.core.courtbooking.data.request.CenterOwnerCancelCourtBookingRequestData;
import com.capstone.core.courtbooking.data.request.CenterOwnerCheckoutCourtBookingRequestData;
import com.capstone.core.courtbooking.data.request.CenterOwnerCourtBookingCenterListRequestData;
import com.capstone.core.courtbooking.data.request.CenterOwnerCourtBookingCourtListRequestData;
import com.capstone.core.courtbooking.data.request.CenterOwnerCourtBookingDetailRequestData;
import com.capstone.core.courtbooking.data.request.CenterOwnerCourtBookingListRequestData;
import com.capstone.core.courtbooking.data.request.CenterOwnerCourtBookingUserListRequestData;
import com.capstone.core.courtbooking.data.request.CenterOwnerCourtCourtBookingListRequestData;
import com.capstone.core.courtbooking.data.request.CenterOwnerStatisticsCenterRequestData;
import com.capstone.core.courtbooking.data.request.UserCancelCourtBookingRequestData;
import com.capstone.core.courtbooking.data.request.UserCenterCourtBookingListRequestData;
import com.capstone.core.courtbooking.data.request.UserCenterListFromUserOrderRequestData;
import com.capstone.core.courtbooking.data.request.UserCourtBookingCenterIdRequestData;
import com.capstone.core.courtbooking.data.request.UserCourtBookingCenterListRequestData;
import com.capstone.core.courtbooking.data.request.UserCourtBookingCourtListRequestData;
import com.capstone.core.courtbooking.data.request.UserCourtBookingDetailRequestData;
import com.capstone.core.courtbooking.data.request.UserCourtBookingListRequestData;
import com.capstone.core.courtbooking.data.request.UserCourtCourtBookingListRequestData;
import com.capstone.core.courtbooking.data.request.UserCourtDateCourtBookingRequestData;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/court-booking")
@AllArgsConstructor
public class CourtBookingController {
    private CourtBookingService courtBookingService;

    @PostMapping(value = "/user")
    @Transactional
    ResponseEntity<Object> addUserNewCourtBooking(@RequestHeader(name = "Authorization", required = true) String jwtToken,
            @RequestBody @Valid AddNewCourtBookingRequestData addNewCourtBookingRequestData) {
        return courtBookingService.addUserNewCourtBooking(jwtToken, addNewCourtBookingRequestData);
    }

    @PostMapping(value = "/center-owner")
    @Transactional
    ResponseEntity<Object> addCenterOwnerNewCourtBooking(@RequestHeader(name = "Authorization", required = true) String jwtToken,
            @RequestBody @Valid AddNewCourtBookingRequestData addNewCourtBookingRequestData) {
        return courtBookingService.addCenterOwnerNewCourtBooking(jwtToken, addNewCourtBookingRequestData);
    }

    @DeleteMapping(value = "/user/cancel")
    @Transactional
    ResponseEntity<Object> userCancelCourtBooking(@RequestHeader(name = "Authorization", required = true) String jwtToken,
            @RequestBody @Valid UserCancelCourtBookingRequestData requestData) {
        return courtBookingService.userCancelCourtBooking(jwtToken, requestData);
    }

    @DeleteMapping(value = "/center-owner/cancel")
    @Transactional
    ResponseEntity<Object> centerOwnerCancelCourtBooking(@RequestHeader(name = "Authorization", required = true) String jwtToken,
            @RequestBody @Valid CenterOwnerCancelCourtBookingRequestData requestData) {
        return courtBookingService.centerOwnerCancelCourtBooking(jwtToken, requestData);
    }

    @PutMapping(value = "/center-owner/checkout")
    @Transactional
    ResponseEntity<Object> centerOwnerCheckoutCourtBooking(@RequestHeader(name = "Authorization", required = true) String jwtToken,
            @RequestBody @Valid CenterOwnerCheckoutCourtBookingRequestData requestData) {
        return courtBookingService.centerOwnerCheckoutCourtBooking(jwtToken, requestData);
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

    @GetMapping(value = "/user/court/list")
    ResponseEntity<Object> getUserCourtCourtBookingList(@RequestHeader(name = "Authorization", required = true) String jwtToken,
            UserCourtCourtBookingListRequestData requestData) {
        return courtBookingService.getUserCourtCourtBookingList(jwtToken, requestData);
    }

    @GetMapping(value = "/user/court/list/date")
    ResponseEntity<Object> getUserCourtDateCourtBookingList(@RequestHeader(name = "Authorization", required = true) String jwtToken,
            UserCourtDateCourtBookingRequestData requestData) {
        return courtBookingService.getUserCourtDateCourtBookingList(jwtToken, requestData);
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

    @GetMapping(value = "/user/detail")
    ResponseEntity<Object> getUserCourtBookingDetail(@RequestHeader(name = "Authorization", required = true) String jwtToken,
            UserCourtBookingDetailRequestData requestData) {
        return courtBookingService.getUserCourtBookingDetail(jwtToken, requestData);
    }

    @GetMapping(value = "/center-owner/detail")
    ResponseEntity<Object> getCenterOwnerCourtBookingDetail(@RequestHeader(name = "Authorization", required = true) String jwtToken,
            CenterOwnerCourtBookingDetailRequestData requestData) {
        return courtBookingService.getCenterOwnerCourtBookingDetail(jwtToken, requestData);
    }

    @GetMapping(value = "/user/center/id")
    ResponseEntity<Object> getUserCourtBookingCenterId(@RequestHeader(name = "Authorization", required = true) String jwtToken,
            UserCourtBookingCenterIdRequestData requestData) {
        return courtBookingService.getUserCourtBookingCenterId(jwtToken, requestData);
    }

    @GetMapping(value = "/center-owner/statistics/center")
    ResponseEntity<Object> getCenterOwnerStatisticsCenter(@RequestHeader(name = "Authorization", required = true) String jwtToken,
            CenterOwnerStatisticsCenterRequestData requestData) {
        return courtBookingService.getCenterOwnerStatisticsCenter(jwtToken, requestData);
    }
}
