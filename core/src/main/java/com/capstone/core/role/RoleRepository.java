package com.capstone.core.role;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.capstone.core.role.projection.RoleListProjection;
import com.capstone.core.table.RoleTable;

@Repository
public interface RoleRepository extends JpaRepository<RoleTable, Long> {

    List<RoleListProjection> findByIdNot(Long id);
    public boolean existsRoleByIdNot(Long id);
    public boolean existsRoleById(Long id);
}
