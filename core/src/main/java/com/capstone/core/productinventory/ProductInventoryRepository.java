package com.capstone.core.productinventory;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.capstone.core.productinventory.projection.ProductInventoryListProjection;
import com.capstone.core.productinventory.projection.UserProductOrderPageListProjection;
import com.capstone.core.table.ProductInventoryTable;

@Repository
public interface ProductInventoryRepository extends JpaRepository<ProductInventoryTable, Long> {
    List<ProductInventoryListProjection> findByUserId(Long userId);
    List<UserProductOrderPageListProjection> findUserProductOrderPageListByCenterId(Long centerId);
}
