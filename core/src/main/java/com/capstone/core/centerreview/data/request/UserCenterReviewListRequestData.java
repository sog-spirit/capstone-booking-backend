package com.capstone.core.centerreview.data.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserCenterReviewListRequestData {
    private String idSortOrder;
    private String centerSortOrder;
    private String createTimestampSortOrder;
    private String updateTimestampSortOrder;

    private Long id;
    private Long centerId;

    private Integer pageNo;
    private Integer pageSize;
}
