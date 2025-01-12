package com.capstone.core.courtbookingproductorder.specification.criteria;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CourtBookingProductOrderCriteria {
    private Long id;
    private Long courtBookingId;
    private Long productInventoryId;
    private Long userId;
    private LocalDateTime createTimestampFrom;
    private LocalDateTime createTimestampTo;
    private Long quantityFrom;
    private Long quantityTo;
    private Long feeFrom;
    private Long feeTo;
    private Long statusId;
    private Long centerOwnerId;
}
