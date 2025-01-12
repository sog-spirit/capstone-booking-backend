package com.capstone.core.centerreview;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import com.capstone.core.centerreview.projection.CenterOwnerCenterReviewCenterFilterListProjection;
import com.capstone.core.centerreview.projection.CenterOwnerCenterReviewUserFilterListProjection;
import com.capstone.core.centerreview.projection.CenterOwnerReviewListProjection;
import com.capstone.core.centerreview.projection.UserCenterCenterReviewListProjection;
import com.capstone.core.centerreview.projection.UserCenterReviewCenterFilterListProjection;
import com.capstone.core.centerreview.projection.UserCenterReviewListProjection;
import com.capstone.core.table.CenterReviewTable;

@Repository
public interface CenterReviewRepository extends JpaRepository<CenterReviewTable, Long>, JpaSpecificationExecutor<CenterReviewTable> {
    List<UserCenterReviewListProjection> findUserCenterReviewListByUserId(Long userId);
    List<CenterOwnerReviewListProjection> findCenterOwnerReviewListByUserId(Long userId);
    List<CenterOwnerCenterReviewUserFilterListProjection> findCenterOwnerCenterReviewUserFilterListDistinctUserIdByCenterUserIdAndUserUsernameContaining(Long ownerId, String username);
    List<CenterOwnerCenterReviewCenterFilterListProjection> findCenterOwnerCenterReviewCenterFilterListDistinctCenterIdByCenterUserIdAndCenterNameContaining(Long ownerId, String name);
    List<UserCenterReviewCenterFilterListProjection> findUserCenterReviewCenterFilterListDistinctCenterIdByUserIdAndCenterNameContaining(Long userId, String name);
    List<UserCenterCenterReviewListProjection> findUserCenterCenterReviewListByCenterId(Long centerId);
}
