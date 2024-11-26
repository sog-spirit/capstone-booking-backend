package com.capstone.core.courtbooking;

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
import com.capstone.core.courtbooking.data.request.AddNewCourtBookingRequestData;
import com.capstone.core.courtbooking.data.request.UserCenterCourtBookingListRequestData;
import com.capstone.core.courtbooking.data.request.UserCenterListFromUserOrderRequestData;
import com.capstone.core.courtbooking.projection.CenterListProjection;
import com.capstone.core.courtbooking.projection.CenterOwnerCourtBookingListProjection;
import com.capstone.core.courtbooking.projection.CourtBookingListProjection;
import com.capstone.core.courtbooking.projection.UserCourtBookingListProjection;
import com.capstone.core.courtbooking.specification.CourtBookingSpecification;
import com.capstone.core.courtbooking.specification.criteria.CourtBookingCriteria;
import com.capstone.core.table.BookingOrderStatusTable;
import com.capstone.core.table.CenterTable;
import com.capstone.core.table.CourtBookingTable;
import com.capstone.core.table.CourtTable;
import com.capstone.core.table.UserTable;
import com.capstone.core.util.consts.BookingOrderStatus;
import com.capstone.core.util.security.jwt.JwtUtil;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class CourtBookingService {
    private CourtBookingRepository courtBookingRepository;

    ResponseEntity<Object> addNewCourtBooking(String jwtToken, AddNewCourtBookingRequestData addNewCourtBookingRequestData) {
        Long userId;
        try {
            userId = JwtUtil.getUserIdFromToken(jwtToken);
        } catch (JWTVerificationException jwtVerificationException) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        CourtBookingTable courtBooking = new CourtBookingTable();

        CenterTable center = new CenterTable();
        center.setId(addNewCourtBookingRequestData.getCenterId());
        courtBooking.setCenter(center);

        CourtTable court = new CourtTable();
        court.setId(addNewCourtBookingRequestData.getCourtId());
        courtBooking.setCourt(court);

        UserTable user = new UserTable();
        user.setId(userId);
        courtBooking.setUser(user);

        courtBooking.setUsageDate(addNewCourtBookingRequestData.getUsageDate());
        courtBooking.setUsageTimeStart(addNewCourtBookingRequestData.getUsageTimeStart());
        courtBooking.setUsageTimeEnd(addNewCourtBookingRequestData.getUsageTimeEnd());
        BookingOrderStatusTable bookingOrderStatus = new BookingOrderStatusTable();
        bookingOrderStatus.setId(BookingOrderStatus.PENDING.getValue());
        courtBooking.setStatus(bookingOrderStatus);
        courtBooking.setCreateTimestamp(LocalDateTime.now());
        courtBookingRepository.save(courtBooking);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    ResponseEntity<Object> getUserCenterCourtBookingList(String jwtToken, UserCenterCourtBookingListRequestData requestData) {
        List<CourtBookingListProjection> courtBookingList = courtBookingRepository.findByCenterIdAndCourtId(requestData.getCenterId(), requestData.getCourtId());
        return new ResponseEntity<>(courtBookingList, HttpStatus.OK);
    }

    ResponseEntity<Object> getUserCourtBookingList(String jwtToken) {
        Long userId;
        try {
            userId = JwtUtil.getUserIdFromToken(jwtToken);
        } catch (JWTVerificationException jwtVerificationException) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        CourtBookingCriteria courtBookingCriteria = new CourtBookingCriteria();
        courtBookingCriteria.setUserId(userId);

        List<Sort.Order> sortOrders = new ArrayList<>();

        Sort sort = Sort.by(sortOrders);

        Pageable pageable = PageRequest.of(Integer.parseInt("0"), Integer.parseInt("5"), sort);

        Page<UserCourtBookingListProjection> userCourtBookingList = courtBookingRepository.findBy(new CourtBookingSpecification(courtBookingCriteria), q -> q.as(UserCourtBookingListProjection.class).sortBy(pageable.getSort()).page(pageable));

        return new ResponseEntity<>(userCourtBookingList.getContent(), HttpStatus.OK);
    }

    ResponseEntity<Object> getCenterOwnerCourtBookingList(String jwtToken) {
        Long userId;
        try {
            userId = JwtUtil.getUserIdFromToken(jwtToken);
        } catch (JWTVerificationException jwtVerificationException) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        List<CenterOwnerCourtBookingListProjection> centerOwnerCourtBookingList = courtBookingRepository.findByCenterUserId(userId);
        return new ResponseEntity<>(centerOwnerCourtBookingList, HttpStatus.OK);
    }

    ResponseEntity<Object> getUserCenterListFromUserOrder(String jwtToken, UserCenterListFromUserOrderRequestData requestData) {
        Long userId;
        try {
            userId = JwtUtil.getUserIdFromToken(jwtToken);
        } catch (JWTVerificationException jwtVerificationException) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        List<CenterListProjection> centerList = courtBookingRepository.findCenterListByUserIdAndCenterNameContaining(userId, requestData.getQuery());
        return new ResponseEntity<>(centerList, HttpStatus.OK);
    }
}
