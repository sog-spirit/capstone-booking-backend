package com.capstone.core.employeelist.specification;

import org.springframework.data.jpa.domain.Specification;

import com.capstone.core.employeelist.specification.criteria.EmployeeListCriteria;
import com.capstone.core.table.EmployeeListTable;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class EmployeeListSpecification implements Specification<EmployeeListTable> {
    private final EmployeeListCriteria employeeListCriteria;

    @Override
    public Predicate toPredicate(Root<EmployeeListTable> root, CriteriaQuery<?> query,
            CriteriaBuilder criteriaBuilder) {
        return onJoinCenterOwner(employeeListCriteria.getCenterOwnerId())
                .toPredicate(root, query, criteriaBuilder);
    }

    public static Specification<EmployeeListTable> onJoinCenterOwner(Long centerOwnerId) {
        return (root, query, builder) -> {
            Predicate predicate = null;
            if (centerOwnerId != null) {
                if (Long.class != query.getResultType()) {
                    root.fetch("employee", JoinType.INNER);
                }
                predicate = builder.equal(root.get("centerOwner").get("id"), centerOwnerId);
            }
            return predicate;
        };
    }
}
