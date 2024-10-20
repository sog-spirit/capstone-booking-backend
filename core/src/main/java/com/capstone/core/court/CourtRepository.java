package com.capstone.core.court;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.capstone.core.table.CourtTable;

@Repository
public interface CourtRepository extends JpaRepository<CourtTable, Long> {

}
