package com.capstone.core.center;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.capstone.core.center.projection.CenterListDropdownProjection;
import com.capstone.core.center.projection.CenterListProjection;
import com.capstone.core.table.CenterTable;

@Repository
public interface CenterRepository extends JpaRepository<CenterTable, Long> {
    Page<CenterListProjection> findByUserId(Long id, Pageable pageable);
    Page<CenterListProjection> findBy(Pageable pageable);
    List<CenterListDropdownProjection> findByUserId(Long id);
    List<CenterListDropdownProjection> findByNameContainingAndUserId(String name, Long id);
}
