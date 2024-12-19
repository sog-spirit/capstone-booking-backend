package com.capstone.core.productorder.specification.criteria;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductOrderCriteria {
    private Long centerUserId;
    private Long id;
    private Long userId;
    private LocalDateTime createTimestampFrom;
    private LocalDateTime createTimestampTo;
    private Long totalFrom;
    private Long totalTo;
    private Long centerId;
    private Long statusId;
}
