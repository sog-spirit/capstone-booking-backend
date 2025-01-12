package com.capstone.core.user.specification;

import org.springframework.data.jpa.domain.Specification;

import com.capstone.core.table.UserTable;
import com.capstone.core.user.specification.criteria.UserCriteria;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class UserSpecification implements Specification<UserTable> {
    private final UserCriteria criteria;

    @Override
    public Predicate toPredicate(Root<UserTable> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        return likeUsername(criteria.getUsernameFilter())
                .and(likeEmail(criteria.getEmailFilter()))
                .and(likePhone(criteria.getPhoneFilter()))
                .and(likeFirstName(criteria.getFirstNameFilter()))
                .and(likeLastName(criteria.getLastNameFilter()))
                .toPredicate(root, query, criteriaBuilder);
    }

    static Specification<UserTable> likeUsername(String username) {
        return (root, query, builder) -> {
            Predicate predicate = null;
            if (username != null) {
                predicate = builder.like(root.get("username"), builder.literal("%" + username + "%"));
            }
            return predicate;
        };
    }

    static Specification<UserTable> likeEmail(String email) {
        return (root, query, builder) -> {
            Predicate predicate = null;
            if (email != null) {
                predicate = builder.like(root.get("email"), builder.literal("%" + email + "%"));
            }
            return predicate;
        };
    }

    static Specification<UserTable> likePhone(String phone) {
        return (root, query, builder) -> {
            Predicate predicate = null;
            if (phone != null) {
                predicate = builder.like(root.get("phone"), builder.literal("%" + phone + "%"));
            }
            return predicate;
        };
    }

    static Specification<UserTable> likeFirstName(String firstName) {
        return (root, query, builder) -> {
            Predicate predicate = null;
            if (firstName != null) {
                predicate = builder.like(root.get("firstName"), builder.literal("%" + firstName + "%"));
            }
            return predicate;
        };
    }

    static Specification<UserTable> likeLastName(String lastName) {
        return (root, query, builder) -> {
            Predicate predicate = null;
            if (lastName != null) {
                predicate = builder.like(root.get("lastName"), builder.literal("%" + lastName + "%"));
            }
            return predicate;
        };
    }
}
