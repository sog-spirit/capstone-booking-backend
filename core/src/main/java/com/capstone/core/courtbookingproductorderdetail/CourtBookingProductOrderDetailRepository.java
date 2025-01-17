package com.capstone.core.courtbookingproductorderdetail;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.capstone.core.courtbookingproductorderdetail.projection.CourtBookingProductOrderDetailProjection;
import com.capstone.core.table.CourtBookingProductOrderDetailTable;

@Repository
public interface CourtBookingProductOrderDetailRepository extends JpaRepository<CourtBookingProductOrderDetailTable, Long> {
    List<CourtBookingProductOrderDetailProjection> findCourtBookingProductOrderDetailByCourtBookingProductOrderId(Long courtBookingProductOrderId);
}
