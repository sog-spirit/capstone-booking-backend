package com.capstone.core.productorder.data.request;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CenterOwnerProductOrderListRequestData {
    private String idSortOrder;
    private String userSortOrder;
    private String createTimestampSortOrder;
    private String totalSortOrder;
    private String centerSortOrder;
    private String statusSortOrder;

    private Long id;
    private Long userId;
    private LocalDateTime createTimestampFrom;
    private LocalDateTime createTimestampTo;
    private Long totalFrom;
    private Long totalTo;
    private Long centerId;
    private Long statusId;

    private Integer pageNo;
    private Integer pageSize;
}
