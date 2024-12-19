package com.capstone.core.centerreview.data.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CenterOwnerReviewListRequestData {
    private String idSortOrder;
    private String userSortOrder;
    private String centerSortOrder;

    private Long id;
    private Long centerId;
    private Long userId;

    private Integer pageNo;
    private Integer pageSize;
}
