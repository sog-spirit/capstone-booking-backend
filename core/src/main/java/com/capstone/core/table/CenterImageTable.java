package com.capstone.core.table;

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
@Table(name = "center_image", schema = "public")
@Data
public class CenterImageTable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(optional = false)
    @JoinColumn(name = "center_id", nullable = false)
    private CenterTable center;
    @ManyToOne(optional = false)
    @JoinColumn(name = "image_id", nullable = false)
    private ImageTable image;
    @Column(name = "\"type\"")
    private Long type;
    private Long displayOrder;
    private Long status;
}
