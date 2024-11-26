package com.capstone.core.employeelist;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.capstone.core.employeelist.projection.CenterOwnerEmployeeListProjection;
import com.capstone.core.table.EmployeeListTable;

@Repository
public interface EmployeeListRepository extends JpaRepository<EmployeeListTable, Long>, JpaSpecificationExecutor<EmployeeListTable> {
    List<CenterOwnerEmployeeListProjection> findByCenterOwnerId(Long centerOwnerId);
}
