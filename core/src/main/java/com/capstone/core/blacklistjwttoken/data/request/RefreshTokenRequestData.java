package com.capstone.core.blacklistjwttoken.data.request;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RefreshTokenRequestData {
    @NotEmpty(message = "{error.refreshToken.required}")
    private String refreshToken;
}
