package com.capstone.core.blacklistjwttoken.data.request;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccessTokenRequestData {
    @NotEmpty(message = "{error.accessToken.required}")
    private String accessToken;
}
