package com.capstone.core.court.data.response;

import java.util.List;

import com.capstone.core.court.projection.CenterOwnerCourtListProjection;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CenterOwnerCourtListResponseData {
    private Integer totalPage;
    private List<CenterOwnerCourtListProjection> courtList;
}
