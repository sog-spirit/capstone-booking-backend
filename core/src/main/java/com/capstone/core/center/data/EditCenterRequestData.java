package com.capstone.core.center.data;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EditCenterRequestData {
    private Long id;
    private String name;
    private String address;
}
