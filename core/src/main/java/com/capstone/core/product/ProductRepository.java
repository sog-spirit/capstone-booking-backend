package com.capstone.core.product;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.capstone.core.table.ProductTable;

@Repository
public interface ProductRepository extends JpaRepository<ProductTable, Long> {

}
