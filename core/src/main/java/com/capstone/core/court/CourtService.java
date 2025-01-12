package com.capstone.core.court;

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
import com.capstone.core.court.data.request.AddNewCourtRequestData;
import com.capstone.core.court.data.request.CenterOwnerCourtDropdownListRequestData;
import com.capstone.core.court.data.request.CenterOwnerCourtListRequestData;
import com.capstone.core.court.data.request.DeleteCourtRequestData;
import com.capstone.core.court.data.request.EditCourtRequestData;
import com.capstone.core.court.data.request.UserCenterCourtListRequestData;
import com.capstone.core.court.data.request.UserCourtDropdownListRequestData;
import com.capstone.core.court.data.response.CenterOwnerCourtListResponseData;
import com.capstone.core.court.projection.CenterOwnerCourtListProjection;
import com.capstone.core.court.projection.UserCourtListProjection;
import com.capstone.core.court.specification.CourtSpecification;
import com.capstone.core.court.specification.criteria.CourtFilterCriteria;
import com.capstone.core.table.CenterTable;
import com.capstone.core.table.CourtTable;
import com.capstone.core.table.UserTable;
import com.capstone.core.userrole.UserRoleRepository;
import com.capstone.core.userrole.projection.UserRoleProjection;
import com.capstone.core.util.consts.CourtStatus;
import com.capstone.core.util.consts.UserRole;
import com.capstone.core.util.security.jwt.JwtUtil;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class CourtService {
    private CourtRepository courtRepository;
    private UserRoleRepository userRoleRepository;

    ResponseEntity<Object> addNewCourt(String jwtToken, AddNewCourtRequestData addNewCourtRequestData) {
        Long userId;
        try {
            userId = JwtUtil.getUserIdFromToken(jwtToken);
        } catch (JWTVerificationException jwtVerificationException) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        CourtTable newCourt = new CourtTable();
        newCourt.setName(addNewCourtRequestData.getName());
        newCourt.setStatus(CourtStatus.ACTIVE.getValue());
        UserTable user = new UserTable();
        user.setId(userId);
        CenterTable center = new CenterTable();
        center.setId(addNewCourtRequestData.getCenterId());
        newCourt.setCenter(center);
        courtRepository.save(newCourt);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    ResponseEntity<Object> deleteCourt(String jwtToken, DeleteCourtRequestData requestData) {
        Long userId;
        try {
            userId = JwtUtil.getUserIdFromToken(jwtToken);
        } catch (JWTVerificationException jwtVerificationException) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        CourtTable court = courtRepository.getReferenceById(requestData.getId());
        court.setStatus(CourtStatus.CLOSED.getValue());
        courtRepository.save(court);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    ResponseEntity<Object> getUserCenterCourtList(String jwtToken, UserCenterCourtListRequestData requestData) {
        Long userId;
        try {
            userId = JwtUtil.getUserIdFromToken(jwtToken);
        } catch (JWTVerificationException jwtVerificationException) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        List<UserCourtListProjection> courtList = courtRepository.findByCenterIdAndStatusOrderById(requestData.getCenterId(), CourtStatus.ACTIVE.getValue());
        return new ResponseEntity<>(courtList, HttpStatus.OK);
    }

    ResponseEntity<Object> getUserCourtDropdownList(String jwtToken, UserCourtDropdownListRequestData requestData) {
        List<UserCourtListProjection> courtList = courtRepository.findByCenterIdAndNameContainingAndStatus(requestData.getCenterId(), requestData.getQuery(), CourtStatus.ACTIVE.getValue());
        return new ResponseEntity<>(courtList, HttpStatus.OK);
    }

    ResponseEntity<Object> getCenterOwnerCourtDropdownList(String jwtToken, CenterOwnerCourtDropdownListRequestData requestData) {
        List<CenterOwnerCourtListProjection> courtList = courtRepository.findCenterOwnerCourtListByCenterIdAndNameContainingAndStatus(requestData.getCenterId(), requestData.getQuery(), CourtStatus.ACTIVE.getValue());
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

    ResponseEntity<Object> getCenterOwnerCourtList(String jwtToken, CenterOwnerCourtListRequestData requestData) {
        Long userId;
        try {
            userId = JwtUtil.getUserIdFromToken(jwtToken);
        } catch (JWTVerificationException jwtVerificationException) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        CourtFilterCriteria filterCriteria = new CourtFilterCriteria();
        filterCriteria.setCenterId(requestData.getCenterId());
        filterCriteria.setStatus(CourtStatus.ACTIVE.getValue());

        List<Sort.Order> sortOrders = new ArrayList<>();
        sortOrders.add(new Sort.Order(Sort.Direction.ASC, "id"));

        Sort sort = Sort.by(sortOrders);

        Pageable pageable = PageRequest.of(Integer.parseInt("0"), Integer.parseInt("5"), sort);

        Page<CenterOwnerCourtListProjection> courtList = courtRepository.findBy(new CourtSpecification(filterCriteria), q -> q.as(CenterOwnerCourtListProjection.class).sortBy(pageable.getSort()).page(pageable));

        CenterOwnerCourtListResponseData responseData = new CenterOwnerCourtListResponseData();
        responseData.setTotalPage(courtList.getTotalPages());
        responseData.setCourtList(courtList.getContent());

        return new ResponseEntity<>(responseData, HttpStatus.OK);
    }
}
