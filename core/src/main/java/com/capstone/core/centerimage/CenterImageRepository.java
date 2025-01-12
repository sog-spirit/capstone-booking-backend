package com.capstone.core.centerimage;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.capstone.core.centerimage.projection.CenterImageInfoListProjection;
import com.capstone.core.centerimage.projection.CenterShowcaseInfoProjection;
import com.capstone.core.centerimage.projection.CenterThumbnailImageProjection;
import com.capstone.core.table.CenterImageTable;

@Repository
public interface CenterImageRepository extends JpaRepository<CenterImageTable, Long> {
    List<CenterImageInfoListProjection> findCenterImageInfoListByCenterId(Long centerId);
    CenterThumbnailImageProjection findCenterThumbnailImageByCenterIdAndType(Long centerId, Long type);
    List<CenterShowcaseInfoProjection> findCenterShowcaseInfoByCenterIdAndType(Long centerId, Long type);
}
