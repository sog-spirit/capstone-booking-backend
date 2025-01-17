package com.capstone.core.courtbookingproductorderpayment.data.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CreatePaymentResponseData {
    private String status;
    private String message;
    private String url;
}
