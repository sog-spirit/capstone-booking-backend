package com.capstone.core.productorder.data.response;

import java.util.List;

import com.capstone.core.productorder.projection.CenterOwnerProductOrderListProjection;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CenterOwnerProductOrderListResponseData {
    private Integer totalPage;
    private List<CenterOwnerProductOrderListProjection> productOrderList;
}
