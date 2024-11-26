package com.capstone.core.product.specification;

import org.springframework.data.jpa.domain.Specification;

import com.capstone.core.product.specification.criteria.ProductCriteria;
import com.capstone.core.table.ProductTable;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class ProductSpecification implements Specification<ProductTable> {
    private final ProductCriteria productCriteria;

    @Override
    public Predicate toPredicate(Root<ProductTable> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        return hasUserId(productCriteria.getUserId())
                .toPredicate(root, query, criteriaBuilder);
    }

    public static Specification<ProductTable> hasUserId(Long userId) {
        return (root, query, builder) -> {
            Predicate predicate = null;
            if (userId != null) {
                predicate = builder.equal(root.get("user").get("id"), userId);
            }
            return predicate;
        };
    }
}
