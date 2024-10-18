package com.capstone.core.blacklistjwttoken.data;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RefreshTokenRequestData {
    @NotEmpty(message = "{error.refreshtoken.required}")
    private String refreshToken;
}
