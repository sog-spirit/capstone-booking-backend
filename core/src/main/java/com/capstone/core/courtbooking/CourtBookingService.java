package com.capstone.core.courtbooking;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.YearMonth;
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
import com.capstone.core.center.CenterRepository;
import com.capstone.core.court.CourtRepository;
import com.capstone.core.court.projection.CourtCenterWorkingTimeProjection;
import com.capstone.core.courtbooking.data.request.AddNewCourtBookingRequestData;
import com.capstone.core.courtbooking.data.request.AdminStatisticsCenterRequestData;
import com.capstone.core.courtbooking.data.request.CenterOwnerCancelCourtBookingRequestData;
import com.capstone.core.courtbooking.data.request.CenterOwnerCheckoutCourtBookingRequestData;
import com.capstone.core.courtbooking.data.request.CenterOwnerCourtBookingCenterListRequestData;
import com.capstone.core.courtbooking.data.request.CenterOwnerCourtBookingCourtListRequestData;
import com.capstone.core.courtbooking.data.request.CenterOwnerCourtBookingDetailRequestData;
import com.capstone.core.courtbooking.data.request.CenterOwnerCourtBookingListRequestData;
import com.capstone.core.courtbooking.data.request.CenterOwnerCourtBookingUserListRequestData;
import com.capstone.core.courtbooking.data.request.CenterOwnerCourtCourtBookingListRequestData;
import com.capstone.core.courtbooking.data.request.CenterOwnerStatisticsCenterRequestData;
import com.capstone.core.courtbooking.data.request.UserCancelCourtBookingRequestData;
import com.capstone.core.courtbooking.data.request.UserCenterCourtBookingListRequestData;
import com.capstone.core.courtbooking.data.request.UserCenterListFromUserOrderRequestData;
import com.capstone.core.courtbooking.data.request.UserCourtBookingCenterIdRequestData;
import com.capstone.core.courtbooking.data.request.UserCourtBookingCenterListRequestData;
import com.capstone.core.courtbooking.data.request.UserCourtBookingCourtListRequestData;
import com.capstone.core.courtbooking.data.request.UserCourtBookingDetailRequestData;
import com.capstone.core.courtbooking.data.request.UserCourtBookingListRequestData;
import com.capstone.core.courtbooking.data.request.UserCourtCourtBookingListRequestData;
import com.capstone.core.courtbooking.data.request.UserCourtDateCourtBookingRequestData;
import com.capstone.core.courtbooking.data.response.CenterOwnerCourtBookingListResponseData;
import com.capstone.core.courtbooking.data.response.CenterOwnerCourtCourtBookingListResponseData;
import com.capstone.core.courtbooking.data.response.UserCourtBookingListResponseData;
import com.capstone.core.courtbooking.data.response.UserCourtCourtBookingListResponseData;
import com.capstone.core.courtbooking.data.response.UserCourtDateCourtBookingListResponseData;
import com.capstone.core.courtbooking.projection.AdminStatisticsCenterProjection;
import com.capstone.core.courtbooking.projection.AdminStatisticsTodayProjection;
import com.capstone.core.courtbooking.projection.CenterListProjection;
import com.capstone.core.courtbooking.projection.CenterOwnerCourtBookingCenterListProjection;
import com.capstone.core.courtbooking.projection.CenterOwnerCourtBookingCourtListProjection;
import com.capstone.core.courtbooking.projection.CenterOwnerCourtBookingDetailProjection;
import com.capstone.core.courtbooking.projection.CenterOwnerCourtBookingListProjection;
import com.capstone.core.courtbooking.projection.CenterOwnerCourtBookingUserListProjection;
import com.capstone.core.courtbooking.projection.CenterOwnerCourtCourtBookingListProjection;
import com.capstone.core.courtbooking.projection.CenterOwnerStatisticsCenterProjection;
import com.capstone.core.courtbooking.projection.CenterOwnerStatisticsTodayProjection;
import com.capstone.core.courtbooking.projection.CourtBookingListProjection;
import com.capstone.core.courtbooking.projection.UserCourtBookingCenterIdProjection;
import com.capstone.core.courtbooking.projection.UserCourtBookingCenterListProjection;
import com.capstone.core.courtbooking.projection.UserCourtBookingCourtListProjection;
import com.capstone.core.courtbooking.projection.UserCourtBookingDetailProjection;
import com.capstone.core.courtbooking.projection.UserCourtBookingListProjection;
import com.capstone.core.courtbooking.projection.UserCourtCourtBookingListProjection;
import com.capstone.core.courtbooking.specification.CourtBookingSpecification;
import com.capstone.core.courtbooking.specification.criteria.CourtBookingCriteria;
import com.capstone.core.table.CenterTable;
import com.capstone.core.table.CourtBookingTable;
import com.capstone.core.table.CourtTable;
import com.capstone.core.table.UserTable;
import com.capstone.core.util.consts.CourtBookingStatus;
import com.capstone.core.util.consts.CourtStatus;
import com.capstone.core.util.security.jwt.JwtUtil;
import com.capstone.core.util.time.TimeUtils;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class CourtBookingService {
    private CourtBookingRepository courtBookingRepository;
    private CourtRepository courtRepository;
    private CenterRepository centerRepository;

    ResponseEntity<Object> addUserNewCourtBooking(String jwtToken, AddNewCourtBookingRequestData addNewCourtBookingRequestData) {
        Long userId;
        try {
            userId = JwtUtil.getUserIdFromToken(jwtToken);
        } catch (JWTVerificationException jwtVerificationException) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        CenterTable center = centerRepository.getReferenceById(addNewCourtBookingRequestData.getCenterId());

        CourtBookingTable courtBooking = new CourtBookingTable();

        CourtTable court = new CourtTable();
        court.setId(addNewCourtBookingRequestData.getCourtId());
        courtBooking.setCourt(court);

        UserTable user = new UserTable();
        user.setId(userId);
        courtBooking.setUser(user);

        courtBooking.setUsageDate(addNewCourtBookingRequestData.getUsageDate());
        courtBooking.setUsageTimeStart(addNewCourtBookingRequestData.getUsageTimeStart());
        courtBooking.setUsageTimeEnd(addNewCourtBookingRequestData.getUsageTimeEnd());
        courtBooking.setStatus(CourtBookingStatus.PENDING.getValue());
        courtBooking.setCreateTimestamp(LocalDateTime.now());
        courtBooking.setCourtFee(TimeUtils.calculateIntervals(addNewCourtBookingRequestData.getUsageTimeStart(), addNewCourtBookingRequestData.getUsageTimeEnd(), 15) * (center.getCourtFee() / 4));
        courtBooking.setProductFee(Long.parseLong("0"));

        courtBookingRepository.save(courtBooking);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    ResponseEntity<Object> addCenterOwnerNewCourtBooking(String jwtToken, AddNewCourtBookingRequestData addNewCourtBookingRequestData) {
        Long userId;
        try {
            userId = JwtUtil.getUserIdFromToken(jwtToken);
        } catch (JWTVerificationException jwtVerificationException) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        CenterTable center = centerRepository.getReferenceById(addNewCourtBookingRequestData.getCenterId());

        CourtBookingTable courtBooking = new CourtBookingTable();

        CourtTable court = new CourtTable();
        court.setId(addNewCourtBookingRequestData.getCourtId());
        courtBooking.setCourt(court);

        UserTable user = new UserTable();
        user.setId(userId);
        courtBooking.setUser(user);

        courtBooking.setUsageDate(addNewCourtBookingRequestData.getUsageDate());
        courtBooking.setUsageTimeStart(addNewCourtBookingRequestData.getUsageTimeStart());
        courtBooking.setUsageTimeEnd(addNewCourtBookingRequestData.getUsageTimeEnd());
        courtBooking.setStatus(CourtBookingStatus.UNAVAILABLE.getValue());
        courtBooking.setCreateTimestamp(LocalDateTime.now());
        courtBooking.setCourtFee(Long.parseLong("0"));
        courtBooking.setProductFee(Long.parseLong("0"));

        courtBookingRepository.save(courtBooking);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    ResponseEntity<Object> userCancelCourtBooking(String jwtToken, UserCancelCourtBookingRequestData requestData) {
        Long userId;
        try {
            userId = JwtUtil.getUserIdFromToken(jwtToken);
        } catch (JWTVerificationException jwtVerificationException) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        CourtBookingTable courtBooking = courtBookingRepository.getReferenceById(requestData.getId());
        courtBooking.setStatus(CourtBookingStatus.CANCELLED.getValue());
        courtBookingRepository.save(courtBooking);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    ResponseEntity<Object> centerOwnerCancelCourtBooking(String jwtToken, CenterOwnerCancelCourtBookingRequestData requestData) {
        Long userId;
        try {
            userId = JwtUtil.getUserIdFromToken(jwtToken);
        } catch (JWTVerificationException jwtVerificationException) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        CourtBookingTable courtBooking = courtBookingRepository.getReferenceById(requestData.getId());
        courtBooking.setStatus(CourtBookingStatus.CANCELLED.getValue());
        courtBookingRepository.save(courtBooking);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    ResponseEntity<Object> centerOwnerCheckoutCourtBooking(String jwtToken, CenterOwnerCheckoutCourtBookingRequestData requestData) {
        Long userId;
        try {
            userId = JwtUtil.getUserIdFromToken(jwtToken);
        } catch (JWTVerificationException jwtVerificationException) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        CourtBookingTable courtBooking = courtBookingRepository.getReferenceById(requestData.getId());
        courtBooking.setStatus(CourtBookingStatus.BOOKED.getValue());
        courtBookingRepository.save(courtBooking);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    ResponseEntity<Object> getUserCenterCourtBookingList(String jwtToken, UserCenterCourtBookingListRequestData requestData) {
        List<CourtBookingListProjection> courtBookingList = courtBookingRepository.findByCourtCenterIdAndCourtId(requestData.getCenterId(), requestData.getCourtId());
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
        List<CenterListProjection> centerList = courtBookingRepository.findNotReviewedCenterList(userId, requestData.getQuery());
        return new ResponseEntity<>(centerList, HttpStatus.OK);
    }

    ResponseEntity<Object> getCenterOwnerCourtCourtBookingList(String jwtToken, CenterOwnerCourtCourtBookingListRequestData requestData) {
        Long userId;
        try {
            userId = JwtUtil.getUserIdFromToken(jwtToken);
        } catch (JWTVerificationException jwtVerificationException) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        CourtCenterWorkingTimeProjection workingTime = courtRepository.findWorkingTimeByIdAndStatus(requestData.getCourtId(), CourtStatus.ACTIVE.getValue());
        List<CenterOwnerCourtCourtBookingListProjection> courtBookingList = courtBookingRepository.findCenterOwnerCourtCourtBookingListByCourtIdAndUsageDateAndStatusNotOrderByUsageTimeStartAscUsageTimeEndAsc(requestData.getCourtId(), requestData.getUsageDate(), CourtBookingStatus.CANCELLED.getValue());

        List<Object> timeMarks = new ArrayList<>();
        Map<String, Object> timeMarksItem;
        LocalTime currentTime = workingTime.getCenter().getOpeningTime();
        int intervalMinutes = 60;

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
            String status = CourtBookingStatus.getName(courtBookingList.get(courtBookingListIndex).getStatus());

            do {
                if (currentTime.equals(bookedStart)) {
                    int bookedSpan = (int) (bookedEnd.toSecondOfDay() - bookedStart.toSecondOfDay()) / (intervalMinutes * 60);
                    timeMarksItem = new HashMap<>();
                    timeMarksItem.put("courtBookingId", courtBookingId);
                    timeMarksItem.put("span", bookedSpan);
                    timeMarksItem.put("status", status);
                    timeMarks.add(timeMarksItem);
                    currentTime = bookedEnd;

                    courtBookingListIndex++;
                    if (courtBookingListIndex < courtBookingList.size()) {
                        courtBookingId = courtBookingList.get(courtBookingListIndex).getId();
                        bookedStart = courtBookingList.get(courtBookingListIndex).getUsageTimeStart();
                        bookedEnd = courtBookingList.get(courtBookingListIndex).getUsageTimeEnd();
                        status = CourtBookingStatus.getName(courtBookingList.get(courtBookingListIndex).getStatus());
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

    ResponseEntity<Object> getUserCourtCourtBookingList(String jwtToken, UserCourtCourtBookingListRequestData requestData) {
        Long userId;
        try {
            userId = JwtUtil.getUserIdFromToken(jwtToken);
        } catch (JWTVerificationException jwtVerificationException) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        CourtCenterWorkingTimeProjection workingTime = courtRepository.findWorkingTimeByIdAndStatus(requestData.getCourtId(), CourtStatus.ACTIVE.getValue());
        List<UserCourtCourtBookingListProjection> courtBookingList = courtBookingRepository.findUserCourtCourtBookingListByCourtIdAndUsageDateAndStatusNotOrderByUsageTimeStartAscUsageTimeEndAsc(requestData.getCourtId(), requestData.getDate(), CourtBookingStatus.CANCELLED.getValue());

        List<Object> timeMarks = new ArrayList<>();
        Map<String, Object> timeMarksItem;
        LocalTime currentTime = workingTime.getCenter().getOpeningTime();
        int intervalMinutes = 60;

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
            Long courtBookingUserId = courtBookingList.get(courtBookingListIndex).getUserId();
            LocalTime bookedStart = courtBookingList.get(courtBookingListIndex).getUsageTimeStart();
            LocalTime bookedEnd = courtBookingList.get(courtBookingListIndex).getUsageTimeEnd();
            String status = CourtBookingStatus.getName(courtBookingList.get(courtBookingListIndex).getStatus());

            do {
                if (currentTime.equals(bookedStart)) {
                    int bookedSpan = (int) (bookedEnd.toSecondOfDay() - bookedStart.toSecondOfDay()) / (intervalMinutes * 60);
                    timeMarksItem = new HashMap<>();
                    if (userId == courtBookingUserId) {
                        timeMarksItem.put("courtBookingId", courtBookingId);
                    }
                    timeMarksItem.put("span", bookedSpan);
                    timeMarksItem.put("status", status);
                    timeMarks.add(timeMarksItem);
                    currentTime = bookedEnd;

                    courtBookingListIndex++;
                    if (courtBookingListIndex < courtBookingList.size()) {
                        courtBookingId = courtBookingList.get(courtBookingListIndex).getId();
                        courtBookingUserId = courtBookingList.get(courtBookingListIndex).getUserId();
                        bookedStart = courtBookingList.get(courtBookingListIndex).getUsageTimeStart();
                        bookedEnd = courtBookingList.get(courtBookingListIndex).getUsageTimeEnd();
                        status = CourtBookingStatus.getName(courtBookingList.get(courtBookingListIndex).getStatus());
                    }
                } else {
                    timeMarksItem = new HashMap<>();
                    timeMarksItem.put("span", 1);
                    timeMarks.add(timeMarksItem);
                    currentTime = currentTime.plusMinutes(intervalMinutes);
                }
            } while (!currentTime.isAfter(workingTime.getCenter().getClosingTime()));
        }

        UserCourtCourtBookingListResponseData responseData = new UserCourtCourtBookingListResponseData();
        responseData.setTimeMarks(timeMarks);

        return new ResponseEntity<>(responseData, HttpStatus.OK);
    }

    ResponseEntity<Object> getUserCourtDateCourtBookingList(String jwtToken, UserCourtDateCourtBookingRequestData requestData) {
        Long userId;
        try {
            userId = JwtUtil.getUserIdFromToken(jwtToken);
        } catch (JWTVerificationException jwtVerificationException) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        CourtCenterWorkingTimeProjection workingTime = courtRepository.findWorkingTimeByIdAndStatus(requestData.getCourtId(), CourtStatus.ACTIVE.getValue());
        List<UserCourtCourtBookingListProjection> courtBookingList = courtBookingRepository.findUserCourtCourtBookingListByCourtIdAndUsageDateAndStatusNotOrderByUsageTimeStartAscUsageTimeEndAsc(requestData.getCourtId(), requestData.getUsageDate(), CourtBookingStatus.CANCELLED.getValue());

        List<Object> timeMarks = new ArrayList<>();
        Map<String, Object> timeMarksItem;
        LocalTime currentTime = workingTime.getCenter().getOpeningTime();
        int intervalMinutes = 60;
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");

        if (courtBookingList.isEmpty()) {
            do {
                timeMarksItem = new HashMap<>();
                timeMarksItem.put("time", String.format("%s-%s", currentTime.format(timeFormatter), currentTime.plusMinutes(intervalMinutes).format(timeFormatter)));
                timeMarksItem.put("status", "Available");
                timeMarks.add(timeMarksItem);
                currentTime = currentTime.plusMinutes(intervalMinutes);
            } while (!currentTime.isAfter(workingTime.getCenter().getClosingTime()));
        } else {
            int courtBookingListIndex = 0;
            LocalTime bookedStart = courtBookingList.get(courtBookingListIndex).getUsageTimeStart();
            LocalTime bookedEnd = courtBookingList.get(courtBookingListIndex).getUsageTimeEnd();
            String status = CourtBookingStatus.getName(courtBookingList.get(courtBookingListIndex).getStatus());

            do {
                if (currentTime.equals(bookedStart) ||
                        (currentTime.isAfter(bookedStart) && currentTime.isBefore(bookedEnd))) {
                    int bookedSpan = (int) (bookedEnd.toSecondOfDay() - bookedStart.toSecondOfDay()) / (intervalMinutes * 60);
                    for (int i = 1; i <= bookedSpan; i++) {
                        timeMarksItem = new HashMap<>();
                        timeMarksItem.put("status", status);
                        timeMarksItem.put("time", String.format("%s-%s", currentTime.format(timeFormatter), currentTime.plusMinutes(intervalMinutes).format(timeFormatter)));
                        timeMarks.add(timeMarksItem);
                        currentTime = currentTime.plusMinutes(intervalMinutes);
                    }
                    
                    courtBookingListIndex++;
                    if (courtBookingListIndex < courtBookingList.size()) {
                        bookedStart = courtBookingList.get(courtBookingListIndex).getUsageTimeStart();
                        bookedEnd = courtBookingList.get(courtBookingListIndex).getUsageTimeEnd();
                        status = CourtBookingStatus.getName(courtBookingList.get(courtBookingListIndex).getStatus());
                    }
                } else {
                    timeMarksItem = new HashMap<>();
                    timeMarksItem.put("time", String.format("%s-%s", currentTime.format(timeFormatter), currentTime.plusMinutes(intervalMinutes).format(timeFormatter)));
                    timeMarksItem.put("status", "Available");
                    timeMarks.add(timeMarksItem);
                    currentTime = currentTime.plusMinutes(intervalMinutes);
                }
            } while (!currentTime.isAfter(workingTime.getCenter().getClosingTime()));
        }

        UserCourtDateCourtBookingListResponseData responseData = new UserCourtDateCourtBookingListResponseData();
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

        List<CenterOwnerCourtBookingCenterListProjection> centerList = courtBookingRepository.findCenterOwnerCourtBookingCenterListDistinctCourtCenterIdByCourtCenterUserIdAndCourtCenterNameContaining(userId, requestData.getQuery());
        return new ResponseEntity<>(centerList, HttpStatus.OK);
    }

    ResponseEntity<Object> getCenterOwnerCourtBookingCourtList(String jwtToken, CenterOwnerCourtBookingCourtListRequestData requestData) {
        Long userId;
        try {
            userId = JwtUtil.getUserIdFromToken(jwtToken);
        } catch (JWTVerificationException jwtVerificationException) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        List<CenterOwnerCourtBookingCourtListProjection> courtList = courtBookingRepository.findCenterOwnerCourtBookingCourtListDistinctCourtIdByCourtCenterUserIdAndCourtNameContaining(userId, requestData.getQuery());
        return new ResponseEntity<>(courtList, HttpStatus.OK);
    }

    ResponseEntity<Object> getCenterOwnerCourtBookingUserList(String jwtToken, CenterOwnerCourtBookingUserListRequestData requestData) {
        Long userId;
        try {
            userId = JwtUtil.getUserIdFromToken(jwtToken);
        } catch (JWTVerificationException jwtVerificationException) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        List<CenterOwnerCourtBookingUserListProjection> userList = courtBookingRepository.findCenterOwnerCourtBookingUserListDistinctUserIdByCourtCenterUserIdAndUserUsernameContaining(userId, requestData.getQuery());
        return new ResponseEntity<>(userList, HttpStatus.OK);
    }

    ResponseEntity<Object> getUserCourtBookingCenterList(String jwtToken, UserCourtBookingCenterListRequestData requestData) {
        Long userId;
        try {
            userId = JwtUtil.getUserIdFromToken(jwtToken);
        } catch (JWTVerificationException jwtVerificationException) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        List<UserCourtBookingCenterListProjection> centerList = courtBookingRepository.findUserCourtBookingCenterListDistinctCourtCenterIdByUserIdAndCourtCenterNameContaining(userId, requestData.getQuery());
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

    ResponseEntity<Object> getUserCourtBookingDetail(String jwtToken, UserCourtBookingDetailRequestData requestData) {
        Long userId;
        try {
            userId = JwtUtil.getUserIdFromToken(jwtToken);
        } catch (JWTVerificationException jwtVerificationException) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        UserCourtBookingDetailProjection courtBookingDetail = courtBookingRepository.findUserCourtBookingDetailById(requestData.getCourtBookingId());
        return new ResponseEntity<>(courtBookingDetail, HttpStatus.OK);
    }

    ResponseEntity<Object> getCenterOwnerCourtBookingDetail(String jwtToken, CenterOwnerCourtBookingDetailRequestData requestData) {
        Long userId;
        try {
            userId = JwtUtil.getUserIdFromToken(jwtToken);
        } catch (JWTVerificationException jwtVerificationException) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        CenterOwnerCourtBookingDetailProjection courtBookingDetail = courtBookingRepository.findCenterOwnerCourtBookingDetailById(requestData.getCourtBookingId());
        return new ResponseEntity<>(courtBookingDetail, HttpStatus.OK);
    }

    ResponseEntity<Object> getUserCourtBookingCenterId(String jwtToken, UserCourtBookingCenterIdRequestData requestData) {
        UserCourtBookingCenterIdProjection centerId = courtBookingRepository.findUserCourtBookingCenterIdById(requestData.getCourtBookingId());
        return new ResponseEntity<>(centerId, HttpStatus.OK);
    }

    ResponseEntity<Object> getCenterOwnerStatisticsCenter(String jwtToken, CenterOwnerStatisticsCenterRequestData requestData) {
        Long userId;
        try {
            userId = JwtUtil.getUserIdFromToken(jwtToken);
        } catch (JWTVerificationException jwtVerificationException) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        if (requestData.getDateFrom() != null && requestData.getDateTo() != null) {
            List<Map<String, Object>> result = new ArrayList<>();
            Map<String, Object> resultItem;

            LocalDate dateFrom = requestData.getDateFrom();
            LocalDate dateTo = requestData.getDateTo();

            while (dateFrom.isBefore(dateTo) || dateFrom.isEqual(dateTo)) {
                CenterOwnerStatisticsCenterProjection queryData = courtBookingRepository.findCenterOwnerStatisticsCenterDaily(userId, requestData.getCenterId(), dateFrom);

                resultItem = new HashMap<>();
                resultItem.put("label", dateFrom);
                resultItem.put("courtFee", queryData.getCourtFee());
                resultItem.put("centerFee", queryData.getProductFee());

                result.add(resultItem);

                dateFrom = dateFrom.plusDays(1);
            }

            return new ResponseEntity<>(result, HttpStatus.OK);
        } else if (requestData.getMonthFrom() != null && requestData.getMonthTo() != null) {
            DateTimeFormatter yearMonthFormatter = DateTimeFormatter.ofPattern("yyyy-MM");
            YearMonth yearMonthFrom = YearMonth.parse(requestData.getMonthFrom(), yearMonthFormatter);
            YearMonth yearMonthTo = YearMonth.parse(requestData.getMonthTo(), yearMonthFormatter);
            List<Map<String, Object>> result = new ArrayList<>();
            Map<String, Object> resultItem;

            while (yearMonthFrom.isBefore(yearMonthTo) || yearMonthFrom.equals(yearMonthTo)) {
                LocalDate firstDayOfMonth = yearMonthFrom.atDay(1);
                LocalDate lastDayOfMonth = yearMonthFrom.atEndOfMonth();
                CenterOwnerStatisticsCenterProjection queryData = courtBookingRepository.findCenterOwnerStatisticsCenterMonthly(userId, requestData.getCenterId(), firstDayOfMonth, lastDayOfMonth);

                resultItem = new HashMap<>();
                resultItem.put("label", yearMonthFrom);
                resultItem.put("courtFee", queryData.getCourtFee());
                resultItem.put("centerFee", queryData.getProductFee());

                result.add(resultItem);

                yearMonthFrom = yearMonthFrom.plusMonths(1);
            }

            return new ResponseEntity<>(result, HttpStatus.OK);
        } else if (requestData.getYearFrom() != null && requestData.getYearTo() != null) {
            YearMonth yearMonthFrom = YearMonth.of(Integer.parseInt(requestData.getYearFrom()), 1);
            YearMonth yearMonthTo = YearMonth.of(Integer.parseInt(requestData.getYearTo()), 1);
            List<Map<String, Object>> result = new ArrayList<>();
            Map<String, Object> resultItem;

            while (yearMonthFrom.isBefore(yearMonthTo) || yearMonthFrom.equals(yearMonthTo)) {
                LocalDate firstDayOfYear = LocalDate.of(yearMonthFrom.getYear(), 1, 1);
                LocalDate lastDayOfYear = LocalDate.of(yearMonthFrom.getYear(), 12, 31);
                CenterOwnerStatisticsCenterProjection queryData = courtBookingRepository.findCenterOwnerStatisticsCenterMonthly(userId, requestData.getCenterId(), firstDayOfYear, lastDayOfYear);

                resultItem = new HashMap<>();
                resultItem.put("label", yearMonthFrom.getYear());
                resultItem.put("courtFee", queryData.getCourtFee());
                resultItem.put("centerFee", queryData.getProductFee());

                result.add(resultItem);

                yearMonthFrom = yearMonthFrom.plusYears(1);
            }

            return new ResponseEntity<>(result, HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.OK);
    }

    ResponseEntity<Object> getCenterOwnerStatisticsToday(String jwtToken) {
        Long userId;
        try {
            userId = JwtUtil.getUserIdFromToken(jwtToken);
        } catch (JWTVerificationException jwtVerificationException) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        LocalDate dateNow = LocalDate.now();
        LocalDateTime localDateTimeFrom = dateNow.atStartOfDay();
        LocalDateTime localDateTimeTo = dateNow.plusDays(1).atStartOfDay();
        CenterOwnerStatisticsTodayProjection todayStatistics = courtBookingRepository.findCenterOwnerStatisticsToday(userId, localDateTimeFrom, localDateTimeTo);
        return new ResponseEntity<Object>(todayStatistics, HttpStatus.OK);
    }

    ResponseEntity<Object> getAdminStatisticsCenter(String jwtToken, AdminStatisticsCenterRequestData requestData) {
        Long userId;
        try {
            userId = JwtUtil.getUserIdFromToken(jwtToken);
        } catch (JWTVerificationException jwtVerificationException) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        if (requestData.getDateFrom() != null && requestData.getDateTo() != null) {
            List<Map<String, Object>> result = new ArrayList<>();
            Map<String, Object> resultItem;

            LocalDate dateFrom = requestData.getDateFrom();
            LocalDate dateTo = requestData.getDateTo();

            while (dateFrom.isBefore(dateTo) || dateFrom.isEqual(dateTo)) {
                AdminStatisticsCenterProjection queryData = courtBookingRepository.findAdminStatisticsCenterDaily(requestData.getCenterId(), dateFrom);

                resultItem = new HashMap<>();
                resultItem.put("label", dateFrom);
                resultItem.put("courtFee", queryData.getCourtFee());
                resultItem.put("centerFee", queryData.getProductFee());

                result.add(resultItem);

                dateFrom = dateFrom.plusDays(1);
            }

            return new ResponseEntity<>(result, HttpStatus.OK);
        } else if (requestData.getMonthFrom() != null && requestData.getMonthTo() != null) {
            DateTimeFormatter yearMonthFormatter = DateTimeFormatter.ofPattern("yyyy-MM");
            YearMonth yearMonthFrom = YearMonth.parse(requestData.getMonthFrom(), yearMonthFormatter);
            YearMonth yearMonthTo = YearMonth.parse(requestData.getMonthTo(), yearMonthFormatter);
            List<Map<String, Object>> result = new ArrayList<>();
            Map<String, Object> resultItem;

            while (yearMonthFrom.isBefore(yearMonthTo) || yearMonthFrom.equals(yearMonthTo)) {
                LocalDate firstDayOfMonth = yearMonthFrom.atDay(1);
                LocalDate lastDayOfMonth = yearMonthFrom.atEndOfMonth();
                AdminStatisticsCenterProjection queryData = courtBookingRepository.findAdminStatisticsCenterMonthly(requestData.getCenterId(), firstDayOfMonth, lastDayOfMonth);

                resultItem = new HashMap<>();
                resultItem.put("label", yearMonthFrom);
                resultItem.put("courtFee", queryData.getCourtFee());
                resultItem.put("centerFee", queryData.getProductFee());

                result.add(resultItem);

                yearMonthFrom = yearMonthFrom.plusMonths(1);
            }

            return new ResponseEntity<>(result, HttpStatus.OK);
        } else if (requestData.getYearFrom() != null && requestData.getYearTo() != null) {
            YearMonth yearMonthFrom = YearMonth.of(Integer.parseInt(requestData.getYearFrom()), 1);
            YearMonth yearMonthTo = YearMonth.of(Integer.parseInt(requestData.getYearTo()), 1);
            List<Map<String, Object>> result = new ArrayList<>();
            Map<String, Object> resultItem;

            while (yearMonthFrom.isBefore(yearMonthTo) || yearMonthFrom.equals(yearMonthTo)) {
                LocalDate firstDayOfYear = LocalDate.of(yearMonthFrom.getYear(), 1, 1);
                LocalDate lastDayOfYear = LocalDate.of(yearMonthFrom.getYear(), 12, 31);
                AdminStatisticsCenterProjection queryData = courtBookingRepository.findAdminStatisticsCenterMonthly(requestData.getCenterId(), firstDayOfYear, lastDayOfYear);

                resultItem = new HashMap<>();
                resultItem.put("label", yearMonthFrom.getYear());
                resultItem.put("courtFee", queryData.getCourtFee());
                resultItem.put("centerFee", queryData.getProductFee());

                result.add(resultItem);

                yearMonthFrom = yearMonthFrom.plusYears(1);
            }

            return new ResponseEntity<>(result, HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.OK);
    }

    ResponseEntity<Object> getAdminStatisticsToday(String jwtToken) {
        Long userId;
        try {
            userId = JwtUtil.getUserIdFromToken(jwtToken);
        } catch (JWTVerificationException jwtVerificationException) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        LocalDate dateNow = LocalDate.now();
        LocalDateTime localDateTimeFrom = dateNow.atStartOfDay();
        LocalDateTime localDateTimeTo = dateNow.plusDays(1).atStartOfDay();
        AdminStatisticsTodayProjection todayStatistics = courtBookingRepository.findAdminStatisticsToday(localDateTimeFrom, localDateTimeTo);
        return new ResponseEntity<Object>(todayStatistics, HttpStatus.OK);
    }
}
