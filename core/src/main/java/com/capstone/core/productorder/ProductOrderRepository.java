package com.capstone.core.productorder;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.capstone.core.productorder.projection.CenterOwnerProductOrderListProjection;
import com.capstone.core.table.ProductOrderTable;

@Repository
public interface ProductOrderRepository extends JpaRepository<ProductOrderTable, Long>, JpaSpecificationExecutor<ProductOrderTable> {
    List<CenterOwnerProductOrderListProjection> findCenterOwnerProductOrderListByUserId(Long userId);
}
