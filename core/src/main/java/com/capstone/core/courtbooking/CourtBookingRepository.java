package com.capstone.core.courtbooking;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.capstone.core.courtbooking.projection.CenterListProjection;
import com.capstone.core.courtbooking.projection.CenterOwnerCourtBookingCenterListProjection;
import com.capstone.core.courtbooking.projection.CenterOwnerCourtBookingCourtListProjection;
import com.capstone.core.courtbooking.projection.CenterOwnerCourtBookingDetailProjection;
import com.capstone.core.courtbooking.projection.CenterOwnerCourtBookingUserListProjection;
import com.capstone.core.courtbooking.projection.CenterOwnerCourtCourtBookingListProjection;
import com.capstone.core.courtbooking.projection.CenterOwnerStatisticsCenterProjection;
import com.capstone.core.courtbooking.projection.CourtBookingListProjection;
import com.capstone.core.courtbooking.projection.UserCourtBookingCenterIdProjection;
import com.capstone.core.courtbooking.projection.UserCourtBookingCenterListProjection;
import com.capstone.core.courtbooking.projection.UserCourtBookingCourtListProjection;
import com.capstone.core.courtbooking.projection.UserCourtBookingDetailProjection;
import com.capstone.core.courtbooking.projection.UserCourtBookingListProjection;
import com.capstone.core.courtbooking.projection.UserCourtCourtBookingListProjection;
import com.capstone.core.table.CourtBookingTable;

@Repository
public interface CourtBookingRepository extends JpaRepository<CourtBookingTable, Long>, JpaSpecificationExecutor<CourtBookingTable> {
    List<CourtBookingListProjection> findByCourtCenterIdAndCourtId(Long courtCenterId, Long courtId);
    List<UserCourtBookingListProjection> findByUserId(Long userId);
    @Query(value = "select distinct c.id, c.\"name\"\r\n"
            + "from center c inner join court c2 on c2.center_id = c.id\r\n"
            + "inner join court_booking cb on cb.court_id = c2.id\r\n"
            + "where cb.user_id = :userId and c.\"name\" like concat('%', :query, '%') and c.id not in (\r\n"
            + "    select distinct cr.center_id from center_review cr\r\n"
            + "    where cr.user_id = :userId and cr.status <> 3 and cr.status <> 4\r\n"
            + ")", nativeQuery = true)
    List<CenterListProjection> findNotReviewedCenterList(Long userId, String query);
    List<CenterOwnerCourtCourtBookingListProjection> findCenterOwnerCourtCourtBookingListByCourtIdAndUsageDateAndStatusNotOrderByUsageTimeStartAscUsageTimeEndAsc(Long courtId, LocalDate usageDate, Long status);
    List<UserCourtCourtBookingListProjection> findUserCourtCourtBookingListByUserIdAndCourtIdAndUsageDateAndStatusNotOrderByUsageTimeStartAscUsageTimeEndAsc(Long userId, Long courtId, LocalDate usageDate, Long status);
    List<CenterOwnerCourtBookingCenterListProjection> findCenterOwnerCourtBookingCenterListDistinctCourtCenterIdByCourtCenterUserIdAndCourtCenterNameContaining(Long centerOwnerId, String centerName);
    List<CenterOwnerCourtBookingCourtListProjection> findCenterOwnerCourtBookingCourtListDistinctCourtIdByCourtCenterUserIdAndCourtNameContaining(Long centerOwnerId, String courtName);
    List<CenterOwnerCourtBookingUserListProjection> findCenterOwnerCourtBookingUserListDistinctUserIdByCourtCenterUserIdAndUserUsernameContaining(Long centerOwnerId, String username);
    List<UserCourtBookingCenterListProjection> findUserCourtBookingCenterListDistinctCourtCenterIdByUserIdAndCourtCenterNameContaining(Long userId, String centerName);
    List<UserCourtBookingCourtListProjection> findUserCourtBookingCourtListDistinctCourtIdByUserIdAndCourtNameContaining(Long userId, String courtName);
    UserCourtBookingDetailProjection findUserCourtBookingDetailById(Long courtBookingId);
    CenterOwnerCourtBookingDetailProjection findCenterOwnerCourtBookingDetailById(Long courtBookingId);
    UserCourtBookingCenterIdProjection findUserCourtBookingCenterIdById(Long courtBookingId);
    @Query(value = "select\r\n"
            + "    coalesce(sum(cb.court_fee), 0) as court_fee,\r\n"
            + "    coalesce(sum(cb.product_fee), 0) as product_fee\r\n"
            + "from\r\n"
            + "    court_booking cb\r\n"
            + "inner join court c on\r\n"
            + "    cb.court_id = c.id\r\n"
            + "inner join center c2 on\r\n"
            + "    c.center_id = c2.id\r\n"
            + "where\r\n"
            + "    cb.usage_date = :date\r\n"
            + "    and c2.\"owner\" = :userId\r\n"
            + "    and c2.id = :centerId", nativeQuery = true)
    CenterOwnerStatisticsCenterProjection findCenterOwnerStatisticsCenterDaily(Long userId, Long centerId, LocalDate date);
    @Query(value = "select\r\n"
            + "    coalesce(sum(cb.court_fee), 0) as court_fee,\r\n"
            + "    coalesce(sum(cb.product_fee), 0) as product_fee\r\n"
            + "from\r\n"
            + "    court_booking cb\r\n"
            + "inner join court c on\r\n"
            + "    cb.court_id = c.id\r\n"
            + "inner join center c2 on\r\n"
            + "    c.center_id = c2.id\r\n"
            + "where\r\n"
            + "    cb.usage_date between :dateFrom and :dateTo\r\n"
            + "    and c2.\"owner\" = :userId\r\n"
            + "    and c2.id = :centerId", nativeQuery = true)
    CenterOwnerStatisticsCenterProjection findCenterOwnerStatisticsCenterMonthly(Long userId, Long centerId, LocalDate dateFrom, LocalDate dateTo);
    @Query(value = "select\r\n"
            + "    coalesce(sum(cb.court_fee), 0) as court_fee,\r\n"
            + "    coalesce(sum(cb.product_fee), 0) as product_fee\r\n"
            + "from\r\n"
            + "    court_booking cb\r\n"
            + "inner join court c on\r\n"
            + "    cb.court_id = c.id\r\n"
            + "inner join center c2 on\r\n"
            + "    c.center_id = c2.id\r\n"
            + "where\r\n"
            + "    cb.usage_date between :dateFrom and :dateTo\r\n"
            + "    and c2.\"owner\" = :userId\r\n"
            + "    and c2.id = :centerId", nativeQuery = true)
    CenterOwnerStatisticsCenterProjection findCenterOwnerStatisticsCenterYearly(Long userId, Long centerId, LocalDate dateFrom, LocalDate dateTo);
}
