package com.capstone.core.user.data;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DuplicateUsernamePasswordResponseData {
    private String username;
    private String password;
}
