package com.capstone.core.productorder.specification;

import java.time.LocalDateTime;

import org.springframework.data.jpa.domain.Specification;

import com.capstone.core.productorder.specification.criteria.ProductOrderCriteria;
import com.capstone.core.table.ProductOrderTable;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class ProductOrderSpecification implements Specification<ProductOrderTable> {
    private final ProductOrderCriteria productOrderCriteria;

    @Override
    public Predicate toPredicate(Root<ProductOrderTable> root, CriteriaQuery<?> query,
            CriteriaBuilder criteriaBuilder) {
        return hasCenterUserId(productOrderCriteria.getCenterUserId())
                .and(hasId(productOrderCriteria.getId()))
                .and(hasUserId(productOrderCriteria.getUserId()))
                .and(createTimestampFrom(productOrderCriteria.getCreateTimestampFrom()))
                .and(createTimestampTo(productOrderCriteria.getCreateTimestampTo()))
                .and(totalFrom(productOrderCriteria.getTotalFrom()))
                .and(totalTo(productOrderCriteria.getTotalTo()))
                .and(hasCenterId(productOrderCriteria.getCenterId()))
                .and(hasStatusId(productOrderCriteria.getCenterId()))
                .toPredicate(root, query, criteriaBuilder);
    }

    public static Specification<ProductOrderTable> hasCenterUserId(Long userId) {
        return (root, query, builder) -> {
            Predicate predicate = null;
            if (userId != null) {
                predicate = builder.equal(root.get("center").get("user").get("id"), userId);
            }
            return predicate;
        };
    }

    public static Specification<ProductOrderTable> hasId(Long id) {
        return (root, query, builder) -> {
            Predicate predicate = null;
            if (id != null) {
                predicate = builder.equal(root.get("id"), id);
            }
            return predicate;
        };
    }

    
    public static Specification<ProductOrderTable> hasUserId(Long userId) {
        return (root, query, builder) -> {
            Predicate predicate = null;
            if (userId != null) {
                predicate = builder.equal(root.get("user").get("id"), userId);
            }
            return predicate;
        };
    }

    public static Specification<ProductOrderTable> createTimestampFrom(LocalDateTime createTimestampFrom) {
        return (root, query, builder) -> {
            Predicate predicate = null;
            if (createTimestampFrom != null) {
                predicate = builder.greaterThanOrEqualTo(root.get("createTimestamp"), createTimestampFrom);
            }
            return predicate;
        };
    }

    public static Specification<ProductOrderTable> createTimestampTo(LocalDateTime createTimestampTo) {
        return (root, query, builder) -> {
            Predicate predicate = null;
            if (createTimestampTo != null) {
                predicate = builder.lessThanOrEqualTo(root.get("createTimestamp"), createTimestampTo);
            }
            return predicate;
        };
    }

    public static Specification<ProductOrderTable> totalFrom(Long totalFrom) {
        return (root, query, builder) -> {
            Predicate predicate = null;
            if (totalFrom != null) {
                predicate = builder.greaterThanOrEqualTo(root.get("total"), totalFrom);
            }
            return predicate;
        };
    }

    public static Specification<ProductOrderTable> totalTo(Long totalTo) {
        return (root, query, builder) -> {
            Predicate predicate = null;
            if (totalTo != null) {
                predicate = builder.lessThanOrEqualTo(root.get("total"), totalTo);
            }
            return predicate;
        };
    }

    public static Specification<ProductOrderTable> hasCenterId(Long hasCenterId) {
        return (root, query, builder) -> {
            Predicate predicate = null;
            if (hasCenterId != null) {
                predicate = builder.equal(root.get("center").get("id"), hasCenterId);
            }
            return predicate;
        };
    }

    public static Specification<ProductOrderTable> hasStatusId(Long hasStatusId) {
        return (root, query, builder) -> {
            Predicate predicate = null;
            if (hasStatusId != null) {
                predicate = builder.equal(root.get("status").get("id"), hasStatusId);
            }
            return predicate;
        };
    }
}
