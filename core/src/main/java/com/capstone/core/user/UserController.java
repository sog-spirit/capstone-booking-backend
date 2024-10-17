package com.capstone.core.user;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    @PostMapping("/register")
    @Transactional
    ResponseEntity<Object> registerUser(@RequestBody @Valid RegisterFormRequestData registerFormData, BindingResult bindingResult) {
        return userService.registerUser(registerFormData, bindingResult);
    }

    @PostMapping("/login")
    ResponseEntity<Object> loginUser(@RequestBody @Valid LoginFormRequestData loginFormData, BindingResult bindingResult) {
        return userService.loginUser(loginFormData, bindingResult);
    }
}
