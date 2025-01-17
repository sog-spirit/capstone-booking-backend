package com.capstone.core.courtbookingproductorder;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.capstone.core.courtbookingproductorder.data.request.AddCourtBookingProductOrderRequestData;
import com.capstone.core.courtbookingproductorder.data.request.AdminCourtBookingStatisticsRequestData;
import com.capstone.core.courtbookingproductorder.data.request.AdminProductOrderStatisticsRequestData;
import com.capstone.core.courtbookingproductorder.data.request.CenterOwnerCancelCourtBookingProductOrderRequestData;
import com.capstone.core.courtbookingproductorder.data.request.CenterOwnerCheckoutCourtBookingProductOrderRequestData;
import com.capstone.core.courtbookingproductorder.data.request.CenterOwnerCourtBookingProductOrderDetailListRequestData;
import com.capstone.core.courtbookingproductorder.data.request.CenterOwnerCourtBookingProductOrderListRequestData;
import com.capstone.core.courtbookingproductorder.data.request.CenterOwnerCourtBookingStatisticsRequestData;
import com.capstone.core.courtbookingproductorder.data.request.CenterOwnerProductOrderStatisticsRequestData;
import com.capstone.core.courtbookingproductorder.data.request.UserCancelCourtBookingProductOrderRequestData;
import com.capstone.core.courtbookingproductorder.data.request.UserCourtBookingProductOrderDetailListRequestData;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/court-booking-product-order")
@AllArgsConstructor
public class CourtBookingProductOrderController {
    private CourtBookingProductOrderService courtBookingProductOrderService;

    @PostMapping
    @Transactional
    ResponseEntity<Object> addCourtBookingProductOrder(@RequestHeader(name = "Authorization", required = true) String jwtToken,
            @RequestBody @Valid AddCourtBookingProductOrderRequestData requestData) {
        return courtBookingProductOrderService.addCourtBookingProductOrder(jwtToken, requestData);
    }

    @DeleteMapping(value = "/center-owner/cancel")
    @Transactional
    ResponseEntity<Object> centerOwnerCancelCourtBookingProductOrder(@RequestHeader(name = "Authorization", required = true) String jwtToken,
            @RequestBody @Valid CenterOwnerCancelCourtBookingProductOrderRequestData requestData) {
        return courtBookingProductOrderService.centerOwnerCancelCourtBookingProductOrder(jwtToken, requestData);
    }

    @DeleteMapping(value = "/user/cancel")
    @Transactional
    ResponseEntity<Object> userCancelCourtBookingProductOrder(@RequestHeader(name = "Authorization", required = true) String jwtToken,
            @RequestBody @Valid UserCancelCourtBookingProductOrderRequestData requestData) {
        return courtBookingProductOrderService.userCancelCourtBookingProductOrder(jwtToken, requestData);
    }

    @PutMapping(value = "/center-owner/checkout")
    @Transactional
    ResponseEntity<Object> centerOwnerCheckoutCourtBookingProductOrder(@RequestHeader(name = "Authorization", required = true) String jwtToken,
            @RequestBody @Valid CenterOwnerCheckoutCourtBookingProductOrderRequestData requestData) {
        return courtBookingProductOrderService.centerOwnerCheckoutCourtBookingProductOrder(jwtToken, requestData);
    }

    @GetMapping("/user/court-booking/list")
    ResponseEntity<Object> getUserCourtBookingProductOrderDetailList(@RequestHeader(name = "Authorization", required = true) String jwtToken,
            UserCourtBookingProductOrderDetailListRequestData requestData) {
        return courtBookingProductOrderService.getUserCourtBookingProductOrderList(jwtToken, requestData);
    }

    @GetMapping("/center-owner/court-booking/list")
    ResponseEntity<Object> getCenterOwnerCourtBookingProductOrderDetailList(@RequestHeader(name = "Authorization", required = true) String jwtToken,
            CenterOwnerCourtBookingProductOrderDetailListRequestData requestData) {
        return courtBookingProductOrderService.getCenterOwnerCourtBookingProductOrderDetailList(jwtToken, requestData);
    }

    @GetMapping("/center-owner/list")
    ResponseEntity<Object> getCenterOwnerCourtBookingProductOrderList(@RequestHeader(name = "Authorization", required = true) String jwtToken,
            CenterOwnerCourtBookingProductOrderListRequestData requestData) {
        return courtBookingProductOrderService.getCenterOwnerCourtBookingProductOrderList(jwtToken, requestData);
    }

    @GetMapping("/center-owner/product-order/statistics")
    ResponseEntity<Object> getCenterOwnerProductOrderStatistics(@RequestHeader(name = "Authorization", required = true) String jwtToken,
            CenterOwnerProductOrderStatisticsRequestData requestData) {
        return courtBookingProductOrderService.getCenterOwnerProductOrderStatistics(jwtToken, requestData);
    }

    @GetMapping("/center-owner/court-booking/statistics")
    ResponseEntity<Object> getCenterOwnerCourtBookingStatistics(@RequestHeader(name = "Authorization", required = true) String jwtToken,
            CenterOwnerCourtBookingStatisticsRequestData requestData) {
        return courtBookingProductOrderService.getCenterOwnerCourtBookingStatistics(jwtToken, requestData);
    }

    @GetMapping("/center-owner/statistics/today")
    ResponseEntity<Object> getCenterOwnerProductOrderStatisticsToday(@RequestHeader(name = "Authorization", required = true) String jwtToken) {
        return courtBookingProductOrderService.getCenterOwnerProductOrderStatisticsToday(jwtToken);
    }

    @GetMapping("/admin/product-order/statistics")
    ResponseEntity<Object> getAdminProductOrderStatistics(@RequestHeader(name = "Authorization", required = true) String jwtToken,
            AdminProductOrderStatisticsRequestData requestData) {
        return courtBookingProductOrderService.getAdminProductOrderStatistics(jwtToken, requestData);
    }

    @GetMapping("/admin/court-booking/statistics")
    ResponseEntity<Object> getAdminCourtBookingStatistics(@RequestHeader(name = "Authorization", required = true) String jwtToken,
            AdminCourtBookingStatisticsRequestData requestData) {
        return courtBookingProductOrderService.getAdminCourtBookingStatistics(jwtToken, requestData);
    }

    @GetMapping("/admin/statistics/today")
    ResponseEntity<Object> getAdminProductOrderStatisticsToday(@RequestHeader(name = "Authorization", required = true) String jwtToken) {
        return courtBookingProductOrderService.getAdminProductOrderStatisticsToday(jwtToken);
    }
}
