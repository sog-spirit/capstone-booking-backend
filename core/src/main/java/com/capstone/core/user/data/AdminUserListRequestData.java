package com.capstone.core.user.data;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdminUserListRequestData {
    private String usernameFilter;
    private String emailFilter;
    private String phoneFilter;
    private String firstNameFilter;
    private String lastNameFilter;
    private String usernameSortOrder;
    private String emailSortOrder;
    private String phoneSortOrder;
    private String firstNameSortOrder;
    private String lastNameSortOrder;
    private Integer pageNo;
    private Integer pageSize;
}
