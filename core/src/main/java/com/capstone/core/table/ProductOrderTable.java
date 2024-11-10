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
    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserTable user;
    private LocalDateTime createTimestamp;
    private Long total;
    @ManyToOne
    @JoinColumn(name = "center_id")
    private CenterTable center;
    @ManyToOne
    @JoinColumn(name = "status")
    private ProductOrderStatusTable status;
}
