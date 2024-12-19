package com.capstone.core.employeelist;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.capstone.core.employeelist.data.request.AddNewEmployeeRequestData;
import com.capstone.core.employeelist.data.request.CenterOwnerEmployeeListRequestData;
import com.capstone.core.employeelist.data.response.CenterOwnerEmployeeListResponseData;
import com.capstone.core.employeelist.projection.CenterOwnerEmployeeListProjection;
import com.capstone.core.employeelist.specification.EmployeeListSpecification;
import com.capstone.core.employeelist.specification.criteria.EmployeeListCriteria;
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

    ResponseEntity<Object> getCenterOwnerEmployeeList(String jwtToken, CenterOwnerEmployeeListRequestData requestData) {
        Long userId;
        try {
            userId = JwtUtil.getUserIdFromToken(jwtToken);
        } catch (JWTVerificationException jwtVerificationException) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        EmployeeListCriteria employeeListCriteria = new EmployeeListCriteria();
        employeeListCriteria.setCenterOwnerId(userId);
        employeeListCriteria.setUsername(requestData.getUsername());
        employeeListCriteria.setFirstName(requestData.getFirstName());
        employeeListCriteria.setLastName(requestData.getLastName());
        employeeListCriteria.setPhone(requestData.getPhone());
        employeeListCriteria.setEmail(requestData.getEmail());

        List<Sort.Order> sortOrders = new ArrayList<>();
        if (requestData.getUsernameSortOrder() != null) {
            sortOrders.add(new Sort.Order(requestData.getUsernameSortOrder().equalsIgnoreCase("desc") ? Sort.Direction.DESC : Sort.Direction.ASC, "employeeUsername"));
        }
        if (requestData.getFirstNameSortOrder() != null) {
            sortOrders.add(new Sort.Order(requestData.getFirstNameSortOrder().equalsIgnoreCase("desc") ? Sort.Direction.DESC : Sort.Direction.ASC, "employeeFirstName"));
        }
        if (requestData.getLastNameSortOrder() != null) {
            sortOrders.add(new Sort.Order(requestData.getLastNameSortOrder().equalsIgnoreCase("desc") ? Sort.Direction.DESC : Sort.Direction.ASC, "employeeLastName"));
        }
        if (requestData.getPhoneSortOrder() != null) {
            sortOrders.add(new Sort.Order(requestData.getPhoneSortOrder().equalsIgnoreCase("desc") ? Sort.Direction.DESC : Sort.Direction.ASC, "employeePhone"));
        }
        if (requestData.getEmailSortOrder() != null) {
            sortOrders.add(new Sort.Order(requestData.getEmailSortOrder().equalsIgnoreCase("desc") ? Sort.Direction.DESC : Sort.Direction.ASC, "employeeEmail"));
        }

        Sort sort = Sort.by(sortOrders);

        Pageable pageable = PageRequest.of(requestData.getPageNo(), requestData.getPageSize(), sort);

        Page<CenterOwnerEmployeeListProjection> employeeList = employeeListRepository.findBy(new EmployeeListSpecification(employeeListCriteria), q -> q.as(CenterOwnerEmployeeListProjection.class).sortBy(pageable.getSort()).page(pageable));

        CenterOwnerEmployeeListResponseData responseData = new CenterOwnerEmployeeListResponseData();
        responseData.setTotalPage(employeeList.getTotalPages());
        responseData.setEmployeeList(employeeList.getContent());

        return new ResponseEntity<>(responseData, HttpStatus.OK);
    }
}
