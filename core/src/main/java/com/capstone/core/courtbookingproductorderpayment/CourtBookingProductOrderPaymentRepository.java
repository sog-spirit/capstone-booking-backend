package com.capstone.core.courtbookingproductorderpayment;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.capstone.core.table.CourtBookingProductOrderPaymentTable;

@Repository
public interface CourtBookingProductOrderPaymentRepository extends JpaRepository<CourtBookingProductOrderPaymentTable, Long> {

}
