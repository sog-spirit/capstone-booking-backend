package com.capstone.core.productimage;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.capstone.core.table.ProductImageTable;

@Repository
public interface ProductImageRepository extends JpaRepository<ProductImageTable, Long> {
    Optional<ProductImageTable> findByProductId(Long productId);
}
