package com.capstone.core.user;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.capstone.core.user.data.AdminUserListRequestData;
import com.capstone.core.user.data.EditUserInfoRequestData;
import com.capstone.core.user.data.EditUserPasswordRequestData;
import com.capstone.core.user.data.LoginFormRequestData;
import com.capstone.core.user.data.RegisterFormRequestData;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/user")
@AllArgsConstructor
public class UserController {

    private UserService userService;

    @PostMapping(value = "/register")
    @Transactional
    ResponseEntity<Object> registerUser(@RequestBody @Valid RegisterFormRequestData registerFormData, BindingResult bindingResult) {
        return userService.registerUser(registerFormData, bindingResult);
    }

    @PostMapping(value =  "/login")
    ResponseEntity<Object> loginUser(@RequestBody @Valid LoginFormRequestData loginFormData, BindingResult bindingResult) {
        return userService.loginUser(loginFormData, bindingResult);
    }

    @GetMapping(value = "/info")
    ResponseEntity<Object> getUserInfo(@RequestHeader(name = "Authorization", required = true) String jwtToken) {
        return userService.getUserInfo(jwtToken);
    }

    @PutMapping(value = "/info")
    @Transactional
    ResponseEntity<Object> editUserInfo(@RequestHeader(name = "Authorization", required = true) String jwtToken,
            @RequestBody EditUserInfoRequestData requestData) {
        return userService.editUserInfo(jwtToken, requestData);
    }

    @PutMapping(value = "/password")
    @Transactional
    ResponseEntity<Object> editUserPassword(@RequestHeader(name = "Authorization", required = true) String jwtToken,
            @RequestBody EditUserPasswordRequestData requestData) {
        return userService.editUserPassword(jwtToken, requestData);
    }

    @GetMapping(value = "/admin/list")
    ResponseEntity<Object> getAdminUserList(@RequestHeader(name = "Authorization", required = true) String jwtToken,
            AdminUserListRequestData requestData) {
        return userService.getAdminUserList(jwtToken, requestData);
    }
}
