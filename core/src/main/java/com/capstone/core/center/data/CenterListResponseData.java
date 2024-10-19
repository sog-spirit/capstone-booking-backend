package com.capstone.core.center.data;

import java.util.List;

import com.capstone.core.center.projection.CenterListProjection;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CenterListResponseData {
    private Integer totalPage;
    private List<CenterListProjection> centerList;
}
