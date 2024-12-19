package com.capstone.core.courtbooking.data.response;

import java.util.List;

import com.capstone.core.courtbooking.projection.CenterOwnerCourtBookingListProjection;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CenterOwnerCourtBookingListResponseData {
    private Integer totalPage;
    private List<CenterOwnerCourtBookingListProjection> courtBookingList;
}
