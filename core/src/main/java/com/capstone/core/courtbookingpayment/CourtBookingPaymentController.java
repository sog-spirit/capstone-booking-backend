package com.capstone.core.courtbookingpayment;

import java.io.UnsupportedEncodingException;

import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.capstone.core.courtbookingpayment.data.request.CreatePaymentRequestData;
import com.capstone.core.courtbookingpayment.data.request.SavePaymentRequestData;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/court-booking-payment")
@AllArgsConstructor
public class CourtBookingPaymentController {
    private CourtBookingPaymentService paymentService;

    @GetMapping(value = "/create-payment")
    ResponseEntity<Object> createPayment(CreatePaymentRequestData requestData) throws UnsupportedEncodingException {
        return paymentService.createPayment(requestData);
    }

    @PostMapping(value = "/save-payment")
    @Transactional
    ResponseEntity<Object> savePayment(@RequestBody SavePaymentRequestData requestData) {
        return paymentService.savePayment(requestData);
    }
}
