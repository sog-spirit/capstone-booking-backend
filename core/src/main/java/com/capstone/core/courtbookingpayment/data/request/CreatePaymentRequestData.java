package com.capstone.core.courtbookingpayment.data.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreatePaymentRequestData {
    private Long amount;
    private String returnUrl;
}
