package com.capstone.core.employeelist;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.capstone.core.employeelist.data.AddNewEmployeeRequestData;
import com.capstone.core.employeelist.projection.EmployeeListProjection;
import com.capstone.core.table.EmployeeListTable;
import com.capstone.core.table.RoleTable;
import com.capstone.core.table.UserRoleTable;
import com.capstone.core.table.UserTable;
import com.capstone.core.user.UserRepository;
import com.capstone.core.userrole.UserRoleRepository;
import com.capstone.core.util.consts.UserRole;
import com.capstone.core.util.security.jwt.JwtUtil;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class EmployeeListService {

    private EmployeeListRepository employeeListRepository;
    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;
    private UserRoleRepository userRoleRepository;

    @Transactional
    ResponseEntity<Object> addNewEmployee(String jwtToken, AddNewEmployeeRequestData addNewEmployeeRequestData) {
        Long userId;
        try {
            userId = JwtUtil.getUserIdFromToken(jwtToken);
        } catch (JWTVerificationException jwtVerificationException) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        if (userRepository.existsUserByEmail(addNewEmployeeRequestData.getEmail())
                || userRepository.existsUserByUsername(addNewEmployeeRequestData.getUsername())) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }

        UserTable addUser = new UserTable();
        LocalDateTime currentDateTime = LocalDateTime.now();
        addUser.setUsername(addNewEmployeeRequestData.getUsername());
        addUser.setPassword(passwordEncoder.encode(addNewEmployeeRequestData.getPassword()));
        addUser.setFirstName(addNewEmployeeRequestData.getFirstName());
        addUser.setLastName(addNewEmployeeRequestData.getLastName());
        addUser.setPhone(addNewEmployeeRequestData.getPhone());
        addUser.setEmail(addNewEmployeeRequestData.getEmail());
        addUser.setCreateTimestamp(currentDateTime);
        addUser.setUpdateTimestamp(currentDateTime);
        UserTable savedUser = userRepository.save(addUser);

        RoleTable role = new RoleTable();
        role.setId(UserRole.EMPLOYEE.getValue());
        
        UserRoleTable userRole = new UserRoleTable();
        userRole.setRole(role);
        userRole.setUser(savedUser);
        userRoleRepository.save(userRole);

        UserTable owner = new UserTable();
        owner.setId(userId);
        EmployeeListTable employeeList = new EmployeeListTable();
        employeeList.setEmployee(savedUser);
        employeeList.setCenterOwner(owner);
        employeeListRepository.save(employeeList);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    ResponseEntity<Object> getEmployeeList(String jwtToken) {
        Long userId;
        try {
            userId = JwtUtil.getUserIdFromToken(jwtToken);
        } catch (JWTVerificationException jwtVerificationException) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        List<EmployeeListProjection> employeeList = employeeListRepository.findByCenterOwnerId(userId);
        return new ResponseEntity<>(employeeList, HttpStatus.OK);
    }
}
