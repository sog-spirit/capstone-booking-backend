package com.capstone.core.centerreview.data.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddNewCenterReviewRequestData {
    private String content;
    private Long centerId;
}
