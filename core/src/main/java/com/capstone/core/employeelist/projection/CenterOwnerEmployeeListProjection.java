package com.capstone.core.employeelist.projection;

public interface CenterOwnerEmployeeListProjection {
    Long getId();
    Employee getEmployee();

    interface Employee {
        String getUsername();
        String getFirstName();
        String getLastName();
        String getPhone();
        String getEmail();
    }
}
