package com.capstone.core.courtbooking;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.capstone.core.courtbooking.projection.CourtBookingListProjection;
import com.capstone.core.table.CourtBookingTable;

@Repository
public interface CourtBookingRepository extends JpaRepository<CourtBookingTable, Long> {
    List<CourtBookingListProjection> findByCenterIdAndCourtId(Long centerId, Long courtId);
}
