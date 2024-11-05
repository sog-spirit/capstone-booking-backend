package com.capstone.core.centerreview;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.capstone.core.centerreview.data.AddNewCenterReviewRequestData;
import com.capstone.core.centerreview.projection.UserCenterReviewListProjection;
import com.capstone.core.table.CenterReviewTable;
import com.capstone.core.table.CenterTable;
import com.capstone.core.table.UserTable;
import com.capstone.core.util.security.jwt.JwtUtil;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class CenterReviewService {
    private CenterReviewRepository centerReviewRepository;

    @Transactional
    ResponseEntity<Object> addNewCenterReview(String jwtToken, AddNewCenterReviewRequestData addNewCenterReviewRequestData) {
        Long userId;
        try {
            userId = JwtUtil.getUserIdFromToken(jwtToken);
        } catch (JWTVerificationException jwtVerificationException) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        CenterReviewTable centerReview = new CenterReviewTable();
        centerReview.setContent(addNewCenterReviewRequestData.getContent());

        CenterTable center = new CenterTable();
        center.setId(addNewCenterReviewRequestData.getCenterId());
        centerReview.setCenter(center);

        UserTable user = new UserTable();
        user.setId(userId);
        centerReview.setUser(user);

        centerReviewRepository.save(centerReview);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    ResponseEntity<Object> getUserCenterReviewList(String jwtToken) {
        Long userId;
        try {
            userId = JwtUtil.getUserIdFromToken(jwtToken);
        } catch (JWTVerificationException jwtVerificationException) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        List<UserCenterReviewListProjection> userCenterReviewList = centerReviewRepository.findUserCenterReviewListByUserId(userId);
        return new ResponseEntity<>(userCenterReviewList, HttpStatus.OK);
    }
}
