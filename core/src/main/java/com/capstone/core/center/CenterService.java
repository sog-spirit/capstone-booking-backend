package com.capstone.core.center;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.capstone.core.center.data.request.AddNewCenterRequestData;
import com.capstone.core.center.data.request.CenterOwnerCenterDropdownRequestData;
import com.capstone.core.center.data.request.CenterOwnerCenterListRequestData;
import com.capstone.core.center.data.request.EditCenterRequestData;
import com.capstone.core.center.data.request.UserCenterListRequestData;
import com.capstone.core.center.data.response.CenterOwnerCenterListResposneData;
import com.capstone.core.center.data.response.UserCenterListResponseData;
import com.capstone.core.center.projection.CenterOwnerCenterListDropdownProjection;
import com.capstone.core.center.projection.CenterListProjection;
import com.capstone.core.center.projection.CenterOwnerCenterListProjection;
import com.capstone.core.center.projection.UserCenterListProjection;
import com.capstone.core.center.specification.CenterSpecification;
import com.capstone.core.center.specification.criteria.CenterFilterCriteria;
import com.capstone.core.table.CenterTable;
import com.capstone.core.table.UserTable;
import com.capstone.core.userrole.UserRoleRepository;
import com.capstone.core.userrole.projection.UserRoleProjection;
import com.capstone.core.util.consts.UserRole;
import com.capstone.core.util.security.jwt.JwtUtil;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class CenterService {
    private static final Long DEFAULT_FIELD_QUANTITY = (long) 0;

    private CenterRepository centerRepository;
    private UserRoleRepository userRoleRepository;

    ResponseEntity<Object> addNewCenter(String jwtToken, AddNewCenterRequestData requestData) {
        Long userId;
        try {
            userId = JwtUtil.getUserIdFromToken(jwtToken);
        } catch (JWTVerificationException jwtVerificationException) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        CenterTable newCenter = new CenterTable();
        newCenter.setName(requestData.getName());
        newCenter.setAddress(requestData.getAddress());
        UserTable user = new UserTable();
        user.setId(userId);
        newCenter.setUser(user);
        newCenter.setFieldQuantity(DEFAULT_FIELD_QUANTITY);
        centerRepository.save(newCenter);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    ResponseEntity<Object> editCenter(String jwtToken, EditCenterRequestData editCenterRequestData) {
        Long userId;
        try {
            userId = JwtUtil.getUserIdFromToken(jwtToken);
        } catch (JWTVerificationException jwtVerificationException) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        Optional<CenterTable> queryResult = centerRepository.findById(editCenterRequestData.getId());
        if (queryResult.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        CenterTable record = queryResult.get();
        record.setName(editCenterRequestData.getName());
        record.setAddress(editCenterRequestData.getAddress());
        centerRepository.save(record);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    ResponseEntity<Object> getUserCenterList(String jwtToken, UserCenterListRequestData requestData) {
        Long userId;
        try {
            userId = JwtUtil.getUserIdFromToken(jwtToken);
        } catch (JWTVerificationException jwtVerificationException) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        Pageable pageable = PageRequest.of(requestData.getPageNo(), requestData.getPageSize());
        Page<UserCenterListProjection> centerPagedResult = centerRepository.findUserCenterListBy(pageable);
        UserCenterListResponseData responseData = new UserCenterListResponseData();
        responseData.setTotalPage(centerPagedResult.getTotalPages());
        responseData.setCenterList(centerPagedResult.getContent());
        return new ResponseEntity<>(responseData, HttpStatus.OK);
    }

    ResponseEntity<Object> getCenterOwnerCenterDropdownList(String jwtToken, CenterOwnerCenterDropdownRequestData requestData) {
        Long userId;
        try {
            userId = JwtUtil.getUserIdFromToken(jwtToken);
        } catch (JWTVerificationException jwtVerificationException) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        List<CenterOwnerCenterListDropdownProjection> centerListResult = centerRepository.findByNameContainingAndUserId(requestData.getQuery(), userId);
        return new ResponseEntity<>(centerListResult, HttpStatus.OK);
    }

    ResponseEntity<Object> getCenterOwnerCenterList(String jwtToken, CenterOwnerCenterListRequestData requestData) {
        Long userId;
        try {
            userId = JwtUtil.getUserIdFromToken(jwtToken);
        } catch (JWTVerificationException jwtVerificationException) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        CenterFilterCriteria filterCriteria = new CenterFilterCriteria();
        filterCriteria.setCenterOwnerId(userId);

        List<Sort.Order> sortOrders = new ArrayList<>();

        Sort sort = Sort.by(sortOrders);

        Pageable pageable = PageRequest.of(requestData.getPageNo(), requestData.getPageSize(), sort);

        Page<CenterOwnerCenterListProjection> centerList = centerRepository.findBy(new CenterSpecification(filterCriteria), q -> q.as(CenterOwnerCenterListProjection.class).sortBy(pageable.getSort()).page(pageable));

        CenterOwnerCenterListResposneData responseData = new CenterOwnerCenterListResposneData();
        responseData.setTotalPage(centerList.getTotalPages());
        responseData.setCenterList(centerList.getContent());

        return new ResponseEntity<>(responseData, HttpStatus.OK);
    }
}
