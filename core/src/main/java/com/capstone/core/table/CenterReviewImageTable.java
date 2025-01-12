package com.capstone.core.table;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "center_review_image", schema = "public")
@Data
public class CenterReviewImageTable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(optional = false)
    @JoinColumn(name = "center_id", nullable = false)
    private CenterReviewTable centerReview;
    @ManyToOne(optional = false)
    @JoinColumn(name = "image_id", nullable = false)
    private ImageTable image;
    private Long type;
    private Long displayOrder;
    private Long status;
}
