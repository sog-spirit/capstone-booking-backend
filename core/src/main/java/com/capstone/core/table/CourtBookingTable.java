package com.capstone.core.table;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "court_booking", schema = "public")
@Data
public class CourtBookingTable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(optional = false)
    @JoinColumn(name = "court_id", nullable = false)
    private CourtTable court;
    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private UserTable user;
    private Long status;
    private LocalDateTime createTimestamp;
    private LocalDate usageDate;
    private LocalTime usageTimeStart;
    private LocalTime usageTimeEnd;
    private Long courtFee;
    private Long productFee;
}
