package com.capstone.core.court;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.capstone.core.court.data.AddNewCourtRequestData;
import com.capstone.core.table.CenterTable;
import com.capstone.core.table.CourtTable;
import com.capstone.core.table.UserTable;
import com.capstone.core.util.security.jwt.JwtUtil;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class CourtService {
    private CourtRepository courtRepository;

    ResponseEntity<Object> addNewCourt(String jwtToken, AddNewCourtRequestData addNewCourtRequestData) {
        Long userId;
        try {
            userId = JwtUtil.getUserIdFromToken(jwtToken);
        } catch (JWTVerificationException jwtVerificationException) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        CourtTable newCourt = new CourtTable();
        newCourt.setName(addNewCourtRequestData.getName());
        UserTable user = new UserTable();
        user.setId(userId);
        CenterTable center = new CenterTable();
        center.setId(addNewCourtRequestData.getCenterId());
        newCourt.setUser(user);
        newCourt.setCenter(center);
        courtRepository.save(newCourt);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
