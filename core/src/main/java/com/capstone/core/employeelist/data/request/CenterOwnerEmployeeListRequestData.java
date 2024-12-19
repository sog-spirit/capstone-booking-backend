package com.capstone.core.employeelist.data.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CenterOwnerEmployeeListRequestData {
    private String usernameSortOrder;
    private String firstNameSortOrder;
    private String lastNameSortOrder;
    private String phoneSortOrder;
    private String emailSortOrder;

    private String username;
    private String firstName;
    private String lastName;
    private String phone;
    private String email;

    private Integer pageNo;
    private Integer pageSize;
}
