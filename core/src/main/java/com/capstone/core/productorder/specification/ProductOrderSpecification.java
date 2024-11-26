package com.capstone.core.productorder.specification;

import org.springframework.data.jpa.domain.Specification;

import com.capstone.core.productorder.specification.criteria.ProductOrderCriteria;
import com.capstone.core.table.CenterTable;
import com.capstone.core.table.ProductOrderTable;
import com.capstone.core.table.UserTable;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class ProductOrderSpecification implements Specification<ProductOrderTable> {
    private final ProductOrderCriteria productOrderCriteria;

    @Override
    public Predicate toPredicate(Root<ProductOrderTable> root, CriteriaQuery<?> query,
            CriteriaBuilder criteriaBuilder) {
        return onCenterUserIdJoin(productOrderCriteria.getCenterUserId())
                .toPredicate(root, query, criteriaBuilder);
    }

    public static Specification<ProductOrderTable> onCenterUserIdJoin(Long userId) {
        return (root, query, builder) -> {
            Predicate predicate = null;
            if (userId != null) {
                Join<ProductOrderTable, CenterTable> centerJoin = root.join("center", JoinType.INNER);
                Join<CenterTable, UserTable> centerUserJoin = centerJoin.join("user", JoinType.INNER);
                predicate = builder.equal(centerUserJoin.get("id"), userId);
            }
            return predicate;
        };
    }
}
