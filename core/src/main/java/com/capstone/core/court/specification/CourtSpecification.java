package com.capstone.core.court.specification;

import org.springframework.data.jpa.domain.Specification;

import com.capstone.core.court.specification.criteria.CourtFilterCriteria;
import com.capstone.core.table.CourtTable;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class CourtSpecification implements Specification<CourtTable> {
    private final CourtFilterCriteria courtFilterCriteria;

    @Override
    public Predicate toPredicate(Root<CourtTable> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        return hasCenterOwnerId(courtFilterCriteria.getCenterOwnerId())
                .and(hasCenterId(courtFilterCriteria.getCenterId()))
                .toPredicate(root, query, criteriaBuilder);
    }

    static Specification<CourtTable> hasCenterOwnerId(Long centerOwnerId) {
        return (root, query, builder) -> {
            Predicate predicate = null;
            if (centerOwnerId != null) {
                if (query.getResultType() != Long.class) {
                    root.fetch("user", JoinType.INNER);
                }
                predicate = builder.equal(root.get("user").get("id"), centerOwnerId);
            }
            return predicate;
        };
    }

    static Specification<CourtTable> hasCenterId(Long centerId) {
        return (root, query, builder) -> {
            Predicate predicate = null;
            if (centerId != null) {
                if (query.getResultType() != Long.class) {
                    root.fetch("center", JoinType.INNER);
                }
                predicate = builder.equal(root.get("center").get("id"), centerId);
            }
            return predicate;
        };
    }
}
