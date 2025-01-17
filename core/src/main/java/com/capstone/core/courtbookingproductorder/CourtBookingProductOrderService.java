package com.capstone.core.courtbookingproductorder;

import java.time.LocalDate;
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
import com.capstone.core.courtbooking.CourtBookingRepository;
import com.capstone.core.courtbookingproductorder.data.request.AddCourtBookingProductOrderRequestData;
import com.capstone.core.courtbookingproductorder.data.request.CenterOwnerCancelCourtBookingProductOrderRequestData;
import com.capstone.core.courtbookingproductorder.data.request.CenterOwnerCheckoutCourtBookingProductOrderRequestData;
import com.capstone.core.courtbookingproductorder.data.request.CenterOwnerCourtBookingProductOrderListRequestData;
import com.capstone.core.courtbookingproductorder.data.request.CenterOwnerCourtBookingStatisticsRequestData;
import com.capstone.core.courtbookingproductorder.data.request.CenterOwnerProductOrderStatisticsRequestData;
import com.capstone.core.courtbookingproductorder.data.request.UserCancelCourtBookingProductOrderRequestData;
import com.capstone.core.courtbookingproductorder.data.request.UserCourtBookingProductOrderDetailListRequestData;
import com.capstone.core.courtbookingproductorder.data.response.CenterOwnerCourtBookingProductOrderListResponseData;
import com.capstone.core.courtbookingproductorder.data.request.AddCourtBookingProductOrderRequestData.CartItem;
import com.capstone.core.courtbookingproductorder.data.request.AdminCourtBookingStatisticsRequestData;
import com.capstone.core.courtbookingproductorder.data.request.AdminProductOrderStatisticsRequestData;
import com.capstone.core.courtbookingproductorder.data.request.CenterOwnerCourtBookingProductOrderDetailListRequestData;
import com.capstone.core.courtbookingproductorder.projection.AdminCourtBookingStatisticsProjection;
import com.capstone.core.courtbookingproductorder.projection.AdminProductOrderStatisticsProjection;
import com.capstone.core.courtbookingproductorder.projection.AdminProductOrderStatisticsTodayProjection;
import com.capstone.core.courtbookingproductorder.projection.CenterOwnerCourtBookingProductOrderDetailListProjection;
import com.capstone.core.courtbookingproductorder.projection.CenterOwnerCourtBookingProductOrderListProjection;
import com.capstone.core.courtbookingproductorder.projection.CenterOwnerCourtBookingStatisticsProjection;
import com.capstone.core.courtbookingproductorder.projection.CenterOwnerProductOrderStatisticsProjection;
import com.capstone.core.courtbookingproductorder.projection.CenterOwnerProductOrderStatisticsTodayProjection;
import com.capstone.core.courtbookingproductorder.projection.UserCourtBookingProductOrderDetailListProjection;
import com.capstone.core.courtbookingproductorder.specification.CourtBookingProductOrderSpecification;
import com.capstone.core.courtbookingproductorder.specification.criteria.CourtBookingProductOrderCriteria;
import com.capstone.core.courtbookingproductorderdetail.CourtBookingProductOrderDetailRepository;
import com.capstone.core.productinventory.ProductInventoryRepository;
import com.capstone.core.table.CourtBookingProductOrderDetailTable;
import com.capstone.core.table.CourtBookingProductOrderTable;
import com.capstone.core.table.CourtBookingTable;
import com.capstone.core.table.ProductInventoryTable;
import com.capstone.core.table.UserTable;
import com.capstone.core.util.consts.CourtBookingProductOrderStatus;
import com.capstone.core.util.consts.CourtBookingStatus;
import com.capstone.core.util.security.jwt.JwtUtil;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class CourtBookingProductOrderService {
    private CourtBookingProductOrderRepository courtBookingProductOrderRepository;
    private CourtBookingProductOrderDetailRepository courtBookingProductOrderDetailRepository;
    private ProductInventoryRepository productInventoryRepository;
    private CourtBookingRepository courtBookingRepository;

    ResponseEntity<Object> addCourtBookingProductOrder(String jwtToken, AddCourtBookingProductOrderRequestData requestData) {
        Long userId;
        try {
            userId = JwtUtil.getUserIdFromToken(jwtToken);
        } catch (JWTVerificationException jwtVerificationException) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        LocalDateTime localDateTimeNow = LocalDateTime.now();

        List<CourtBookingProductOrderDetailTable> courtBookingProductOrderDetailItems = new ArrayList<>();
        CourtBookingProductOrderDetailTable courtBookingProductOrderDetailItem;

        List<ProductInventoryTable> productInventoryItems = new ArrayList<>();
        ProductInventoryTable productInventoryItem;
        
        UserTable user = new UserTable();
        user.setId(userId);

        CourtBookingTable courtBooking = courtBookingRepository.getReferenceById(requestData.getCourtBookingId());
        courtBooking.setProductFee(courtBooking.getProductFee() + requestData.getTotal());

        CourtBookingProductOrderTable courtBookingProductOrder = new CourtBookingProductOrderTable();
        courtBookingProductOrder.setCourtBooking(courtBooking);
        courtBookingProductOrder.setCreateTimestamp(localDateTimeNow);
        courtBookingProductOrder.setUpdateTimestamp(localDateTimeNow);
        courtBookingProductOrder.setFee(Long.parseLong("0"));
        courtBookingProductOrder.setStatus(CourtBookingStatus.PENDING.getValue());
        courtBookingProductOrder = courtBookingProductOrderRepository.save(courtBookingProductOrder);

        for (CartItem cartItem : requestData.getCart()) {
            productInventoryItem = productInventoryRepository.getReferenceById(cartItem.getProductInventoryId());
            productInventoryItem.setQuantity(productInventoryItem.getQuantity() - cartItem.getQuantity());
            productInventoryItems.add(productInventoryItem);

            courtBookingProductOrderDetailItem = new CourtBookingProductOrderDetailTable();
            courtBookingProductOrderDetailItem.setCourtBookingProductOrder(courtBookingProductOrder);
            courtBookingProductOrderDetailItem.setProductInventory(productInventoryItem);
            courtBookingProductOrderDetailItem.setQuantity(cartItem.getQuantity());
            courtBookingProductOrderDetailItem.setFee(productInventoryItem.getProduct().getPrice() * cartItem.getQuantity());
            courtBookingProductOrderDetailItems.add(courtBookingProductOrderDetailItem);

            courtBookingProductOrder.setFee(courtBookingProductOrder.getFee() + productInventoryItem.getProduct().getPrice() * cartItem.getQuantity());
        }

        courtBookingProductOrderRepository.save(courtBookingProductOrder);
        productInventoryRepository.saveAll(productInventoryItems);
        courtBookingProductOrderDetailRepository.saveAll(courtBookingProductOrderDetailItems);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    ResponseEntity<Object> centerOwnerCancelCourtBookingProductOrder(String jwtToken, CenterOwnerCancelCourtBookingProductOrderRequestData requestData) {
        Long userId;
        try {
            userId = JwtUtil.getUserIdFromToken(jwtToken);
        } catch (JWTVerificationException jwtVerificationException) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        CourtBookingProductOrderTable productOrder = courtBookingProductOrderRepository.getReferenceById(requestData.getId());
        productOrder.setStatus(CourtBookingProductOrderStatus.CANCELED.getValue());
        Long courtBookingProductFee = productOrder.getCourtBooking().getProductFee();
        Long productFee = productOrder.getFee();
        productOrder.getCourtBooking().setProductFee(courtBookingProductFee - productFee);
        courtBookingProductOrderRepository.save(productOrder);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    ResponseEntity<Object> userCancelCourtBookingProductOrder(String jwtToken, UserCancelCourtBookingProductOrderRequestData requestData) {
        Long userId;
        try {
            userId = JwtUtil.getUserIdFromToken(jwtToken);
        } catch (JWTVerificationException jwtVerificationException) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        CourtBookingProductOrderTable productOrder = courtBookingProductOrderRepository.getReferenceById(requestData.getId());
        productOrder.setStatus(CourtBookingProductOrderStatus.CANCELED.getValue());
        Long courtBookingProductFee = productOrder.getCourtBooking().getProductFee();
        Long productFee = productOrder.getFee();
        productOrder.getCourtBooking().setProductFee(courtBookingProductFee - productFee);
        courtBookingProductOrderRepository.save(productOrder);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    ResponseEntity<Object> centerOwnerCheckoutCourtBookingProductOrder(String jwtToken, CenterOwnerCheckoutCourtBookingProductOrderRequestData requestData) {
        Long userId;
        try {
            userId = JwtUtil.getUserIdFromToken(jwtToken);
        } catch (JWTVerificationException jwtVerificationException) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        CourtBookingProductOrderTable productOrder = courtBookingProductOrderRepository.getReferenceById(requestData.getId());
        productOrder.setStatus(CourtBookingProductOrderStatus.PAID.getValue());
        courtBookingProductOrderRepository.save(productOrder);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    ResponseEntity<Object> getUserCourtBookingProductOrderList(String jwtToken, UserCourtBookingProductOrderDetailListRequestData requestData) {
        Long userId;
        try {
            userId = JwtUtil.getUserIdFromToken(jwtToken);
        } catch (JWTVerificationException jwtVerificationException) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        List<UserCourtBookingProductOrderDetailListProjection> productOrderList = courtBookingProductOrderRepository.findUserCourtBookingProductOrderDetailListByCourtBookingIdAndCourtBookingUserIdAndStatusNot(requestData.getCourtBookingId(), userId, CourtBookingProductOrderStatus.CANCELED.getValue());
        return new ResponseEntity<>(productOrderList, HttpStatus.OK);
    }

    ResponseEntity<Object> getCenterOwnerCourtBookingProductOrderDetailList(String jwtToken, CenterOwnerCourtBookingProductOrderDetailListRequestData requestData) {
        Long userId;
        try {
            userId = JwtUtil.getUserIdFromToken(jwtToken);
        } catch (JWTVerificationException jwtVerificationException) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        List<CenterOwnerCourtBookingProductOrderDetailListProjection> productOrderList = courtBookingProductOrderRepository.findCenterOwnerCourtBookingProductOrderDetailListByCourtBookingIdAndStatusNot(requestData.getCourtBookingId(), CourtBookingProductOrderStatus.CANCELED.getValue());
        return new ResponseEntity<>(productOrderList, HttpStatus.OK);
    }

    ResponseEntity<Object> getCenterOwnerCourtBookingProductOrderList(String jwtToken, CenterOwnerCourtBookingProductOrderListRequestData requestData) {
        Long userId;
        try {
            userId = JwtUtil.getUserIdFromToken(jwtToken);
        } catch (JWTVerificationException jwtVerificationException) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        CourtBookingProductOrderCriteria filterCriteria = new CourtBookingProductOrderCriteria();
        filterCriteria.setId(requestData.getId());
        filterCriteria.setCourtBookingId(requestData.getCourtBookingId());
        filterCriteria.setCreateTimestampFrom(requestData.getCreateTimestampFrom());
        filterCriteria.setCreateTimestampTo(requestData.getCreateTimestampTo());
        filterCriteria.setFeeFrom(requestData.getFeeFrom());
        filterCriteria.setFeeTo(requestData.getFeeTo());
        filterCriteria.setStatusId(requestData.getStatusId());

        List<Sort.Order> sortOrders = new ArrayList<>();
        if (requestData.getIdSortOrder() != null) {
            sortOrders.add(new Sort.Order(requestData.getIdSortOrder().equalsIgnoreCase("desc") ? Sort.Direction.DESC : Sort.Direction.ASC, "id"));
        }
        if (requestData.getCourtBookingIdSortOrder() != null) {
            sortOrders.add(new Sort.Order(requestData.getIdSortOrder().equalsIgnoreCase("desc") ? Sort.Direction.DESC : Sort.Direction.ASC, "courtBookingId"));
        }
        if (requestData.getProductInventoryIdSortOrder() != null) {
            sortOrders.add(new Sort.Order(requestData.getIdSortOrder().equalsIgnoreCase("desc") ? Sort.Direction.DESC : Sort.Direction.ASC, "productInventoryId"));
        }
        if (requestData.getUserSortOrder() != null) {
            sortOrders.add(new Sort.Order(requestData.getIdSortOrder().equalsIgnoreCase("desc") ? Sort.Direction.DESC : Sort.Direction.ASC, "userUsername"));
        }
        if (requestData.getCreateTimestampSortOrder() != null) {
            sortOrders.add(new Sort.Order(requestData.getIdSortOrder().equalsIgnoreCase("desc") ? Sort.Direction.DESC : Sort.Direction.ASC, "createTimestamp"));
        }
        if (requestData.getQuantitySortOrder() != null) {
            sortOrders.add(new Sort.Order(requestData.getIdSortOrder().equalsIgnoreCase("desc") ? Sort.Direction.DESC : Sort.Direction.ASC, "quantity"));
        }
        if (requestData.getFeeSortOrder() != null) {
            sortOrders.add(new Sort.Order(requestData.getIdSortOrder().equalsIgnoreCase("desc") ? Sort.Direction.DESC : Sort.Direction.ASC, "fee"));
        }
        if (requestData.getStatusSortOrder() != null) {
            sortOrders.add(new Sort.Order(requestData.getIdSortOrder().equalsIgnoreCase("desc") ? Sort.Direction.DESC : Sort.Direction.ASC, "statusId"));
        }

        Sort sort = Sort.by(sortOrders);

        Pageable pageable = PageRequest.of(requestData.getPageNo(), requestData.getPageSize(), sort);

        Page<CenterOwnerCourtBookingProductOrderListProjection> productOrderList = courtBookingProductOrderRepository.findBy(new CourtBookingProductOrderSpecification(filterCriteria), q -> q.as(CenterOwnerCourtBookingProductOrderListProjection.class).sortBy(pageable.getSort()).page(pageable));
        CenterOwnerCourtBookingProductOrderListResponseData responseData = new CenterOwnerCourtBookingProductOrderListResponseData();
        responseData.setProductOrderList(productOrderList.getContent());
        responseData.setTotalPage(productOrderList.getTotalPages());

        return new ResponseEntity<>(responseData, HttpStatus.OK);
    }

    ResponseEntity<Object> getCenterOwnerProductOrderStatistics(String jwtToken, CenterOwnerProductOrderStatisticsRequestData requestData) {
        Long userId;
        try {
            userId = JwtUtil.getUserIdFromToken(jwtToken);
        } catch (JWTVerificationException jwtVerificationException) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        LocalDateTime localDateTimeFrom = requestData.getDateFrom().atStartOfDay();
        LocalDateTime localDateTimeTo = requestData.getDateTo().plusDays(1).atStartOfDay();
        List<CenterOwnerProductOrderStatisticsProjection> productOrderStatistics = courtBookingProductOrderRepository.findCenterOwnerProductOrderStatistics(userId, localDateTimeFrom, localDateTimeTo, requestData.getCenterId());
        return new ResponseEntity<>(productOrderStatistics, HttpStatus.OK);
    }

    ResponseEntity<Object> getCenterOwnerCourtBookingStatistics(String jwtToken, CenterOwnerCourtBookingStatisticsRequestData requestData) {
        Long userId;
        try {
            userId = JwtUtil.getUserIdFromToken(jwtToken);
        } catch (JWTVerificationException jwtVerificationException) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        LocalDateTime localDateTimeFrom = requestData.getDateFrom().atStartOfDay();
        LocalDateTime localDateTimeTo = requestData.getDateTo().plusDays(1).atStartOfDay();
        List<CenterOwnerCourtBookingStatisticsProjection> courtBookingStatistics = courtBookingProductOrderRepository.findCenterOwnerCourtBookingStatistics(userId, localDateTimeFrom, localDateTimeTo, requestData.getCenterId());
        return new ResponseEntity<>(courtBookingStatistics, HttpStatus.OK);
    }

    ResponseEntity<Object> getCenterOwnerProductOrderStatisticsToday(String jwtToken) {
        Long userId;
        try {
            userId = JwtUtil.getUserIdFromToken(jwtToken);
        } catch (JWTVerificationException jwtVerificationException) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        LocalDate dateNow = LocalDate.now();
        LocalDateTime localDateTimeFrom = dateNow.atStartOfDay();
        LocalDateTime localDateTimeTo = dateNow.plusDays(1).atStartOfDay();
        CenterOwnerProductOrderStatisticsTodayProjection productOrderTodayStatistics = courtBookingProductOrderRepository.findCenterOwnerProductOrderStatisticsToday(userId, localDateTimeFrom, localDateTimeTo);
        return new ResponseEntity<>(productOrderTodayStatistics, HttpStatus.OK);
    }

    ResponseEntity<Object> getAdminProductOrderStatistics(String jwtToken, AdminProductOrderStatisticsRequestData requestData) {
        Long userId;
        try {
            userId = JwtUtil.getUserIdFromToken(jwtToken);
        } catch (JWTVerificationException jwtVerificationException) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        LocalDateTime localDateTimeFrom = requestData.getDateFrom().atStartOfDay();
        LocalDateTime localDateTimeTo = requestData.getDateTo().plusDays(1).atStartOfDay();
        List<AdminProductOrderStatisticsProjection> productOrderStatistics = courtBookingProductOrderRepository.findAdminProductOrderStatistics(localDateTimeFrom, localDateTimeTo, requestData.getCenterId());
        return new ResponseEntity<>(productOrderStatistics, HttpStatus.OK);
    }

    ResponseEntity<Object> getAdminCourtBookingStatistics(String jwtToken, AdminCourtBookingStatisticsRequestData requestData) {
        Long userId;
        try {
            userId = JwtUtil.getUserIdFromToken(jwtToken);
        } catch (JWTVerificationException jwtVerificationException) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        LocalDateTime localDateTimeFrom = requestData.getDateFrom().atStartOfDay();
        LocalDateTime localDateTimeTo = requestData.getDateTo().plusDays(1).atStartOfDay();
        List<AdminCourtBookingStatisticsProjection> courtBookingStatistics = courtBookingProductOrderRepository.findAdminCourtBookingStatistics(localDateTimeFrom, localDateTimeTo, requestData.getCenterId());
        return new ResponseEntity<>(courtBookingStatistics, HttpStatus.OK);
    }

    ResponseEntity<Object> getAdminProductOrderStatisticsToday(String jwtToken) {
        Long userId;
        try {
            userId = JwtUtil.getUserIdFromToken(jwtToken);
        } catch (JWTVerificationException jwtVerificationException) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        LocalDate dateNow = LocalDate.now();
        LocalDateTime localDateTimeFrom = dateNow.atStartOfDay();
        LocalDateTime localDateTimeTo = dateNow.plusDays(1).atStartOfDay();
        AdminProductOrderStatisticsTodayProjection productOrderTodayStatistics = courtBookingProductOrderRepository.findAdminProductOrderStatisticsToday(localDateTimeFrom, localDateTimeTo);
        return new ResponseEntity<>(productOrderTodayStatistics, HttpStatus.OK);
    }
}
