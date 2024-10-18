package com.capstone.core.blacklistjwttoken.data;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccessTokenResponseData {
    private String accessToken;
}
