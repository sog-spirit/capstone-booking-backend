package com.capstone.core.user.data;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginFormRequestData {
    @NotEmpty(message = "{error.username.required}")
    private String username;

    @NotEmpty(message = "{error.password.required}")
    private String password;
}
