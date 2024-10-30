package com.capstone.core.employeelist;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.capstone.core.table.EmployeeListTable;

@Repository
public interface EmployeeListRepository extends JpaRepository<EmployeeListTable, Long> {

}