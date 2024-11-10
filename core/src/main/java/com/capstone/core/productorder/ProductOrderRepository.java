package com.capstone.core.productorder;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.capstone.core.table.ProductOrderTable;

@Repository
public interface ProductOrderRepository extends JpaRepository<ProductOrderTable, Long> {

}
