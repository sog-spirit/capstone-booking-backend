package com.capstone.core.employeelist.data.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddNewEmployeeRequestData {
    private String firstName;
    private String lastName;
    private String username;
    private String phone;
    private String email;
    private String password;
}
