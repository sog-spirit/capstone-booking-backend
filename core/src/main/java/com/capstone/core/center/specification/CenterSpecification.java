package com.capstone.core.center.specification;

import org.springframework.data.jpa.domain.Specification;

import com.capstone.core.center.specification.criteria.CenterFilterCriteria;
import com.capstone.core.table.CenterTable;
import com.capstone.core.table.UserTable;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Fetch;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class CenterSpecification implements Specification<CenterTable> {
    private final CenterFilterCriteria centerFilterCriteria;

    @Override
    public Predicate toPredicate(Root<CenterTable> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        return hasCenterOwnerId(centerFilterCriteria.getCenterOwnerId())
                .and(containName(centerFilterCriteria.getName()))
                .and(containAddress(centerFilterCriteria.getAddress()))
                .and(priceFrom(centerFilterCriteria.getPriceFrom()))
                .and(priceTo(centerFilterCriteria.getPriceTo()))
                .and(hasStatus(centerFilterCriteria.getStatus()))
                .toPredicate(root, query, criteriaBuilder);
    }

    static Specification<CenterTable> hasCenterOwnerId(Long userId) {
        return (root, query, builder) -> {
            Predicate predicate = null;
            if (userId != null) {
                predicate = builder.equal(root.get("user").get("id"), userId);
            }
            return predicate;
        };
    }

    static Specification<CenterTable> containName(String name) {
        return (root, query, builder) -> {
            Predicate predicate = null;
            if (name != null) {
                predicate = builder.like(root.get("name"), builder.literal("%" + name + "%"));
            }
            return predicate;
        };
    }

    static Specification<CenterTable> containAddress(String address) {
        return (root, query, builder) -> {
            Predicate predicate = null;
            if (address != null) {
                predicate = builder.like(root.get("address"), builder.literal("%" + address + "%"));
            }
            return predicate;
        };
    }

    static Specification<CenterTable> priceFrom(Long price) {
        return (root, query, builder) -> {
            Predicate predicate = null;
            if (price != null) {
                predicate = builder.greaterThanOrEqualTo(root.get("price"), price);
            }
            return predicate;
        };
    }

    static Specification<CenterTable> priceTo(Long price) {
        return (root, query, builder) -> {
            Predicate predicate = null;
            if (price != null) {
                predicate = builder.lessThanOrEqualTo(root.get("price"), price);
            }
            return predicate;
        };
    }

    static Specification<CenterTable> hasStatus(Long status) {
        return (root, query, builder) -> {
            Predicate predicate = null;
            if (status != null) {
                predicate = builder.equal(root.get("status"), status);
            }
            return predicate;
        };
    }
}
