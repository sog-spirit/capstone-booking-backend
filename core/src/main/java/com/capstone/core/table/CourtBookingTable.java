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
    private LocalDateTime createTimestamp;
    @ManyToOne(optional = false)
    @JoinColumn(name = "center_id", nullable = false)
    private CenterTable center;
    @ManyToOne(optional = false)
    @JoinColumn(name = "court_id", nullable = false)
    private CourtTable court;
    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private UserTable user;
    private LocalDate usageDate;
    private LocalTime usageTimeStart;
    private LocalTime usageTimeEnd;
    @ManyToOne(optional = false)
    @JoinColumn(name = "status", nullable = false)
    private BookingOrderStatusTable status;
}
