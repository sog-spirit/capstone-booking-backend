package com.capstone.core.courtbooking.specification;

import org.springframework.data.jpa.domain.Specification;

import com.capstone.core.courtbooking.specification.criteria.CourtBookingCriteria;
import com.capstone.core.table.CourtBookingTable;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class CourtBookingSpecification implements Specification<CourtBookingTable> {
    private final CourtBookingCriteria courtBookingCriteria;

    @Override
    public Predicate toPredicate(Root<CourtBookingTable> root, CriteriaQuery<?> query,
            CriteriaBuilder criteriaBuilder) {
        return onUserCourtBookingList(courtBookingCriteria.getUserId())
                .toPredicate(root, query, criteriaBuilder);
    }

    public static Specification<CourtBookingTable> onUserCourtBookingList(Long userId) {
        return (root, query, builder) -> {
            Predicate predicate = null;
            if (userId != null) {
                predicate = builder.equal(root.get("user").get("id"), userId);
            }
            return predicate;
        };
    }
}
