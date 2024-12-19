package com.capstone.core.centerreview.data.response;

import java.util.List;

import com.capstone.core.centerreview.projection.UserCenterReviewListProjection;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserCenterReviewListResponseData {
    private Integer totalPage;
    private List<UserCenterReviewListProjection> centerReviewList;
}
