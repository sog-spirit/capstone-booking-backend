package com.capstone.core.courtbooking.specification;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

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
        return hasUserId(courtBookingCriteria.getUserId())
                .and(hasCenterOwnerId(courtBookingCriteria.getCenterUserId()))
                .and(hasId(courtBookingCriteria.getId()))
                .and(hasCreateTimestampFrom(courtBookingCriteria.getCreateTimestampFrom()))
                .and(hasCreateTimestampTo(courtBookingCriteria.getCreateTimestampTo()))
                .and(hasCenterId(courtBookingCriteria.getCenterId()))
                .and(hasCourtId(courtBookingCriteria.getCourtId()))
                .and(hasUsageDateFrom(courtBookingCriteria.getUsageDateFrom()))
                .and(hasUsageDateTo(courtBookingCriteria.getUsageDateTo()))
                .and(hasUsageTimeStartFrom(courtBookingCriteria.getUsageTimeStartFrom()))
                .and(hasUsageTimeStartTo(courtBookingCriteria.getUsageTimeStartTo()))
                .and(hasUsageTimeEndFrom(courtBookingCriteria.getUsageTimeEndFrom()))
                .and(hasUsageTimeEndTo(courtBookingCriteria.getUsageTimeEndTo()))
                .and(hasStatusId(courtBookingCriteria.getStatusId()))
                .toPredicate(root, query, criteriaBuilder);
    }

    static Specification<CourtBookingTable> hasUserId(Long userId) {
        return (root, query, builder) -> {
            Predicate predicate = null;
            if (userId != null) {
                predicate = builder.equal(root.get("court").get("center").get("user").get("id"), userId);
            }
            return predicate;
        };
    }

    static Specification<CourtBookingTable> hasCenterOwnerId(Long userId) {
        return (root, query, builder) -> {
            Predicate predicate = null;
            if (userId != null) {
                predicate = builder.equal(root.get("court").get("center").get("user").get("id"), userId);
            }
            return predicate;
        };
    }

    static Specification<CourtBookingTable> hasId(Long id) {
        return (root, query, builder) -> {
            Predicate predicate = null;
            if (id != null) {
                predicate = builder.equal(root.get("id"), id);
            }
            return predicate;
        };
    }

    static Specification<CourtBookingTable> hasCreateTimestampFrom(LocalDateTime createTimestampFrom) {
        return (root, query, builder) -> {
            Predicate predicate = null;
            if (createTimestampFrom != null) {
                predicate = builder.greaterThanOrEqualTo(root.get("createTimestamp"), createTimestampFrom);
            }
            return predicate;
        };
    }

    static Specification<CourtBookingTable> hasCreateTimestampTo(LocalDateTime createTimestampTo) {
        return (root, query, builder) -> {
            Predicate predicate = null;
            if (createTimestampTo != null) {
                predicate = builder.lessThanOrEqualTo(root.get("createTimestamp"), createTimestampTo);
            }
            return predicate;
        };
    }

    static Specification<CourtBookingTable> hasCenterId(Long centerId) {
        return (root, query, builder) -> {
            Predicate predicate = null;
            if (centerId != null) {
                predicate = builder.equal(root.get("court").get("center").get("id"), centerId);
            }
            return predicate;
        };
    }

    static Specification<CourtBookingTable> hasCourtId(Long courtId) {
        return (root, query, builder) -> {
            Predicate predicate = null;
            if (courtId != null) {
                predicate = builder.equal(root.get("court").get("id"), courtId);
            }
            return predicate;
        };
    }

    static Specification<CourtBookingTable> hasUsageDateFrom(LocalDate usageDateFrom) {
        return (root, query, builder) -> {
            Predicate predicate = null;
            if (usageDateFrom != null) {
                predicate = builder.greaterThanOrEqualTo(root.get("usageDate"), usageDateFrom);
            }
            return predicate;
        };
    }

    static Specification<CourtBookingTable> hasUsageDateTo(LocalDate usageDateTo) {
        return (root, query, builder) -> {
            Predicate predicate = null;
            if (usageDateTo != null) {
                predicate = builder.lessThanOrEqualTo(root.get("usageDate"), usageDateTo);
            }
            return predicate;
        };
    }

    static Specification<CourtBookingTable> hasUsageTimeStartFrom(LocalTime usageTimeStartFrom) {
        return (root, query, builder) -> {
            Predicate predicate = null;
            if (usageTimeStartFrom != null) {
                predicate = builder.greaterThanOrEqualTo(root.get("usageTimeStart"), usageTimeStartFrom);
            }
            return predicate;
        };
    }

    static Specification<CourtBookingTable> hasUsageTimeStartTo(LocalTime usageTimeStartTo) {
        return (root, query, builder) -> {
            Predicate predicate = null;
            if (usageTimeStartTo != null) {
                predicate = builder.lessThanOrEqualTo(root.get("usageTimeStart"), usageTimeStartTo);
            }
            return predicate;
        };
    }

    static Specification<CourtBookingTable> hasUsageTimeEndFrom(LocalTime usageTimeEndFrom) {
        return (root, query, builder) -> {
            Predicate predicate = null;
            if (usageTimeEndFrom != null) {
                predicate = builder.greaterThanOrEqualTo(root.get("usageTimeEnd"), usageTimeEndFrom);
            }
            return predicate;
        };
    }

    static Specification<CourtBookingTable> hasUsageTimeEndTo(LocalTime usageTimeEndTo) {
        return (root, query, builder) -> {
            Predicate predicate = null;
            if (usageTimeEndTo != null) {
                predicate = builder.lessThanOrEqualTo(root.get("usageTimeEnd"), usageTimeEndTo);
            }
            return predicate;
        };
    }

    static Specification<CourtBookingTable> hasStatusId(Long statusId) {
        return (root, query, builder) -> {
            Predicate predicate = null;
            if (statusId != null) {
                predicate = builder.equal(root.get("status"), statusId);
            }
            return predicate;
        };
    }
}
