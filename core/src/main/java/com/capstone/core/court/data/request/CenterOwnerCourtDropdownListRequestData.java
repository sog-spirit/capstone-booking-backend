package com.capstone.core.court.data.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CenterOwnerCourtDropdownListRequestData {
    private Long centerId;
    private String query;
}
