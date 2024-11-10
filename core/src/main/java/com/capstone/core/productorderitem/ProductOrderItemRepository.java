package com.capstone.core.productorderitem;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.capstone.core.table.ProductOrderItemTable;

@Repository
public interface ProductOrderItemRepository extends JpaRepository<ProductOrderItemTable, Long> {

}
