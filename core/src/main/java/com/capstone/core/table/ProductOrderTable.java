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
@Table(name = "product_order", schema = "public")
@Data
public class ProductOrderTable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private UserTable user;
    private LocalDateTime createTimestamp;
    private Long total;
    @ManyToOne(optional = false)
    @JoinColumn(name = "center_id", nullable = false)
    private CenterTable center;
    @ManyToOne(optional = false)
    @JoinColumn(name = "status", nullable = false)
    private ProductOrderStatusTable status;
}
