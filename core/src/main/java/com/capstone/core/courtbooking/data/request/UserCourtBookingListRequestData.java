package com.capstone.core.courtbooking.data.request;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserCourtBookingListRequestData {
    private String idSortOrder;
    private String centerSortOrder;
    private String courtSortOrder;
    private String createTimestampSortOrder;
    private String usageDateSortOrder;
    private String usageTimeStartSortOrder;
    private String usageTimeEndSortOrder;
    private String statusSortOrder;

    private Long id;
    private LocalDateTime createTimestampFrom;
    private LocalDateTime createTimestampTo;
    private Long centerId;
    private Long courtId;
    private Long userId;
    private LocalDate usageDateFrom;
    private LocalDate usageDateTo;
    private LocalTime usageTimeStartFrom;
    private LocalTime usageTimeStartTo;
    private LocalTime usageTimeEndFrom;
    private LocalTime usageTimeEndTo;
    private Long statusId;

    private Integer pageNo;
    private Integer pageSize;
}
