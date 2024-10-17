package com.capstone.core.userrole;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/user-role")
@AllArgsConstructor
public class UserRoleController {

    private UserRoleService userRoleService;
}
