package com.capstone.core.productinventory.specification;

import org.springframework.data.jpa.domain.Specification;

import com.capstone.core.productinventory.specification.criteria.ProductInventoryFilterCriteria;
import com.capstone.core.table.ProductInventoryTable;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class ProductInventorySpecification implements Specification<ProductInventoryTable> {
    private final ProductInventoryFilterCriteria productInventoryFilterCriteria;

    @Override
    public Predicate toPredicate(Root<ProductInventoryTable> root, CriteriaQuery<?> query,
            CriteriaBuilder criteriaBuilder) {
        return hasUserId(productInventoryFilterCriteria.getUserId())
                .and(hasCenterId(productInventoryFilterCriteria.getCenterId()))
                .and(hasProductId(productInventoryFilterCriteria.getProductId()))
                .toPredicate(root, query, criteriaBuilder);
    }

    public static Specification<ProductInventoryTable> hasCenterId(Long centerId) {
        return (root, query, builder) -> {
            Predicate predicate = null;
            if (centerId != null) {
                predicate = builder.equal(root.get("center").get("id"), centerId);
            }
            return predicate;
        };
    }

    public static Specification<ProductInventoryTable> hasProductId(Long productId) {
        return (root, query, builder) -> {
            Predicate predicate = null;
            if (productId != null) {
                predicate = builder.equal(root.get("product").get("id"), productId);
            }
            return predicate;
        };
    }

    public static Specification<ProductInventoryTable> hasUserId(Long userId) {
        return (root, query, builder) -> {
            Predicate predicate = null;
            if (userId != null) {
                predicate = builder.equal(root.get("user").get("id"), userId);
            }
            return predicate;
        };
    }
}
