package com.capstone.core.user.specification.criteria;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserCriteria {
    private String usernameFilter;
    private String emailFilter;
    private String phoneFilter;
    private String firstNameFilter;
    private String lastNameFilter;
}
