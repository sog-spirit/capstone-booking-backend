package com.capstone.core.product.data.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CenterOwnerProductListRequestData {
    private Integer pageNo;
    private Integer pageSize;
}
