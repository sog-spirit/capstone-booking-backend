package com.capstone.core.courtbookingproductorder.data.response;

import java.util.List;

import com.capstone.core.courtbookingproductorder.projection.CenterOwnerCourtBookingProductOrderListProjection;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CenterOwnerCourtBookingProductOrderListResponseData {
    private Integer totalPage;
    private List<CenterOwnerCourtBookingProductOrderListProjection> productOrderList;
}
