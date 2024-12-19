package com.capstone.core.courtbooking.data.response;

import java.util.List;

import com.capstone.core.courtbooking.projection.UserCourtBookingListProjection;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserCourtBookingListResponseData {
    private Integer totalPage;
    private List<UserCourtBookingListProjection> courtBookingList;
}
