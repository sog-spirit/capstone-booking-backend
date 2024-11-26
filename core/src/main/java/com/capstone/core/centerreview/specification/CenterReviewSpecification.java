package com.capstone.core.centerreview.specification;

import org.springframework.data.jpa.domain.Specification;

import com.capstone.core.centerreview.specification.criteria.CenterReviewCriteria;
import com.capstone.core.table.CenterReviewTable;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class CenterReviewSpecification implements Specification<CenterReviewTable> {
    private final CenterReviewCriteria centerReviewCriteria;

    @Override
    public Predicate toPredicate(Root<CenterReviewTable> root, CriteriaQuery<?> query,
            CriteriaBuilder criteriaBuilder) {
        return hasCenterOwnerId(centerReviewCriteria.getCenterOwnerId())
                .and(hasUserId(centerReviewCriteria.getUserId()))
                .toPredicate(root, query, criteriaBuilder);
    }

    static Specification<CenterReviewTable> hasCenterOwnerId(Long centerOwnerId) {
        return (root, query, builder) -> {
            Predicate predicate = null;
            if (centerOwnerId != null) {
                if (Long.class != query.getResultType()) {
                    root.fetch("center", JoinType.INNER)
                            .fetch("user", JoinType.INNER);
                }
                predicate = builder.equal(root.get("center").get("user").get("id"), centerOwnerId);
            }
            return predicate;
        };
    }

    static Specification<CenterReviewTable> hasUserId(Long userId) {
        return (root, query, builder) -> {
            Predicate predicate = null;
            if (userId != null) {
                if (Long.class != query.getResultType()) {
                    root.fetch("user", JoinType.INNER);
                }
                predicate = builder.equal(root.get("user").get("id"), userId);
            }
            return predicate;
        };
    }
}
