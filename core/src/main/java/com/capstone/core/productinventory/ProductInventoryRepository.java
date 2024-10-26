package com.capstone.core.productinventory;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.capstone.core.table.ProductInventoryTable;

@Repository
public interface ProductInventoryRepository extends JpaRepository<ProductInventoryTable, Long> {

}
