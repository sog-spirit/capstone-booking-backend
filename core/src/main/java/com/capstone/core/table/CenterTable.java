package com.capstone.core.table;

import java.time.LocalTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "center", schema = "public")
@Data
public class CenterTable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(optional = false)
    @JoinColumn(name = "\"owner\"", nullable = false)
    private UserTable user;
    @Column(name = "\"name\"")
    private String name;
    private String address;
    private Long fieldQuantity;
    private Long courtFee;
    private LocalTime openingTime;
    private LocalTime closingTime;
    private Long status;
    private Long reviewCount;
    private Long recommendCount;
    private Long notRecommendCount;
}
