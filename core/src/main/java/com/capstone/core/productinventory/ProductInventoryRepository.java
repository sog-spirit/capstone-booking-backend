package com.capstone.core.productinventory;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.capstone.core.productinventory.projection.CenterOwnerProductInventoryCenterFilterListProjection;
import com.capstone.core.productinventory.projection.CenterOwnerProductInventoryListProjection;
import com.capstone.core.productinventory.projection.CenterOwnerProductInventoryProductFilterListInterface;
import com.capstone.core.productinventory.projection.UserProductOrderPageListProjection;
import com.capstone.core.table.ProductInventoryTable;

@Repository
public interface ProductInventoryRepository extends JpaRepository<ProductInventoryTable, Long>, JpaSpecificationExecutor<ProductInventoryTable> {
    List<CenterOwnerProductInventoryListProjection> findByCenterUserId(Long userId);
    List<UserProductOrderPageListProjection> findUserProductOrderPageListByCenterId(Long centerId);
    List<CenterOwnerProductInventoryCenterFilterListProjection> findCenterOwnerProductInventoryCenterFilterListDistinctCenterIdByCenterUserIdAndCenterNameContaining(Long userId, String centerName);
    List<CenterOwnerProductInventoryProductFilterListInterface> findCenterOwnerProductInventoryProductFilterListDistinctProductIdByCenterUserIdAndProductNameContaining(Long userId, String centerName);
}
