package com.capstone.core.center.data.response;

import java.util.List;

import com.capstone.core.center.projection.CenterOwnerCenterListProjection;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CenterOwnerCenterListResposneData {
    private Integer totalPage;
    private List<CenterOwnerCenterListProjection> centerList;
}
