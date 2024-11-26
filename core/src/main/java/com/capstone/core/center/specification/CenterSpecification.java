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
                .toPredicate(root, query, criteriaBuilder);
    }

    static Specification<CenterTable> hasCenterOwnerId(Long userId) {
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
