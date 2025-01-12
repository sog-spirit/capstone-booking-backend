package com.capstone.core.centerreview.data.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EditCenterReviewRequestData {
    private Long id;
    private Long rating;
    private String content;
}
