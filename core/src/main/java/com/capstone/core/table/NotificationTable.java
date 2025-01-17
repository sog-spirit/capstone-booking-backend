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
@Table(name = "notification", schema = "public")
@Data
public class NotificationTable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String message;
    @ManyToOne(optional = false)
    @JoinColumn(name = "recipent", nullable = false)
    private UserTable recipent;
    private LocalDateTime createTimestamp;
}
