package com.capstone.core.role;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/role")
@AllArgsConstructor
public class RoleController {

    private RoleService roleService;

    @GetMapping("/register-list")
    public ResponseEntity<Object> getRegisterRoleList() {
        return roleService.getRegisterRoleList();
    }
}
