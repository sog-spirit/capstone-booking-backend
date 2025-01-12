package com.capstone.core.courtbookingproductorder.data.request;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CenterOwnerCourtBookingProductOrderListRequestData {
    private String idSortOrder;
    private String courtBookingIdSortOrder;
    private String productInventoryIdSortOrder;
    private String userSortOrder;
    private String createTimestampSortOrder;
    private String quantitySortOrder;
    private String feeSortOrder;
    private String statusSortOrder;

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

    private Integer pageNo;
    private Integer pageSize;
}
