package com.capstone.core.employeelist.data.response;

import java.util.List;

import com.capstone.core.employeelist.projection.CenterOwnerEmployeeListProjection;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CenterOwnerEmployeeListResponseData {
    private Integer totalPage;
    private List<CenterOwnerEmployeeListProjection> employeeList;
}
