package com.capstone.core.centerreview;

import java.time.LocalDateTime;
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
import com.capstone.core.centerreview.data.request.AdminApproveCenterReviewRequestData;
import com.capstone.core.centerreview.data.request.AdminCenterReviewListRequestData;
import com.capstone.core.centerreview.data.request.AdminDeniedCenterReviewRequestData;
import com.capstone.core.centerreview.data.request.CenterOwnerCenterReviewCenterFilterListRequestData;
import com.capstone.core.centerreview.data.request.CenterOwnerCenterReviewUserFilterListRequestData;
import com.capstone.core.centerreview.data.request.CenterOwnerReviewListRequestData;
import com.capstone.core.centerreview.data.request.DeleteCenterReviewRequestData;
import com.capstone.core.centerreview.data.request.EditCenterReviewRequestData;
import com.capstone.core.centerreview.data.request.UserCenterCenterReviewListRequestData;
import com.capstone.core.centerreview.data.request.UserCenterReviewCenterFilterListRequestData;
import com.capstone.core.centerreview.data.request.UserCenterReviewListRequestData;
import com.capstone.core.centerreview.data.response.AdminCenterReviewListResponseData;
import com.capstone.core.centerreview.data.response.CenterOwnerReviewListResponseData;
import com.capstone.core.centerreview.data.response.UserCenterReviewListResponseData;
import com.capstone.core.centerreview.projection.AdminCenterReviewListProjection;
import com.capstone.core.centerreview.projection.CenterOwnerCenterReviewCenterFilterListProjection;
import com.capstone.core.centerreview.projection.CenterOwnerCenterReviewUserFilterListProjection;
import com.capstone.core.centerreview.projection.CenterOwnerReviewListProjection;
import com.capstone.core.centerreview.projection.UserCenterCenterReviewListProjection;
import com.capstone.core.centerreview.projection.UserCenterReviewCenterFilterListProjection;
import com.capstone.core.centerreview.projection.UserCenterReviewListProjection;
import com.capstone.core.centerreview.specification.CenterReviewSpecification;
import com.capstone.core.centerreview.specification.criteria.CenterReviewCriteria;
import com.capstone.core.table.CenterReviewTable;
import com.capstone.core.table.CenterTable;
import com.capstone.core.table.UserTable;
import com.capstone.core.util.consts.CenterReviewStatus;
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
        LocalDateTime dateTimeNow = LocalDateTime.now();
        CenterReviewTable centerReview = new CenterReviewTable();
        centerReview.setContent(addNewCenterReviewRequestData.getContent());
        centerReview.setRating(addNewCenterReviewRequestData.getRating());
        centerReview.setStatus(CenterReviewStatus.PENDING.getValue());
        centerReview.setCreateTimestamp(dateTimeNow);
        centerReview.setUpdateTimestamp(dateTimeNow);

        CenterTable center = new CenterTable();
        center.setId(addNewCenterReviewRequestData.getCenterId());
        centerReview.setCenter(center);

        UserTable user = new UserTable();
        user.setId(userId);
        centerReview.setUser(user);

        centerReviewRepository.save(centerReview);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    ResponseEntity<Object> editCenterReview(String jwtToken, EditCenterReviewRequestData requestData) {
        Long userId;
        try {
            userId = JwtUtil.getUserIdFromToken(jwtToken);
        } catch (JWTVerificationException jwtVerificationException) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        LocalDateTime nowDateTime = LocalDateTime.now();

        CenterReviewTable centerReview = centerReviewRepository.getReferenceById(requestData.getId());
        centerReview.setContent(requestData.getContent());
        centerReview.setRating(requestData.getRating());
        centerReview.setUpdateTimestamp(nowDateTime);
        centerReview.setStatus(CenterReviewStatus.PENDING.getValue());
        centerReviewRepository.save(centerReview);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    ResponseEntity<Object> deleteCenterReview(String jwtToken, DeleteCenterReviewRequestData requestData) {
        Long userId;
        try {
            userId = JwtUtil.getUserIdFromToken(jwtToken);
        } catch (JWTVerificationException jwtVerificationException) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        CenterReviewTable centerReview = centerReviewRepository.getReferenceById(requestData.getId());
        centerReview.setStatus(CenterReviewStatus.CANCELLED.getValue());
        centerReviewRepository.save(centerReview);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    ResponseEntity<Object> getUserCenterReviewList(String jwtToken, UserCenterReviewListRequestData requestData) {
        Long userId;
        try {
            userId = JwtUtil.getUserIdFromToken(jwtToken);
        } catch (JWTVerificationException jwtVerificationException) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        CenterReviewCriteria centerReviewCriteria = new CenterReviewCriteria();
        centerReviewCriteria.setUserId(userId);
        centerReviewCriteria.setId(requestData.getId());
        centerReviewCriteria.setCenterId(requestData.getCenterId());

        List<Sort.Order> sortOrders = new ArrayList<>();
        if (requestData.getIdSortOrder() != null) {
            sortOrders.add(new Sort.Order(requestData.getIdSortOrder().equalsIgnoreCase("desc") ? Sort.Direction.DESC : Sort.Direction.ASC, "id"));
        }
        if (requestData.getCenterSortOrder() != null) {
            sortOrders.add(new Sort.Order(requestData.getCenterSortOrder().equalsIgnoreCase("desc") ? Sort.Direction.DESC : Sort.Direction.ASC, "center"));
        }
        if (requestData.getCreateTimestampSortOrder() != null) {
            sortOrders.add(new Sort.Order(requestData.getCreateTimestampSortOrder().equalsIgnoreCase("desc") ? Sort.Direction.DESC : Sort.Direction.ASC, "createTimestamp"));
        }
        if (requestData.getUpdateTimestampSortOrder() != null) {
            sortOrders.add(new Sort.Order(requestData.getUpdateTimestampSortOrder().equalsIgnoreCase("desc") ? Sort.Direction.DESC : Sort.Direction.ASC, "updateTimestamp"));
        }

        Sort sort = Sort.by(sortOrders);

        Pageable pageable = PageRequest.of(requestData.getPageNo(), requestData.getPageSize(), sort);

        Page<UserCenterReviewListProjection> reviewList = centerReviewRepository.findBy(new CenterReviewSpecification(centerReviewCriteria), q -> q.as(UserCenterReviewListProjection.class).sortBy(pageable.getSort()).page(pageable));
        UserCenterReviewListResponseData responseData = new UserCenterReviewListResponseData();
        responseData.setTotalPage(reviewList.getTotalPages());
        responseData.setCenterReviewList(reviewList.getContent());

        return new ResponseEntity<>(responseData, HttpStatus.OK);
    }

    ResponseEntity<Object> getCenterOwnerReviewList(String jwtToken, CenterOwnerReviewListRequestData requestData) {
        Long userId;
        try {
            userId = JwtUtil.getUserIdFromToken(jwtToken);
        } catch (JWTVerificationException jwtVerificationException) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        CenterReviewCriteria centerReviewCriteria = new CenterReviewCriteria();
        centerReviewCriteria.setCenterOwnerId(userId);
        centerReviewCriteria.setId(requestData.getId());
        centerReviewCriteria.setCenterId(requestData.getCenterId());
        centerReviewCriteria.setUserId(requestData.getUserId());

        List<Sort.Order> sortOrders = new ArrayList<>();
        if (requestData.getIdSortOrder() != null) {
            sortOrders.add(new Sort.Order(requestData.getIdSortOrder().equalsIgnoreCase("desc") ? Sort.Direction.DESC : Sort.Direction.ASC, "id"));
        }
        if (requestData.getUserSortOrder() != null) {
            sortOrders.add(new Sort.Order(requestData.getUserSortOrder().equalsIgnoreCase("desc") ? Sort.Direction.DESC : Sort.Direction.ASC, "user"));
        }
        if (requestData.getCenterSortOrder() != null) {
            sortOrders.add(new Sort.Order(requestData.getCenterSortOrder().equalsIgnoreCase("desc") ? Sort.Direction.DESC : Sort.Direction.ASC, "center"));
        }
        if (requestData.getCreateTimestampSortOrder() != null) {
            sortOrders.add(new Sort.Order(requestData.getCreateTimestampSortOrder().equalsIgnoreCase("desc") ? Sort.Direction.DESC : Sort.Direction.ASC, "createTimestamp"));
        }
        if (requestData.getUpdateTimestampSortOrder() != null) {
            sortOrders.add(new Sort.Order(requestData.getUpdateTimestampSortOrder().equalsIgnoreCase("desc") ? Sort.Direction.DESC : Sort.Direction.ASC, "updateTimestamp"));
        }

        Sort sort = Sort.by(sortOrders);

        Pageable pageable = PageRequest.of(requestData.getPageNo(), requestData.getPageSize(), sort);

        CenterOwnerReviewListResponseData responseData = new CenterOwnerReviewListResponseData();
        Page<CenterOwnerReviewListProjection> reviewList = centerReviewRepository.findBy(new CenterReviewSpecification(centerReviewCriteria), q -> q.as(CenterOwnerReviewListProjection.class).sortBy(pageable.getSort()).page(pageable));
        responseData.setTotalPage(reviewList.getTotalPages());
        responseData.setCenterReviewList(reviewList.getContent());

        return new ResponseEntity<>(responseData, HttpStatus.OK);
    }

    ResponseEntity<Object> getCenterOwnerCenterReviewUserFilterList(String jwtToken, CenterOwnerCenterReviewUserFilterListRequestData requestData) {
        Long userId;
        try {
            userId = JwtUtil.getUserIdFromToken(jwtToken);
        } catch (JWTVerificationException jwtVerificationException) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        List<CenterOwnerCenterReviewUserFilterListProjection> userList = centerReviewRepository.findCenterOwnerCenterReviewUserFilterListDistinctUserIdByCenterUserIdAndUserUsernameContaining(userId, requestData.getQuery());
        return new ResponseEntity<>(userList, HttpStatus.OK);
    }

    ResponseEntity<Object> getCenterOwnerCenterReviewCenterFilterList(String jwtToken, CenterOwnerCenterReviewCenterFilterListRequestData requestData) {
        Long userId;
        try {
            userId = JwtUtil.getUserIdFromToken(jwtToken);
        } catch (JWTVerificationException jwtVerificationException) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        List<CenterOwnerCenterReviewCenterFilterListProjection> centerList = centerReviewRepository.findCenterOwnerCenterReviewCenterFilterListDistinctCenterIdByCenterUserIdAndCenterNameContaining(userId, requestData.getQuery());
        return new ResponseEntity<>(centerList, HttpStatus.OK);
    }

    ResponseEntity<Object> getUserCenterReviewCenterFilterList(String jwtToken, UserCenterReviewCenterFilterListRequestData requestData) {
        Long userId;
        try {
            userId = JwtUtil.getUserIdFromToken(jwtToken);
        } catch (JWTVerificationException jwtVerificationException) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        List<UserCenterReviewCenterFilterListProjection> centerList = centerReviewRepository.findUserCenterReviewCenterFilterListDistinctCenterIdByUserIdAndCenterNameContaining(userId, requestData.getQuery());
        return new ResponseEntity<>(centerList, HttpStatus.OK);
    }

    ResponseEntity<Object> getUserCenterCenterReviewList(String jwtToken, UserCenterCenterReviewListRequestData requestData) {
        List<UserCenterCenterReviewListProjection> centerReviews = centerReviewRepository.findUserCenterCenterReviewListByCenterId(requestData.getCenterId());
        return new ResponseEntity<>(centerReviews, HttpStatus.OK);
    }

    ResponseEntity<Object> getAdminCenterReviewList(String jwtToken, AdminCenterReviewListRequestData requestData) {
        Long userId;
        try {
            userId = JwtUtil.getUserIdFromToken(jwtToken);
        } catch (JWTVerificationException jwtVerificationException) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        CenterReviewCriteria centerReviewCriteria = new CenterReviewCriteria();
        centerReviewCriteria.setStatus(CenterReviewStatus.PENDING.getValue());

        List<Sort.Order> sortOrders = new ArrayList<>();

        Sort sort = Sort.by(sortOrders);

        Pageable pageable = PageRequest.of(requestData.getPageNo(), requestData.getPageSize(), sort);

        AdminCenterReviewListResponseData responseData = new AdminCenterReviewListResponseData();
        Page<AdminCenterReviewListProjection> reviewList = centerReviewRepository.findBy(new CenterReviewSpecification(centerReviewCriteria), q -> q.as(AdminCenterReviewListProjection.class).sortBy(pageable.getSort()).page(pageable));
        responseData.setTotalPage(reviewList.getTotalPages());
        responseData.setCenterReviewList(reviewList.getContent());

        return new ResponseEntity<>(responseData, HttpStatus.OK);
    }

    ResponseEntity<Object> adminApproveCenterReview(String jwtToken, AdminApproveCenterReviewRequestData requestData) {
        Long userId;
        try {
            userId = JwtUtil.getUserIdFromToken(jwtToken);
        } catch (JWTVerificationException jwtVerificationException) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        CenterReviewTable centerReview = centerReviewRepository.getReferenceById(requestData.getId());
        centerReview.setStatus(CenterReviewStatus.VERIFIED.getValue());
        centerReviewRepository.save(centerReview);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    ResponseEntity<Object> adminDeniedCenterReview(String jwtToken, AdminDeniedCenterReviewRequestData requestData) {
        Long userId;
        try {
            userId = JwtUtil.getUserIdFromToken(jwtToken);
        } catch (JWTVerificationException jwtVerificationException) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        CenterReviewTable centerReview = centerReviewRepository.getReferenceById(requestData.getId());
        centerReview.setStatus(CenterReviewStatus.DENIED.getValue());
        centerReviewRepository.save(centerReview);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
