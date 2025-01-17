package com.capstone.core.courtbookingproductorderdetail;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.capstone.core.courtbookingproductorderdetail.data.request.CourtBookingProductOrderDetailRequestData;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/court_booking_product_order_detail")
@AllArgsConstructor
public class CourtBookingProductOrderDetailController {
    private CourtBookingProductOrderDetailService courtBookingProductOrderDetailService;

    @GetMapping(value = "/list")
    ResponseEntity<Object> getCourtBookingProductOrderDetail(@RequestHeader(name = "Authorization", required = true) String jwtToken,
            CourtBookingProductOrderDetailRequestData requestData) {
        return courtBookingProductOrderDetailService.getCourtBookingProductOrderDetail(jwtToken, requestData);
    }
}
