package com.capstone.core.user;

import java.time.LocalDateTime;
import java.util.Locale;

import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import com.capstone.core.role.RoleRepository;
import com.capstone.core.table.RoleTable;
import com.capstone.core.table.UserRoleTable;
import com.capstone.core.table.UserTable;
import com.capstone.core.user.data.DuplicateUsernamePasswordResponseData;
import com.capstone.core.user.data.InvalidRoleIdResponseData;
import com.capstone.core.user.data.JwtTokenResponseData;
import com.capstone.core.user.data.LoginFormRequestData;
import com.capstone.core.user.data.RegisterFormRequestData;
import com.capstone.core.user.projection.ValidateUserProjection;
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
