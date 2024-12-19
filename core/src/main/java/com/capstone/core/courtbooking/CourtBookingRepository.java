package com.capstone.core.courtbooking;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.capstone.core.courtbooking.projection.CenterListProjection;
import com.capstone.core.courtbooking.projection.CenterOwnerCourtBookingCenterListProjection;
import com.capstone.core.courtbooking.projection.CenterOwnerCourtBookingCourtListProjection;
import com.capstone.core.courtbooking.projection.CenterOwnerCourtBookingListProjection;
import com.capstone.core.courtbooking.projection.CenterOwnerCourtBookingUserListProjection;
import com.capstone.core.courtbooking.projection.CenterOwnerCourtCourtBookingListProjection;
import com.capstone.core.courtbooking.projection.CourtBookingListProjection;
import com.capstone.core.courtbooking.projection.UserCourtBookingCenterListProjection;
import com.capstone.core.courtbooking.projection.UserCourtBookingCourtListProjection;
import com.capstone.core.courtbooking.projection.UserCourtBookingListProjection;
import com.capstone.core.table.CourtBookingTable;

@Repository
public interface CourtBookingRepository extends JpaRepository<CourtBookingTable, Long>, JpaSpecificationExecutor<CourtBookingTable> {
    List<CourtBookingListProjection> findByCenterIdAndCourtId(Long centerId, Long courtId);
    List<UserCourtBookingListProjection> findByUserId(Long userId);
    List<CenterOwnerCourtBookingListProjection> findByCenterUserId(Long userId);
    List<CenterListProjection> findCenterListDistinctCenterIdByUserIdAndCenterNameContaining(Long userId, String centerNameQuery);
    List<CenterOwnerCourtCourtBookingListProjection> findCourtBookingListByCourtId(Long courtId);
    List<CenterOwnerCourtBookingCenterListProjection> findCenterOwnerCourtBookingCenterListDistinctCenterByCenterUserIdAndCenterNameContaining(Long centerOwnerId, String query);
    List<CenterOwnerCourtBookingCourtListProjection> findCenterOwnerCourtBookingCourtListDistinctCourtByCenterUserIdAndCourtNameContaining(Long centerOwnerId, String query);
    List<CenterOwnerCourtBookingUserListProjection> findCenterOwnerCourtBookingUserListDistinctUserByCenterUserIdAndUserUsernameContaining(Long centerOwnerId, String query);
    List<UserCourtBookingCenterListProjection> findUserCourtBookingCenterListDistinctCenterIdByUserIdAndCenterNameContaining(Long userId, String centerName);
    List<UserCourtBookingCourtListProjection> findUserCourtBookingCourtListDistinctCourtIdByUserIdAndCourtNameContaining(Long userId, String courtName);
}
