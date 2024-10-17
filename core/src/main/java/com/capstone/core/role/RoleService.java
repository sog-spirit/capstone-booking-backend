package com.capstone.core.role;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.capstone.core.role.projection.RoleListProjection;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class RoleService {

    private RoleRepository roleRepository;

    public ResponseEntity<Object> getRegisterRoleList() {
        List<RoleListProjection> roleList = roleRepository.findByIdNot(Long.parseLong("1"));
        return new ResponseEntity<>(roleList, HttpStatus.OK);
    }
}
