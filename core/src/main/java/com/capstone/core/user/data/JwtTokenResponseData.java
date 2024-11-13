package com.capstone.core.user.data;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class JwtTokenResponseData {
    private String accessToken;
    private String refreshToken;
    private String role;
    private String username;
}
