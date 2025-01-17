package com.capstone.core.courtbookingpayment.data.request;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GetPaymentInfoRequestData {
    private String txnRef;
    private String transactionNo;
    private LocalDate transDate;
}
