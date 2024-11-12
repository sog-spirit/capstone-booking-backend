package com.capstone.core.productorder;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.capstone.core.productorder.projection.CenterOwnerProductOrderListProjection;
import com.capstone.core.table.ProductOrderTable;

@Repository
public interface ProductOrderRepository extends JpaRepository<ProductOrderTable, Long> {
    List<CenterOwnerProductOrderListProjection> findCenterOwnerProductOrderListByUserId(Long userId);
}
