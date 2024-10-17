package com.capstone.core.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.capstone.core.table.UserTable;
import com.capstone.core.user.projection.ValidateUserProjection;

@Repository
public interface UserRepository extends JpaRepository<UserTable, Long> {
    boolean existsUserByUsername(String username);
    boolean existsUserByEmail(String email);
    ValidateUserProjection findByUsername(String username);
}
