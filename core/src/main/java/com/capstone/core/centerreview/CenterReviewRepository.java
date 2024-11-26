package com.capstone.core.centerreview;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.capstone.core.centerreview.projection.CenterOwnerReviewListProjection;
import com.capstone.core.centerreview.projection.UserCenterReviewListProjection;
import com.capstone.core.table.CenterReviewTable;

@Repository
public interface CenterReviewRepository extends JpaRepository<CenterReviewTable, Long>, JpaSpecificationExecutor<CenterReviewTable> {
    List<UserCenterReviewListProjection> findUserCenterReviewListByUserId(Long userId);
    List<CenterOwnerReviewListProjection> findCenterOwnerReviewListByUserId(Long userId);
}
