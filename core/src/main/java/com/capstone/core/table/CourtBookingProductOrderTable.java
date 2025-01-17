package com.capstone.core.table;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "court_booking_product_order", schema = "public")
@Data
public class CourtBookingProductOrderTable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(optional = false)
    @JoinColumn(name = "court_booking_id", nullable = false)
    private CourtBookingTable courtBooking;
    private LocalDateTime createTimestamp;
    private LocalDateTime updateTimestamp;
    private Long fee;
    private Long status;
}
