package com.capstone.core.center.data.request;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddNewCenterRequestData {
    @NotEmpty(message = "{error.name.required}")
    private String name;
    @NotEmpty(message = "{error.address.required}")
    private String address;
}
