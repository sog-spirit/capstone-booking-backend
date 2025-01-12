package com.capstone.core.table;

import java.time.LocalDateTime;

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
@Table(name = "center_review", schema = "public")
@Data
public class CenterReviewTable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private UserTable user;
    @ManyToOne(optional = false)
    @JoinColumn(name = "center_id", nullable = false)
    private CenterTable center;
    @Column(name = "\"content\"")
    private String content;
    private Long status;
    private Long rating;
    private LocalDateTime createTimestamp;
    private LocalDateTime updateTimestamp;
}
