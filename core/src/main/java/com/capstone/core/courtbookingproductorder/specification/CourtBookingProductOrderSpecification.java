package com.capstone.core.courtbookingproductorder.specification;

import java.time.LocalDateTime;

import org.springframework.data.jpa.domain.Specification;

import com.capstone.core.courtbookingproductorder.specification.criteria.CourtBookingProductOrderCriteria;
import com.capstone.core.table.CourtBookingProductOrderTable;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class CourtBookingProductOrderSpecification implements Specification<CourtBookingProductOrderTable> {
    private final CourtBookingProductOrderCriteria courtBookingProductOrderCriteria;

    @Override
    public Predicate toPredicate(Root<CourtBookingProductOrderTable> root, CriteriaQuery<?> query,
            CriteriaBuilder criteriaBuilder) {
        return hasId(courtBookingProductOrderCriteria.getId())
                .and(hasCourtBookingId(courtBookingProductOrderCriteria.getCourtBookingId()))
                .and(hasProductInventoryId(courtBookingProductOrderCriteria.getProductInventoryId()))
                .and(hasUserId(courtBookingProductOrderCriteria.getUserId()))
                .and(hasCreateTimestampFrom(courtBookingProductOrderCriteria.getCreateTimestampFrom()))
                .and(hasCreateTimestampTo(courtBookingProductOrderCriteria.getCreateTimestampTo()))
                .and(hasQuantityFrom(courtBookingProductOrderCriteria.getQuantityFrom()))
                .and(hasQuantityTo(courtBookingProductOrderCriteria.getQuantityTo()))
                .and(hasFeeFrom(courtBookingProductOrderCriteria.getFeeFrom()))
                .and(hasFeeTo(courtBookingProductOrderCriteria.getFeeTo()))
                .and(hasStatusId(courtBookingProductOrderCriteria.getStatusId()))
                .and(hasCenterOwnerId(courtBookingProductOrderCriteria.getCenterOwnerId()))
                .toPredicate(root, query, criteriaBuilder);
    }

    static Specification<CourtBookingProductOrderTable> hasId(Long id) {
        return (root, query, builder) -> {
            Predicate predicate = null;
            if (id != null) {
                predicate = builder.equal(root.get("id"), id);
            }
            return predicate;
        };
    }

    static Specification<CourtBookingProductOrderTable> hasCourtBookingId(Long courtBookingId) {
        return (root, query, builder) -> {
            Predicate predicate = null;
            if (courtBookingId != null) {
                predicate = builder.equal(root.get("courtBooking").get("id"), courtBookingId);
            }
            return predicate;
        };
    }

    static Specification<CourtBookingProductOrderTable> hasProductInventoryId(Long productInventoryId) {
        return (root, query, builder) -> {
            Predicate predicate = null;
            if (productInventoryId != null) {
                predicate = builder.equal(root.get("productInventory").get("id"), productInventoryId);
            }
            return predicate;
        };
    }
    
    static Specification<CourtBookingProductOrderTable> hasUserId(Long userId) {
        return (root, query, builder) -> {
            Predicate predicate = null;
            if (userId != null) {
                predicate = builder.equal(root.get("user").get("id"), userId);
            }
            return predicate;
        };
    }
    
    static Specification<CourtBookingProductOrderTable> hasCreateTimestampFrom(LocalDateTime createTimestampFrom) {
        return (root, query, builder) -> {
            Predicate predicate = null;
            if (createTimestampFrom != null) {
                predicate = builder.greaterThanOrEqualTo(root.get("createTimestamp"), createTimestampFrom);
            }
            return predicate;
        };
    }
    
    static Specification<CourtBookingProductOrderTable> hasCreateTimestampTo(LocalDateTime createTimestampTo) {
        return (root, query, builder) -> {
            Predicate predicate = null;
            if (createTimestampTo != null) {
                predicate = builder.lessThanOrEqualTo(root.get("createTimestamp"), createTimestampTo);
            }
            return predicate;
        };
    }
    
    static Specification<CourtBookingProductOrderTable> hasQuantityFrom(Long quantityFrom) {
        return (root, query, builder) -> {
            Predicate predicate = null;
            if (quantityFrom != null) {
                predicate = builder.greaterThanOrEqualTo(root.get("quantity"), quantityFrom);
            }
            return predicate;
        };
    }
    
    static Specification<CourtBookingProductOrderTable> hasQuantityTo(Long quantityTo) {
        return (root, query, builder) -> {
            Predicate predicate = null;
            if (quantityTo != null) {
                predicate = builder.lessThanOrEqualTo(root.get("quantity"), quantityTo);
            }
            return predicate;
        };
    }
    
    static Specification<CourtBookingProductOrderTable> hasFeeFrom(Long feeFrom) {
        return (root, query, builder) -> {
            Predicate predicate = null;
            if (feeFrom != null) {
                predicate = builder.greaterThanOrEqualTo(root.get("fee"), feeFrom);
            }
            return predicate;
        };
    }
    
    static Specification<CourtBookingProductOrderTable> hasFeeTo(Long feeTo) {
        return (root, query, builder) -> {
            Predicate predicate = null;
            if (feeTo != null) {
                predicate = builder.lessThanOrEqualTo(root.get("fee"), feeTo);
            }
            return predicate;
        };
    }

    static Specification<CourtBookingProductOrderTable> hasStatusId(Long statusId) {
        return (root, query, builder) -> {
            Predicate predicate = null;
            if (statusId != null) {
                predicate = builder.equal(root.get("status").get("id"), statusId);
            }
            return predicate;
        };
    }

    static Specification<CourtBookingProductOrderTable> hasCenterOwnerId(Long centerOwnerId) {
        return (root, query, builder) -> {
            Predicate predicate = null;
            if (centerOwnerId != null) {
                predicate = builder.equal(root.get("productInventory").get("center").get("user").get("id"), centerOwnerId);
            }
            return predicate;
        };
    }
}
