package com.capstone.core.center.data.response;

import java.time.LocalTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CenterOwnerCenterWorkingTimeResponseData {
    private LocalTime openingTime;
    private LocalTime closingTime;
}
