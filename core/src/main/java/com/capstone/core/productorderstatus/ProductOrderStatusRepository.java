package com.capstone.core.productorderstatus;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.capstone.core.productorderstatus.projection.ProductOrderStatusProjection;
import com.capstone.core.table.ProductOrderStatusTable;

@Repository
public interface ProductOrderStatusRepository extends JpaRepository<ProductOrderStatusTable, Long> {
    List<ProductOrderStatusProjection> findProductOrderStatusBy();
}
