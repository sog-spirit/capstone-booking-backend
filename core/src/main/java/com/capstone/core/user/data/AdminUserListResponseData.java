package com.capstone.core.user.data;

import java.util.List;

import com.capstone.core.user.projection.AdminUserListProjection;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AdminUserListResponseData {
    private Integer totalPage;
    private List<AdminUserListProjection> userList;
}
