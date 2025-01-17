package com.capstone.core.courtbookingproductorderpayment.data.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SavePaymentRequestData {
    private Long productOrderId;
    private String vnpAmount;
    private String vnpPayDate;
    private String vnpTransactionNo;
    private String vnpTxnRef;
}
