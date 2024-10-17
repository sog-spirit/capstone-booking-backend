package com.capstone.core.blacklistjwttoken;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.capstone.core.blacklistjwttoken.data.RefreshTokenResponseData;
import com.capstone.core.table.BlacklistJWTTokenTable;
import com.capstone.core.table.UserTable;
import com.capstone.core.user.UserRepository;
import com.capstone.core.util.security.jwt.JwtUtil;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class BlacklistJWTTokenService {

    private BlacklistJWTTokenRepository blacklistJWTTokenRepository;
    private UserRepository userRepository;

    public ResponseEntity<Object> verifyBlacklistToken(RefreshTokenResponseData refreshTokenData) {
        if (!isValidToken(refreshTokenData.getRefreshToken()) || blacklistTokenIsExist(refreshTokenData.getRefreshToken())) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    public ResponseEntity<Object> refreshAccessToken(RefreshTokenResponseData refreshTokenData) {
        if (!isValidToken(refreshTokenData.getRefreshToken()) || blacklistTokenIsExist(refreshTokenData.getRefreshToken())) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        DecodedJWT decodedJWT = JwtUtil.decodeJWT(refreshTokenData.getRefreshToken());
        Long userId = Long.getLong(decodedJWT.getSubject());
        String accessToken = JwtUtil.createAccessToken(userId);
        return new ResponseEntity<>(accessToken, HttpStatus.OK);
    }

    public ResponseEntity<Object> logout(RefreshTokenResponseData refreshTokenData) {
        if (!isValidToken(refreshTokenData.getRefreshToken()) || blacklistTokenIsExist(refreshTokenData.getRefreshToken())) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Long userId = getUserIdFromToken(refreshTokenData.getRefreshToken());
        BlacklistJWTTokenTable blacklistJWTToken = new BlacklistJWTTokenTable();
        UserTable user = userRepository.getReferenceById(userId);
        blacklistJWTToken.setJwtToken(refreshTokenData.getRefreshToken());
        blacklistJWTToken.setUser(user);
        blacklistJWTTokenRepository.save(blacklistJWTToken);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    private Long getUserIdFromToken(String token) {
        DecodedJWT decodedJWT = JwtUtil.decodeJWT(token);
        return Long.parseLong(decodedJWT.getSubject());
    }

    private boolean blacklistTokenIsExist(String token) {
        return blacklistJWTTokenRepository.existsBlacklistJWTTokenByJwtToken(token);
    }

    private boolean isValidToken(String token) {
        try {
            JwtUtil.validateToken(token);
        } catch (JWTVerificationException jwtVerificationException) {
            return false;
        }
        return true;
    }
}
