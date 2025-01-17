package com.capstone.core.center;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.capstone.core.center.projection.CenterOwnerCenterListDropdownProjection;
import com.capstone.core.center.projection.CenterOwnerStatisticsCenterListProjection;
import com.capstone.core.center.projection.CenterOwnerCenterInfoProjection;
import com.capstone.core.center.projection.AdminStatisticsCenterListProjection;
import com.capstone.core.center.projection.CenterListProjection;
import com.capstone.core.center.projection.UserCenterListProjection;
import com.capstone.core.center.projection.UserCenterInfoProjection;
import com.capstone.core.table.CenterTable;

@Repository
public interface CenterRepository extends JpaRepository<CenterTable, Long>, JpaSpecificationExecutor<CenterTable> {
    Page<CenterListProjection> findByUserId(Long id, Pageable pageable);
    Page<UserCenterListProjection> findUserCenterListBy(Pageable pageable);
    @Query(value = "select\r\n"
            + "    c.id,\r\n"
            + "    c.name\r\n"
            + "from\r\n"
            + "    center c\r\n"
            + "inner join\r\n"
            + "    product_inventory pi2\r\n"
            + "on\r\n"
            + "    c.id = pi2.center_id\r\n"
            + "where\r\n"
            + "    c.\"owner\" = :id\r\n"
            + "    and pi2.product_id <> :productId\r\n"
            + "    and c.name like concat('%', :name, '%')", nativeQuery = true)
    List<CenterOwnerCenterListDropdownProjection> findCenterOwnerCenterListDropdown(String name, Long id, Long productId);
    CenterOwnerCenterInfoProjection findCenterOwnerCenterInfoById(Long centerId);
    UserCenterInfoProjection findUserCenterInfoById(Long centerId);
    List<CenterOwnerStatisticsCenterListProjection> findCenterOwnerStatisticsCenterListByUserIdAndNameContaining(Long userId, String name);
    List<AdminStatisticsCenterListProjection> findAdminStatisticsCenterListByNameContaining(String name);
}
