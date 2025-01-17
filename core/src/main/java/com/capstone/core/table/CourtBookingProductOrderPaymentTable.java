package com.capstone.core.table;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "court_booking_product_order_payment", schema = "public")
@Data
public class CourtBookingProductOrderPaymentTable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne(optional = false)
    @JoinColumn(name = "court_booking_product_order_id", nullable = false)
    private CourtBookingProductOrderTable courtBookingProductOrder;
    private Long totalMoney;
    private LocalDateTime createTimestamp;
    private LocalDateTime updateTimestamp;
    private Long paymentType;
    private String txnRef;
    private String transactionNo;
}
