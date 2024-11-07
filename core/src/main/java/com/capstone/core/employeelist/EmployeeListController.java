package com.capstone.core.employeelist;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.capstone.core.employeelist.data.AddNewEmployeeRequestData;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/employee-management")
@AllArgsConstructor
public class EmployeeListController {

    private EmployeeListService employeeListService;

    @PostMapping
    ResponseEntity<Object> addNewEmployee(@RequestHeader(name = "Authorization", required = true) String jwtToken,
            @ModelAttribute @Valid AddNewEmployeeRequestData addNewEmployeeRequestData) {
        return employeeListService.addNewEmployee(jwtToken, addNewEmployeeRequestData);
    }

    @GetMapping(value = "/list")
    ResponseEntity<Object> getEmployeeList(@RequestHeader(name = "Authorization", required = true) String jwtToken) {
        return employeeListService.getEmployeeList(jwtToken);
    }
}
