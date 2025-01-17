package com.capstone.core.courtbookingproductorderpayment;

import java.io.UnsupportedEncodingException;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.capstone.core.courtbookingproductorderpayment.data.request.CreatePaymentRequestData;
import com.capstone.core.courtbookingproductorderpayment.data.request.SavePaymentRequestData;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/product-order-payment")
@AllArgsConstructor
public class CourtBookingProductOrderPaymentController {
    private CourtBookingProductOrderPaymentService courtBookingProductOrderPaymentService;

    @GetMapping(value = "/create-payment")
    ResponseEntity<Object> createPayment(CreatePaymentRequestData requestData) throws UnsupportedEncodingException {
        return courtBookingProductOrderPaymentService.createPayment(requestData);
    }

    @PostMapping(value = "/save-payment")
    ResponseEntity<Object> savePayment(@RequestBody SavePaymentRequestData requestData) {
        return courtBookingProductOrderPaymentService.savePayment(requestData);
    }
}
