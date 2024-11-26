package com.capstone.core.centerreview;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.capstone.core.centerreview.data.request.AddNewCenterReviewRequestData;
import com.capstone.core.centerreview.projection.CenterOwnerReviewListProjection;
import com.capstone.core.centerreview.projection.UserCenterReviewListProjection;
import com.capstone.core.centerreview.specification.CenterReviewSpecification;
import com.capstone.core.centerreview.specification.criteria.CenterReviewCriteria;
import com.capstone.core.table.CenterReviewTable;
import com.capstone.core.table.CenterTable;
import com.capstone.core.table.UserTable;
import com.capstone.core.util.security.jwt.JwtUtil;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class CenterReviewService {
    private CenterReviewRepository centerReviewRepository;

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

        CenterReviewCriteria centerReviewCriteria = new CenterReviewCriteria();
        centerReviewCriteria.setUserId(userId);

        List<Sort.Order> sortOrders = new ArrayList<>();

        Sort sort = Sort.by(sortOrders);

        Pageable pageable = PageRequest.of(Integer.parseInt("0"), Integer.parseInt("5"), sort);

        Page<UserCenterReviewListProjection> reviewList = centerReviewRepository.findBy(new CenterReviewSpecification(centerReviewCriteria), q -> q.as(UserCenterReviewListProjection.class).sortBy(pageable.getSort()).page(pageable));

        return new ResponseEntity<>(reviewList.getContent(), HttpStatus.OK);
    }

    ResponseEntity<Object> getCenterOwnerReviewList(String jwtToken) {
        Long userId;
        try {
            userId = JwtUtil.getUserIdFromToken(jwtToken);
        } catch (JWTVerificationException jwtVerificationException) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        CenterReviewCriteria centerReviewCriteria = new CenterReviewCriteria();
        centerReviewCriteria.setCenterOwnerId(userId);

        List<Sort.Order> sortOrders = new ArrayList<>();

        Sort sort = Sort.by(sortOrders);

        Pageable pageable = PageRequest.of(Integer.parseInt("0"), Integer.parseInt("5"), sort);

        Page<CenterOwnerReviewListProjection> reviewList = centerReviewRepository.findBy(new CenterReviewSpecification(centerReviewCriteria), q -> q.as(CenterOwnerReviewListProjection.class).sortBy(pageable.getSort()).page(pageable));

        return new ResponseEntity<>(reviewList.getContent(), HttpStatus.OK);
    }
}
