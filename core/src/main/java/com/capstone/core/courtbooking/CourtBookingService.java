package com.capstone.core.courtbooking;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.capstone.core.court.CourtRepository;
import com.capstone.core.court.projection.CourtCenterWorkingTimeProjection;
import com.capstone.core.courtbooking.data.request.AddNewCourtBookingRequestData;
import com.capstone.core.courtbooking.data.request.CenterOwnerCourtBookingCenterListRequestData;
import com.capstone.core.courtbooking.data.request.CenterOwnerCourtBookingCourtListRequestData;
import com.capstone.core.courtbooking.data.request.CenterOwnerCourtBookingListRequestData;
import com.capstone.core.courtbooking.data.request.CenterOwnerCourtBookingUserListRequestData;
import com.capstone.core.courtbooking.data.request.CenterOwnerCourtCourtBookingListRequestData;
import com.capstone.core.courtbooking.data.request.UserCenterCourtBookingListRequestData;
import com.capstone.core.courtbooking.data.request.UserCenterListFromUserOrderRequestData;
import com.capstone.core.courtbooking.data.request.UserCourtBookingCenterListRequestData;
import com.capstone.core.courtbooking.data.request.UserCourtBookingCourtListRequestData;
import com.capstone.core.courtbooking.data.request.UserCourtBookingListRequestData;
import com.capstone.core.courtbooking.data.response.CenterOwnerCourtBookingListResponseData;
import com.capstone.core.courtbooking.data.response.CenterOwnerCourtCourtBookingListResponseData;
import com.capstone.core.courtbooking.data.response.UserCourtBookingListResponseData;
import com.capstone.core.courtbooking.projection.CenterListProjection;
import com.capstone.core.courtbooking.projection.CenterOwnerCourtBookingCenterListProjection;
import com.capstone.core.courtbooking.projection.CenterOwnerCourtBookingCourtListProjection;
import com.capstone.core.courtbooking.projection.CenterOwnerCourtBookingListProjection;
import com.capstone.core.courtbooking.projection.CenterOwnerCourtBookingUserListProjection;
import com.capstone.core.courtbooking.projection.CenterOwnerCourtCourtBookingListProjection;
import com.capstone.core.courtbooking.projection.CourtBookingListProjection;
import com.capstone.core.courtbooking.projection.UserCourtBookingCenterListProjection;
import com.capstone.core.courtbooking.projection.UserCourtBookingCourtListProjection;
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
    private CourtRepository courtRepository;

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

    ResponseEntity<Object> getUserCourtBookingList(String jwtToken, UserCourtBookingListRequestData requestData) {
        Long userId;
        try {
            userId = JwtUtil.getUserIdFromToken(jwtToken);
        } catch (JWTVerificationException jwtVerificationException) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        CourtBookingCriteria courtBookingCriteria = new CourtBookingCriteria();
        courtBookingCriteria.setUserId(userId);
        courtBookingCriteria.setId(requestData.getId());
        courtBookingCriteria.setCreateTimestampFrom(requestData.getCreateTimestampFrom());
        courtBookingCriteria.setCreateTimestampTo(requestData.getCreateTimestampTo());
        courtBookingCriteria.setCenterId(requestData.getCenterId());
        courtBookingCriteria.setCourtId(requestData.getCourtId());
        courtBookingCriteria.setUserId(requestData.getUserId());
        courtBookingCriteria.setUsageDateFrom(requestData.getUsageDateFrom());
        courtBookingCriteria.setUsageDateTo(requestData.getUsageDateTo());
        courtBookingCriteria.setUsageTimeStartFrom(requestData.getUsageTimeStartFrom());
        courtBookingCriteria.setUsageTimeStartTo(requestData.getUsageTimeStartTo());
        courtBookingCriteria.setUsageTimeEndFrom(requestData.getUsageTimeEndFrom());
        courtBookingCriteria.setUsageTimeEndTo(requestData.getUsageTimeEndTo());
        courtBookingCriteria.setStatusId(requestData.getStatusId());

        List<Sort.Order> sortOrders = new ArrayList<>();
        if (requestData.getIdSortOrder() != null) {
            sortOrders.add(new Sort.Order(requestData.getIdSortOrder().equalsIgnoreCase("desc") ? Sort.Direction.DESC : Sort.Direction.ASC, "id"));
        }
        if (requestData.getCenterSortOrder() != null) {
            sortOrders.add(new Sort.Order(requestData.getCenterSortOrder().equalsIgnoreCase("desc") ? Sort.Direction.DESC : Sort.Direction.ASC, "center"));
        }
        if (requestData.getCourtSortOrder() != null) {
            sortOrders.add(new Sort.Order(requestData.getCourtSortOrder().equalsIgnoreCase("desc") ? Sort.Direction.DESC : Sort.Direction.ASC, "court"));
        }
        if (requestData.getCreateTimestampSortOrder() != null) {
            sortOrders.add(new Sort.Order(requestData.getCreateTimestampSortOrder().equalsIgnoreCase("desc") ? Sort.Direction.DESC : Sort.Direction.ASC, "createTimestamp"));
        }
        if (requestData.getUsageDateSortOrder() != null) {
            sortOrders.add(new Sort.Order(requestData.getUsageDateSortOrder().equalsIgnoreCase("desc") ? Sort.Direction.DESC : Sort.Direction.ASC, "usageDate"));
        }
        if (requestData.getUsageTimeStartSortOrder() != null) {
            sortOrders.add(new Sort.Order(requestData.getUsageTimeStartSortOrder().equalsIgnoreCase("desc") ? Sort.Direction.DESC : Sort.Direction.ASC, "usageTimeStart"));
        }
        if (requestData.getUsageTimeEndSortOrder() != null) {
            sortOrders.add(new Sort.Order(requestData.getUsageTimeEndSortOrder().equalsIgnoreCase("desc") ? Sort.Direction.DESC : Sort.Direction.ASC, "usageTimeEnd"));
        }
        if (requestData.getStatusSortOrder() != null) {
            sortOrders.add(new Sort.Order(requestData.getStatusSortOrder().equalsIgnoreCase("desc") ? Sort.Direction.DESC : Sort.Direction.ASC, "status"));
        }

        Sort sort = Sort.by(sortOrders);

        Pageable pageable = PageRequest.of(requestData.getPageNo(), requestData.getPageSize(), sort);

        Page<UserCourtBookingListProjection> userCourtBookingList = courtBookingRepository.findBy(new CourtBookingSpecification(courtBookingCriteria), q -> q.as(UserCourtBookingListProjection.class).sortBy(pageable.getSort()).page(pageable));
        UserCourtBookingListResponseData responseData = new UserCourtBookingListResponseData();
        responseData.setTotalPage(userCourtBookingList.getTotalPages());
        responseData.setCourtBookingList(userCourtBookingList.getContent());

        return new ResponseEntity<>(responseData, HttpStatus.OK);
    }

    ResponseEntity<Object> getCenterOwnerCourtBookingList(String jwtToken, CenterOwnerCourtBookingListRequestData requestData) {
        Long userId;
        try {
            userId = JwtUtil.getUserIdFromToken(jwtToken);
        } catch (JWTVerificationException jwtVerificationException) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        CourtBookingCriteria courtBookingCriteria = new CourtBookingCriteria();
        courtBookingCriteria.setCenterUserId(userId);
        courtBookingCriteria.setId(requestData.getId());
        courtBookingCriteria.setCreateTimestampFrom(requestData.getCreateTimestampFrom());
        courtBookingCriteria.setCreateTimestampTo(requestData.getCreateTimestampTo());
        courtBookingCriteria.setUserId(requestData.getUserId());
        courtBookingCriteria.setCenterId(requestData.getCenterId());
        courtBookingCriteria.setCourtId(requestData.getCourtId());
        courtBookingCriteria.setUsageDateFrom(requestData.getUsageDateFrom());
        courtBookingCriteria.setUsageDateTo(requestData.getUsageDateTo());
        courtBookingCriteria.setUsageTimeStartFrom(requestData.getUsageTimeStartFrom());
        courtBookingCriteria.setUsageTimeStartTo(requestData.getUsageTimeStartTo());
        courtBookingCriteria.setUsageTimeEndFrom(requestData.getUsageTimeEndFrom());
        courtBookingCriteria.setUsageTimeEndTo(requestData.getUsageTimeEndTo());
        courtBookingCriteria.setStatusId(requestData.getStatusId());

        List<Sort.Order> sortOrders = new ArrayList<>();
        if (requestData.getIdSortOrder() != null) {
            sortOrders.add(new Sort.Order(requestData.getIdSortOrder().equalsIgnoreCase("desc") ? Sort.Direction.DESC : Sort.Direction.ASC, "id"));
        }
        if (requestData.getCreateTimestampSortOrder() != null) {
            sortOrders.add(new Sort.Order(requestData.getCreateTimestampSortOrder().equalsIgnoreCase("desc") ? Sort.Direction.DESC : Sort.Direction.ASC, "createTimestamp"));
        }
        if (requestData.getCenterSortOrder() != null) {
            sortOrders.add(new Sort.Order(requestData.getCenterSortOrder().equalsIgnoreCase("desc") ? Sort.Direction.DESC : Sort.Direction.ASC, "center"));
        }
        if (requestData.getCourtSortOrder() != null) {
            sortOrders.add(new Sort.Order(requestData.getCourtSortOrder().equalsIgnoreCase("desc") ? Sort.Direction.DESC : Sort.Direction.ASC, "court"));
        }
        if (requestData.getUserSortOrder() != null) {
            sortOrders.add(new Sort.Order(requestData.getUserSortOrder().equalsIgnoreCase("desc") ? Sort.Direction.DESC : Sort.Direction.ASC, "user"));
        }
        if (requestData.getUsageDateSortOrder() != null) {
            sortOrders.add(new Sort.Order(requestData.getUsageDateSortOrder().equalsIgnoreCase("desc") ? Sort.Direction.DESC : Sort.Direction.ASC, "usageDate"));
        }
        if (requestData.getUsageTimeStartSortOrder() != null) {
            sortOrders.add(new Sort.Order(requestData.getUsageTimeStartSortOrder().equalsIgnoreCase("desc") ? Sort.Direction.DESC : Sort.Direction.ASC, "usageTimeStart"));
        }
        if (requestData.getUsageTimeEndSortOrder() != null) {
            sortOrders.add(new Sort.Order(requestData.getUsageTimeEndSortOrder().equalsIgnoreCase("desc") ? Sort.Direction.DESC : Sort.Direction.ASC, "usageTimeEnd"));
        }
        if (requestData.getStatusSortOrder() != null) {
            sortOrders.add(new Sort.Order(requestData.getStatusSortOrder().equalsIgnoreCase("desc") ? Sort.Direction.DESC : Sort.Direction.ASC, "status"));
        }
        Sort sort = Sort.by(sortOrders);

        Pageable pageable = PageRequest.of(requestData.getPageNo(), requestData.getPageSize(), sort);

        CenterOwnerCourtBookingListResponseData responseData = new CenterOwnerCourtBookingListResponseData();
        Page<CenterOwnerCourtBookingListProjection> courtBookingList = courtBookingRepository.findBy(new CourtBookingSpecification(courtBookingCriteria), q -> q.as(CenterOwnerCourtBookingListProjection.class).sortBy(pageable.getSort()).page(pageable));
        responseData.setCourtBookingList(courtBookingList.getContent());
        responseData.setTotalPage(courtBookingList.getTotalPages());
        return new ResponseEntity<>(responseData, HttpStatus.OK);
    }

    ResponseEntity<Object> getUserCenterListFromUserOrder(String jwtToken, UserCenterListFromUserOrderRequestData requestData) {
        Long userId;
        try {
            userId = JwtUtil.getUserIdFromToken(jwtToken);
        } catch (JWTVerificationException jwtVerificationException) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        List<CenterListProjection> centerList = courtBookingRepository.findCenterListDistinctCenterIdByUserIdAndCenterNameContaining(userId, requestData.getQuery());
        return new ResponseEntity<>(centerList, HttpStatus.OK);
    }

    ResponseEntity<Object> getCenterOwnerCourtCourtBookingList(String jwtToken, CenterOwnerCourtCourtBookingListRequestData requestData) {
        Long userId;
        try {
            userId = JwtUtil.getUserIdFromToken(jwtToken);
        } catch (JWTVerificationException jwtVerificationException) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        CourtCenterWorkingTimeProjection workingTime = courtRepository.findWorkingTimeById(requestData.getCourtId());
        List<CenterOwnerCourtCourtBookingListProjection> courtBookingList = courtBookingRepository.findCourtBookingListByCourtId(requestData.getCourtId());

        List<Object> timeMarks = new ArrayList<>();
        Map<String, Object> timeMarksItem;
        LocalTime currentTime = workingTime.getCenter().getOpeningTime();
        int intervalMinutes = 15;

        if (courtBookingList.isEmpty()) {
            do {
                timeMarksItem = new HashMap<>();
                timeMarksItem.put("span", 1);
                timeMarks.add(timeMarksItem);
                currentTime = currentTime.plusMinutes(intervalMinutes);
            } while (!currentTime.isAfter(workingTime.getCenter().getClosingTime()));
        } else {
            int courtBookingListIndex = 0;
            Long courtBookingId = courtBookingList.get(courtBookingListIndex).getId();
            LocalTime bookedStart = courtBookingList.get(courtBookingListIndex).getUsageTimeStart();
            LocalTime bookedEnd = courtBookingList.get(courtBookingListIndex).getUsageTimeEnd();

            do {
                if (currentTime.equals(bookedStart)) {
                    int bookedSpan = (int) (bookedEnd.toSecondOfDay() - bookedStart.toSecondOfDay()) / (intervalMinutes * 60);
                    timeMarksItem = new HashMap<>();
                    timeMarksItem.put("courtBookingId", courtBookingId);
                    timeMarksItem.put("span", bookedSpan);
                    timeMarks.add(timeMarksItem);
                    currentTime = bookedEnd;

                    courtBookingListIndex++;
                    if (courtBookingListIndex < courtBookingList.size()) {
                        courtBookingId = courtBookingList.get(courtBookingListIndex).getId();
                        bookedStart = courtBookingList.get(courtBookingListIndex).getUsageTimeStart();
                        bookedEnd = courtBookingList.get(courtBookingListIndex).getUsageTimeEnd();
                    }
                } else {
                    timeMarksItem = new HashMap<>();
                    timeMarksItem.put("span", 1);
                    timeMarks.add(timeMarksItem);
                    currentTime = currentTime.plusMinutes(intervalMinutes);
                }
            } while (!currentTime.isAfter(workingTime.getCenter().getClosingTime()));
        }

        CenterOwnerCourtCourtBookingListResponseData responseData = new CenterOwnerCourtCourtBookingListResponseData();
        responseData.setTimeMarks(timeMarks);

        return new ResponseEntity<>(responseData, HttpStatus.OK);
    }

    ResponseEntity<Object> getCenterOwnerCourtBookingCenterList(String jwtToken, CenterOwnerCourtBookingCenterListRequestData requestData) {
        Long userId;
        try {
            userId = JwtUtil.getUserIdFromToken(jwtToken);
        } catch (JWTVerificationException jwtVerificationException) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        List<CenterOwnerCourtBookingCenterListProjection> centerList = courtBookingRepository.findCenterOwnerCourtBookingCenterListDistinctCenterByCenterUserIdAndCenterNameContaining(userId, requestData.getQuery());
        return new ResponseEntity<>(centerList, HttpStatus.OK);
    }

    ResponseEntity<Object> getCenterOwnerCourtBookingCourtList(String jwtToken, CenterOwnerCourtBookingCourtListRequestData requestData) {
        Long userId;
        try {
            userId = JwtUtil.getUserIdFromToken(jwtToken);
        } catch (JWTVerificationException jwtVerificationException) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        List<CenterOwnerCourtBookingCourtListProjection> courtList = courtBookingRepository.findCenterOwnerCourtBookingCourtListDistinctCourtByCenterUserIdAndCourtNameContaining(userId, requestData.getQuery());
        return new ResponseEntity<>(courtList, HttpStatus.OK);
    }

    ResponseEntity<Object> getCenterOwnerCourtBookingUserList(String jwtToken, CenterOwnerCourtBookingUserListRequestData requestData) {
        Long userId;
        try {
            userId = JwtUtil.getUserIdFromToken(jwtToken);
        } catch (JWTVerificationException jwtVerificationException) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        List<CenterOwnerCourtBookingUserListProjection> userList = courtBookingRepository.findCenterOwnerCourtBookingUserListDistinctUserByCenterUserIdAndUserUsernameContaining(userId, requestData.getQuery());
        return new ResponseEntity<>(userList, HttpStatus.OK);
    }

    ResponseEntity<Object> getUserCourtBookingCenterList(String jwtToken, UserCourtBookingCenterListRequestData requestData) {
        Long userId;
        try {
            userId = JwtUtil.getUserIdFromToken(jwtToken);
        } catch (JWTVerificationException jwtVerificationException) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        List<UserCourtBookingCenterListProjection> centerList = courtBookingRepository.findUserCourtBookingCenterListDistinctCenterIdByUserIdAndCenterNameContaining(userId, requestData.getQuery());
        return new ResponseEntity<>(centerList, HttpStatus.OK);
    }

    ResponseEntity<Object> getUserCourtBookingCourtList(String jwtToken, UserCourtBookingCourtListRequestData requestData) {
        Long userId;
        try {
            userId = JwtUtil.getUserIdFromToken(jwtToken);
        } catch (JWTVerificationException jwtVerificationException) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        List<UserCourtBookingCourtListProjection> courtList = courtBookingRepository.findUserCourtBookingCourtListDistinctCourtIdByUserIdAndCourtNameContaining(userId, requestData.getQuery());
        return new ResponseEntity<>(courtList, HttpStatus.OK);
    }
}
