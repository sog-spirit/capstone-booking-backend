package com.capstone.core.center.data.request;

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
