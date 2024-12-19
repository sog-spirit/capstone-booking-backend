package com.capstone.core.product.data.response;

import java.util.List;

import com.capstone.core.product.projection.CenterOwnerProductListProjection;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CenterOwnerProductListResponseData {
    private Integer totalPage;
    private List<CenterOwnerProductListProjection> productList;
}
