package com.capstone.core.centerreview.data.response;

import java.util.List;

import com.capstone.core.centerreview.projection.AdminCenterReviewListProjection;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdminCenterReviewListResponseData {
    private Integer totalPage;
    private List<AdminCenterReviewListProjection> centerReviewList;
}
