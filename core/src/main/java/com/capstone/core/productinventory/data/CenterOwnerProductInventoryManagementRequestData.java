package com.capstone.core.productinventory.data;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CenterOwnerProductInventoryManagementRequestData {
    private String centerIdFilter;
    private String productIdFilter;
    private String idSortOrder;
    private String productSortOrder;
    private String centerSortOrder;
    private String quantitySortOrder;
    private Integer pageNo;
    private Integer pageSize;
}
