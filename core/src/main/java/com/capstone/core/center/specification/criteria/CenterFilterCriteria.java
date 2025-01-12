package com.capstone.core.center.specification.criteria;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CenterFilterCriteria {
    private Long centerOwnerId;
    private String name;
    private String address;
    private Long priceFrom;
    private Long priceTo;
    private Long status;
}
