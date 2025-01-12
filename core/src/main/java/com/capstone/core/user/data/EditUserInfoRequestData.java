package com.capstone.core.user.data;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EditUserInfoRequestData {
    private String firstName;
    private String lastName;
}
