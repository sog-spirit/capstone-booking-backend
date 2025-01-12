package com.capstone.core.court;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.capstone.core.court.projection.CenterOwnerCourtListProjection;
import com.capstone.core.court.projection.CourtCenterWorkingTimeProjection;
import com.capstone.core.court.projection.UserCourtListProjection;
import com.capstone.core.table.CourtTable;

@Repository
public interface CourtRepository extends JpaRepository<CourtTable, Long>, JpaSpecificationExecutor<CourtTable> {
    List<UserCourtListProjection> findByCenterIdAndCenterUserId(Long centerId, Long userId);
    List<UserCourtListProjection> findByCenterIdAndStatusOrderById(Long centerId, Long status);
    List<UserCourtListProjection> findByCenterIdAndNameContainingAndStatus(Long centerId, String name, Long status);
    List<CenterOwnerCourtListProjection> findCenterOwnerCourtListByCenterIdAndNameContainingAndStatus(Long centerId, String name, Long status);
    CourtCenterWorkingTimeProjection findWorkingTimeByIdAndStatus(Long courtId, Long status);
}
