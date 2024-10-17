package com.capstone.core.blacklistjwttoken;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.capstone.core.table.BlacklistJWTTokenTable;

@Repository
public interface BlacklistJWTTokenRepository extends JpaRepository<BlacklistJWTTokenTable, Long> {
    boolean existsBlacklistJWTTokenByJwtToken(String jwtToken);
}
