package com.capstone.core.center.data.response;

import java.util.List;

import com.capstone.core.center.projection.CenterListProjection;
import com.capstone.core.center.projection.UserCenterListProjection;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserCenterListResponseData {
    private Integer totalPage;
    private List<UserCenterListProjection> centerList;
}
