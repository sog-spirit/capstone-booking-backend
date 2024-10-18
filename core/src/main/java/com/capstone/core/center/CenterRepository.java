package com.capstone.core.center;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.capstone.core.table.CenterTable;

@Repository
public interface CenterRepository extends JpaRepository<CenterTable, Long> {

}
