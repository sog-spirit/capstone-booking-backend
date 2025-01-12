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
        return  hasCenterId(courtFilterCriteria.getCenterId())
                .and(hasStatus(courtFilterCriteria.getStatus()))
                .toPredicate(root, query, criteriaBuilder);
    }

    static Specification<CourtTable> hasCenterId(Long centerId) {
        return (root, query, builder) -> {
            Predicate predicate = null;
            if (centerId != null) {
                predicate = builder.equal(root.get("center").get("id"), centerId);
            }
            return predicate;
        };
    }

    static Specification<CourtTable> hasStatus(Long status) {
        return (root, query, builder) -> {
            Predicate predicate = null;
            if (status != null) {
                predicate = builder.equal(root.get("status"), status);
            }
            return predicate;
        };
    }
}
