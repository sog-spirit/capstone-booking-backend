package com.capstone.core.blacklistjwttoken;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.capstone.core.blacklistjwttoken.data.RefreshTokenRequestData;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/jwt")
@AllArgsConstructor
public class BlacklistJWTTokenController {

    private BlacklistJWTTokenService blacklistJWTTokenService;

    @PostMapping("/refresh")
    ResponseEntity<Object> refreshAccessToken(@RequestBody @Valid RefreshTokenRequestData refreshTokenData) {
        return blacklistJWTTokenService.refreshAccessToken(refreshTokenData);
    }

    @PostMapping("/logout")
    @Transactional
    ResponseEntity<Object> logout(@RequestBody @Valid RefreshTokenRequestData refreshTokenData) {
        return blacklistJWTTokenService.logout(refreshTokenData);
    }

    @PostMapping("/blacklist-verify")
    ResponseEntity<Object> verifyBlacklistToken(@RequestBody @Valid RefreshTokenRequestData refreshTokenData) {
        return blacklistJWTTokenService.verifyBlacklistToken(refreshTokenData);
    }
}
