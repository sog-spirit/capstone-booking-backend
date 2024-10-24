package com.capstone.core.court.data;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EditCourtRequestData {
    private Long id;
    private String name;
}
