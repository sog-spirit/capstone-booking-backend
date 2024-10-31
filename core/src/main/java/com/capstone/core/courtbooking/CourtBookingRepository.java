package com.capstone.core.courtbooking;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.capstone.core.table.CourtBookingTable;

@Repository
public interface CourtBookingRepository extends JpaRepository<CourtBookingTable, Long> {

}
