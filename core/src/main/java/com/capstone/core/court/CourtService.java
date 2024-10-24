package com.capstone.core.court;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.capstone.core.court.data.AddNewCourtRequestData;
import com.capstone.core.court.data.EditCourtRequestData;
import com.capstone.core.court.projection.CourtListProjection;
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

    ResponseEntity<Object> getCourtList(String jwtToken, Long centerId) {
        Long userId;
        try {
            userId = JwtUtil.getUserIdFromToken(jwtToken);
        } catch (JWTVerificationException jwtVerificationException) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        List<CourtListProjection> courtList = courtRepository.findByCenterId(centerId);
        return new ResponseEntity<>(courtList, HttpStatus.OK);
    }

    ResponseEntity<Object> editCourt(String jwtToken, EditCourtRequestData editCourtRequestData) {
        Long userId;
        try {
            userId = JwtUtil.getUserIdFromToken(jwtToken);
        } catch (JWTVerificationException jwtVerificationException) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        Optional<CourtTable> courtQuery = courtRepository.findById(editCourtRequestData.getId());
        if (courtQuery.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        CourtTable court = courtQuery.get();
        court.setName(editCourtRequestData.getName());
        courtRepository.save(court);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
