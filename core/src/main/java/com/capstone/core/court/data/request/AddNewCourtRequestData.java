package com.capstone.core.court.data.request;

import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddNewCourtRequestData {
    @NotEmpty(message = "{error.name.required}")
    private String name;
    private Long centerId;
}
