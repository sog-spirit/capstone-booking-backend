package com.capstone.core.center.data.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserCenterListRequestData {
    private Integer pageNo;
    private Integer pageSize;

    private String name;
    private String address;
    private Long priceFrom;
    private Long priceTo;
}
