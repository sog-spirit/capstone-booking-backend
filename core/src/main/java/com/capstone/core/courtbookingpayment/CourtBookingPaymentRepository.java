package com.capstone.core.courtbookingpayment;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.capstone.core.table.CourtBookingPaymentTable;

@Repository
public interface CourtBookingPaymentRepository extends JpaRepository<CourtBookingPaymentTable, Long> {

}
