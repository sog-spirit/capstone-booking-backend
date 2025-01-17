package com.capstone.core.courtbookingproductorder.data.request;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CenterOwnerProductOrderStatisticsRequestData {
    private Long centerId;
    private LocalDate dateFrom;
    private LocalDate dateTo;
}
