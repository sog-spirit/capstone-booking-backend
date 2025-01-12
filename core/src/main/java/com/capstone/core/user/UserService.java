package com.capstone.core.user;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.springframework.context.MessageSource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.capstone.core.role.RoleRepository;
import com.capstone.core.table.RoleTable;
import com.capstone.core.table.UserRoleTable;
import com.capstone.core.table.UserTable;
import com.capstone.core.user.data.AdminUserListRequestData;
import com.capstone.core.user.data.AdminUserListResponseData;
import com.capstone.core.user.data.DuplicateUsernamePasswordResponseData;
import com.capstone.core.user.data.EditUserInfoRequestData;
import com.capstone.core.user.data.EditUserPasswordRequestData;
import com.capstone.core.user.data.InvalidRoleIdResponseData;
import com.capstone.core.user.data.JwtTokenResponseData;
import com.capstone.core.user.data.LoginFormRequestData;
import com.capstone.core.user.data.RegisterFormRequestData;
import com.capstone.core.user.projection.AdminUserListProjection;
import com.capstone.core.user.projection.UserInfoProjection;
import com.capstone.core.user.projection.ValidateUserProjection;
import com.capstone.core.user.specification.UserSpecification;
import com.capstone.core.user.specification.criteria.UserCriteria;
import com.capstone.core.userrole.UserRoleRepository;
import com.capstone.core.userrole.projection.UserRoleProjection;
import com.capstone.core.util.consts.MessageConstsUtil;
import com.capstone.core.util.consts.UserRole;
import com.capstone.core.util.security.jwt.JwtUtil;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class UserService {

    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private UserRoleRepository userRoleRepository;
    private PasswordEncoder passwordEncoder;
    private MessageSource messageSource;

    public ResponseEntity<Object> registerUser(RegisterFormRequestData registerFormData, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return new ResponseEntity<>(MessageConstsUtil.createValidationResultMessageMap(bindingResult), HttpStatus.BAD_REQUEST);
        }

        if (isDuplicateUsername(registerFormData.getUsername())
                || isDuplicateEmail(registerFormData.getEmail())) {
            String errorMessage = messageSource.getMessage("error.register.duplicate", null, Locale.getDefault());
            DuplicateUsernamePasswordResponseData responseData = new DuplicateUsernamePasswordResponseData();
            responseData.setUsername(errorMessage);
            responseData.setPassword(errorMessage);
            return new ResponseEntity<>(responseData, HttpStatus.CONFLICT);
        }

        if (!roleRepository.existsRoleById(registerFormData.getRole()) && roleRepository.existsRoleByIdNot(UserRole.ADMIN.getValue())) {
            InvalidRoleIdResponseData responseData = new InvalidRoleIdResponseData();
            String errorMessage = messageSource.getMessage("error.role.invalid", null, Locale.getDefault());
            responseData.setRole(errorMessage);
            return new ResponseEntity<>(responseData, HttpStatus.CONFLICT);
        }

        UserTable user = new UserTable();
        saveNewRegisterRecord(registerFormData, user);
        saveNewUserRoleRecord(registerFormData, user);

        String successMessage = messageSource.getMessage("info.register.success", null, Locale.getDefault());
        return new ResponseEntity<>(successMessage, HttpStatus.OK);
    }

    public ResponseEntity<Object> loginUser(LoginFormRequestData loginFormData, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return new ResponseEntity<>(MessageConstsUtil.createValidationResultMessageMap(bindingResult), HttpStatus.BAD_REQUEST);
        }

        String invalidLoginMessage = messageSource.getMessage("error.login", null, Locale.getDefault());

        if (!validateUser(loginFormData)) {
            return new ResponseEntity<>(new LoginFormRequestData(invalidLoginMessage, invalidLoginMessage), HttpStatus.UNAUTHORIZED);
        }

        ValidateUserProjection userData = userRepository.findByUsername(loginFormData.getUsername());
        String accessToken = JwtUtil.createAccessToken(userData.getId());
        String refreshToken = JwtUtil.createRefreshToken(userData.getId());
        UserRoleProjection userRole = userRoleRepository.findByUserId(userData.getId());
        JwtTokenResponseData responseData = new JwtTokenResponseData();
        responseData.setAccessToken(accessToken);
        responseData.setRefreshToken(refreshToken);
        responseData.setRole(userRole.getRoleName());
        responseData.setUsername(loginFormData.getUsername());

        return new ResponseEntity<>(responseData, HttpStatus.OK);
    }

    ResponseEntity<Object> getUserInfo(String jwtToken) {
        Long userId;
        try {
            userId = JwtUtil.getUserIdFromToken(jwtToken);
        } catch (JWTVerificationException jwtVerificationException) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        UserInfoProjection data = userRepository.findUserInfoById(userId);

        return new ResponseEntity<>(data, HttpStatus.OK);
    }

    ResponseEntity<Object> editUserInfo(String jwtToken, EditUserInfoRequestData requestData) {
        Long userId;
        try {
            userId = JwtUtil.getUserIdFromToken(jwtToken);
        } catch (JWTVerificationException jwtVerificationException) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        UserTable user = userRepository.getReferenceById(userId);
        user.setFirstName(requestData.getFirstName());
        user.setLastName(requestData.getLastName());
        userRepository.save(user);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    ResponseEntity<Object> editUserPassword(String jwtToken, EditUserPasswordRequestData requestData) {
        Long userId;
        try {
            userId = JwtUtil.getUserIdFromToken(jwtToken);
        } catch (JWTVerificationException jwtVerificationException) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        UserTable user = userRepository.getReferenceById(userId);
        user.setPassword(passwordEncoder.encode(requestData.getNewPassword()));
        userRepository.save(user);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    ResponseEntity<Object> getAdminUserList(String jwtToken, AdminUserListRequestData requestData) {
        Long userId;
        try {
            userId = JwtUtil.getUserIdFromToken(jwtToken);
        } catch (JWTVerificationException jwtVerificationException) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        UserCriteria filterCriteria = new UserCriteria();
        filterCriteria.setUsernameFilter(requestData.getUsernameFilter());
        filterCriteria.setPhoneFilter(requestData.getPhoneFilter());
        filterCriteria.setEmailFilter(requestData.getEmailFilter());
        filterCriteria.setFirstNameFilter(requestData.getFirstNameFilter());
        filterCriteria.setLastNameFilter(requestData.getLastNameFilter());

        List<Sort.Order> sortOrders = new ArrayList<>();
        if (requestData.getUsernameSortOrder() != null) {
            sortOrders.add(new Sort.Order(requestData.getUsernameSortOrder().equalsIgnoreCase("desc") ? Sort.Direction.DESC : Sort.Direction.ASC, "username"));
        }
        if (requestData.getEmailSortOrder() != null) {
            sortOrders.add(new Sort.Order(requestData.getEmailSortOrder().equalsIgnoreCase("desc") ? Sort.Direction.DESC : Sort.Direction.ASC, "email"));
        }
        if (requestData.getPhoneSortOrder() != null) {
            sortOrders.add(new Sort.Order(requestData.getUsernameSortOrder().equalsIgnoreCase("desc") ? Sort.Direction.DESC : Sort.Direction.ASC, "phone"));
        }
        if (requestData.getFirstNameSortOrder() != null) {
            sortOrders.add(new Sort.Order(requestData.getFirstNameSortOrder().equalsIgnoreCase("desc") ? Sort.Direction.DESC : Sort.Direction.ASC, "firstName"));
        }
        if (requestData.getLastNameSortOrder() != null) {
            sortOrders.add(new Sort.Order(requestData.getLastNameSortOrder().equalsIgnoreCase("desc") ? Sort.Direction.DESC : Sort.Direction.ASC, "lastName"));
        }

        Sort sort = Sort.by(sortOrders);

        Pageable pageable = PageRequest.of(requestData.getPageNo(), requestData.getPageSize(), sort);

        AdminUserListResponseData responseData = new AdminUserListResponseData();
        Page<AdminUserListProjection> userList = userRepository.findBy(new UserSpecification(filterCriteria), q -> q.as(AdminUserListProjection.class).sortBy(pageable.getSort()).page(pageable));
        responseData.setUserList(userList.getContent());
        responseData.setTotalPage(userList.getTotalPages());

        return new ResponseEntity<>(responseData, HttpStatus.OK);
    }

    private boolean isDuplicateUsername(String username) {
        return userRepository.existsUserByUsername(username);
    }

    private boolean isDuplicateEmail(String email) {
        return userRepository.existsUserByEmail(email);
    }

    private void saveNewRegisterRecord(RegisterFormRequestData registerFormData, UserTable user) {
        LocalDateTime currentTime = LocalDateTime.now();
        user.setUsername(registerFormData.getUsername());
        user.setPassword(passwordEncoder.encode(registerFormData.getPassword()));
        user.setFirstName(registerFormData.getFirstName());
        user.setLastName(registerFormData.getLastName());
        user.setPhone(registerFormData.getPhone());
        user.setEmail(registerFormData.getEmail());
        user.setCreateTimestamp(currentTime);
        user.setUpdateTimestamp(currentTime);
        userRepository.save(user);
    }

    private void saveNewUserRoleRecord(RegisterFormRequestData registerFormData, UserTable user) {
        UserRoleTable userRole = new UserRoleTable();
        RoleTable role = new RoleTable();
        role.setId(registerFormData.getRole());
        userRole.setRole(role);
        userRole.setUser(user);
        userRoleRepository.save(userRole);
    }

    private boolean validateUser(LoginFormRequestData loginFormData) {
        if (!userRepository.existsUserByUsername(loginFormData.getUsername())) {
            return false;
        }

        ValidateUserProjection userData = userRepository.findByUsername(loginFormData.getUsername());
        if (!passwordEncoder.matches(loginFormData.getPassword(), userData.getPassword())) {
            return false;
        }

        return true;
    }
}
