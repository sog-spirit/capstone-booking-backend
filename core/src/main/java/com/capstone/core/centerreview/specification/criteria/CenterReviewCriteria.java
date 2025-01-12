package com.capstone.core.centerreview.specification.criteria;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CenterReviewCriteria {
    private Long centerOwnerId;
    private Long id;
    private Long userId;
    private Long centerId;
    private Long status;
}
