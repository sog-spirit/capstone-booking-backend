package com.capstone.core.courtbookingproductorderdetail;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.capstone.core.courtbookingproductorderdetail.data.request.CourtBookingProductOrderDetailRequestData;
import com.capstone.core.courtbookingproductorderdetail.projection.CourtBookingProductOrderDetailProjection;
import com.capstone.core.util.security.jwt.JwtUtil;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class CourtBookingProductOrderDetailService {
    private CourtBookingProductOrderDetailRepository courtBookingProductOrderDetailRepository;

    ResponseEntity<Object> getCourtBookingProductOrderDetail(String jwtToken, CourtBookingProductOrderDetailRequestData requestData) {
        Long userId;
        try {
            userId = JwtUtil.getUserIdFromToken(jwtToken);
        } catch (JWTVerificationException jwtVerificationException) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        List<CourtBookingProductOrderDetailProjection> courtBookingProductOrderDetailList = courtBookingProductOrderDetailRepository.findCourtBookingProductOrderDetailByCourtBookingProductOrderId(requestData.getCourtBookingProductOrderId());
        return new ResponseEntity<>(courtBookingProductOrderDetailList, HttpStatus.OK);
    }
}
