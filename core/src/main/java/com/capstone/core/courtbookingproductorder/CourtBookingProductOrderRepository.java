package com.capstone.core.courtbookingproductorder;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.capstone.core.courtbookingproductorder.projection.CenterOwnerCourtBookingProductOrderDetailListProjection;
import com.capstone.core.courtbookingproductorder.projection.UserCourtBookingProductOrderDetailListProjection;
import com.capstone.core.table.CourtBookingProductOrderTable;

@Repository
public interface CourtBookingProductOrderRepository extends JpaRepository<CourtBookingProductOrderTable, Long>, JpaSpecificationExecutor<CourtBookingProductOrderTable> {
    List<UserCourtBookingProductOrderDetailListProjection> findUserCourtBookingProductOrderDetailListByCourtBookingIdAndUserIdAndStatusNot(Long courtBookingId, Long userId, Long status);
    List<CenterOwnerCourtBookingProductOrderDetailListProjection> findCenterOwnerCourtBookingProductOrderDetailListByCourtBookingIdAndStatusNot(Long courtBookingId, Long status);
}
