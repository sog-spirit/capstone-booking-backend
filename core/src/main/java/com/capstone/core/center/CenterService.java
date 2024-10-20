package com.capstone.core.center;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.capstone.core.center.data.AddNewCenterRequestData;
import com.capstone.core.center.data.CenterListResponseData;
import com.capstone.core.center.data.EditCenterRequestData;
import com.capstone.core.center.projection.CenterListProjection;
import com.capstone.core.table.CenterTable;
import com.capstone.core.table.UserTable;
import com.capstone.core.util.security.jwt.JwtUtil;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class CenterService {

    private CenterRepository centerRepository;

    ResponseEntity<Object> addNewCenter(String jwtToken, AddNewCenterRequestData requestData) {
        Long userId = JwtUtil.getUserIdFromToken(jwtToken);
        CenterTable newCenter = new CenterTable();
        newCenter.setName(requestData.getName());
        newCenter.setAddress(requestData.getAddress());
        UserTable user = new UserTable();
        user.setId(userId);
        newCenter.setUser(user);
        newCenter.setFieldQuantity(Long.parseLong("0"));
        centerRepository.save(newCenter);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    ResponseEntity<Object> editCenter(String jwtToken, EditCenterRequestData editCenterRequestData) {
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

    ResponseEntity<Object> getCenterList(String jwtToken, Integer pageNo, Integer pageSize) {
        Long userId = JwtUtil.getUserIdFromToken(jwtToken);
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        Page<CenterListProjection> centerPagedResult = centerRepository.findByUserId(userId, pageable);
        CenterListResponseData responseData = new CenterListResponseData();
        responseData.setTotalPage(centerPagedResult.getTotalPages());
        responseData.setCenterList(centerPagedResult.getContent());
        return new ResponseEntity<>(responseData, HttpStatus.OK);
    }
}
