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
                .and(containUsername(employeeListCriteria.getUsername()))
                .and(containFirstName(employeeListCriteria.getFirstName()))
                .and(containLastName(employeeListCriteria.getLastName()))
                .and(containEmail(employeeListCriteria.getEmail()))
                .and(containPhone(employeeListCriteria.getPhone()))
                .toPredicate(root, query, criteriaBuilder);
    }

    static Specification<EmployeeListTable> onJoinCenterOwner(Long centerOwnerId) {
        return (root, query, builder) -> {
            Predicate predicate = null;
            if (centerOwnerId != null) {
                predicate = builder.equal(root.get("centerOwner").get("id"), centerOwnerId);
            }
            return predicate;
        };
    }

    static Specification<EmployeeListTable> containUsername(String username) {
        return (root, query, builder) -> {
            Predicate predicate = null;
            if (username != null) {
                predicate = builder.like(root.get("employee").get("username"), builder.literal("%" + username + "%"));
            }
            return predicate;
        };
    }

    static Specification<EmployeeListTable> containFirstName(String firstName) {
        return (root, query, builder) -> {
            Predicate predicate = null;
            if (firstName != null) {
                predicate = builder.like(root.get("employee").get("firstName"), builder.literal("%" + firstName + "%"));
            }
            return predicate;
        };
    }

    static Specification<EmployeeListTable> containLastName(String lastName) {
        return (root, query, builder) -> {
            Predicate predicate = null;
            if (lastName != null) {
                predicate = builder.like(root.get("employee").get("lastName"), builder.literal("%" + lastName + "%"));
            }
            return predicate;
        };
    }

    static Specification<EmployeeListTable> containPhone(String phone) {
        return (root, query, builder) -> {
            Predicate predicate = null;
            if (phone != null) {
                predicate = builder.like(root.get("employee").get("phone"), builder.literal("%" + phone + "%"));
            }
            return predicate;
        };
    }

    static Specification<EmployeeListTable> containEmail(String email) {
        return (root, query, builder) -> {
            Predicate predicate = null;
            if (email != null) {
                predicate = builder.like(root.get("employee").get("email"), builder.literal("%" + email + "%"));
            }
            return predicate;
        };
    }
}
