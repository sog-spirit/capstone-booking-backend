package com.capstone.core.userrole;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.capstone.core.table.UserRoleTable;
import com.capstone.core.userrole.projection.RoleNameProjection;

@Repository
public interface UserRoleRepository extends JpaRepository<UserRoleTable, Long> {
    public RoleNameProjection findByUserId(Long id);
}
