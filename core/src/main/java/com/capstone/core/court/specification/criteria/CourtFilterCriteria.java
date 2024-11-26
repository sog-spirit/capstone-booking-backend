package com.capstone.core.court.specification.criteria;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CourtFilterCriteria {
    private Long centerOwnerId;
    private Long centerId;
}
