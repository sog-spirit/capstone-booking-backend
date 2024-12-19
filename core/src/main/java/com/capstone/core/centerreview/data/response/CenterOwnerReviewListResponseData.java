package com.capstone.core.centerreview.data.response;

import java.util.List;

import com.capstone.core.centerreview.projection.CenterOwnerReviewListProjection;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CenterOwnerReviewListResponseData {
    private Integer totalPage;
    private List<CenterOwnerReviewListProjection> centerReviewList;
}
