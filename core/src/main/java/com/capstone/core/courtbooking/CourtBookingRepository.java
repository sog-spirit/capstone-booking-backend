package com.capstone.core.courtbooking;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.capstone.core.courtbooking.projection.CenterListProjection;
import com.capstone.core.courtbooking.projection.CenterOwnerCourtBookingListProjection;
import com.capstone.core.courtbooking.projection.CourtBookingListProjection;
import com.capstone.core.courtbooking.projection.UserCourtBookingListProjection;
import com.capstone.core.table.CourtBookingTable;

@Repository
public interface CourtBookingRepository extends JpaRepository<CourtBookingTable, Long>, JpaSpecificationExecutor<CourtBookingTable> {
    List<CourtBookingListProjection> findByCenterIdAndCourtId(Long centerId, Long courtId);
    List<UserCourtBookingListProjection> findByUserId(Long userId);
    List<CenterOwnerCourtBookingListProjection> findByCenterUserId(Long userId);
    List<CenterListProjection> findCenterListByUserIdAndCenterNameContaining(Long userId, String centerNameQuery);
}
