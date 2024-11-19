package com.capstone.core.productinventory.data;

import java.util.List;

import com.capstone.core.productinventory.projection.CenterOwnerProductInventoryListProjection;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CenterOwnerProductInventoryManagementResponseData {
    private Integer totalPage;
    private List<CenterOwnerProductInventoryListProjection> productInventoryList;
}
