package com.capstone.core.center;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.capstone.core.center.data.AddNewCenterRequestData;
import com.capstone.core.table.CenterTable;
import com.capstone.core.table.UserTable;
import com.capstone.core.util.security.jwt.JwtUtil;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class CenterService {

    private CenterRepository centerRepository;

    ResponseEntity<Object> addNewCenter(AddNewCenterRequestData requestData) {
        Long userId = JwtUtil.getUserIdFromToken(requestData.getAccessToken());
        CenterTable newCenter = new CenterTable();
        newCenter.setName(requestData.getName());
        newCenter.setAddress(requestData.getAddress());
        UserTable user = new UserTable();
        user.setId(userId);
        newCenter.setUser(user);
        centerRepository.save(newCenter);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
