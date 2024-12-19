package com.capstone.core.employeelist.specification.criteria;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeListCriteria {
    private Long centerOwnerId;
    private String username;
    private String firstName;
    private String lastName;
    private String phone;
    private String email;
}
