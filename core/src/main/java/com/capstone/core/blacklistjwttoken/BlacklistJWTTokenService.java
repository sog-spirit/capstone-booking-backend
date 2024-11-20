package com.capstone.core.blacklistjwttoken;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.capstone.core.blacklistjwttoken.data.AccessTokenRequestData;
import com.capstone.core.blacklistjwttoken.data.AccessTokenResponseData;
import com.capstone.core.blacklistjwttoken.data.RefreshTokenRequestData;
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

    public ResponseEntity<Object> verifyAccessToken(AccessTokenRequestData accessTokenData) {
        if (!JwtUtil.isValidToken(accessTokenData.getAccessToken())) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    public ResponseEntity<Object> verifyBlacklistToken(RefreshTokenRequestData refreshTokenData) {
        if (!JwtUtil.isValidToken(refreshTokenData.getRefreshToken()) || blacklistTokenIsExist(refreshTokenData.getRefreshToken())) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    public ResponseEntity<Object> refreshAccessToken(RefreshTokenRequestData refreshTokenData) {
        if (!JwtUtil.isValidToken(refreshTokenData.getRefreshToken()) || blacklistTokenIsExist(refreshTokenData.getRefreshToken())) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Long userId = JwtUtil.getUserIdFromToken(refreshTokenData.getRefreshToken());
        AccessTokenResponseData responseData = new AccessTokenResponseData();
        responseData.setAccessToken(JwtUtil.createAccessToken(userId));
        responseData.setRefreshToken(JwtUtil.createRefreshToken(userId));
        return new ResponseEntity<>(responseData, HttpStatus.OK);
    }

    public ResponseEntity<Object> logout(RefreshTokenRequestData refreshTokenData) {
        if (!JwtUtil.isValidToken(refreshTokenData.getRefreshToken()) || blacklistTokenIsExist(refreshTokenData.getRefreshToken())) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Long userId = JwtUtil.getUserIdFromToken(refreshTokenData.getRefreshToken());
        BlacklistJWTTokenTable blacklistJWTToken = new BlacklistJWTTokenTable();
        UserTable user = userRepository.getReferenceById(userId);
        blacklistJWTToken.setJwtToken(refreshTokenData.getRefreshToken());
        blacklistJWTToken.setUser(user);
        blacklistJWTTokenRepository.save(blacklistJWTToken);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    private boolean blacklistTokenIsExist(String token) {
        return blacklistJWTTokenRepository.existsBlacklistJWTTokenByJwtToken(token);
    }
}
