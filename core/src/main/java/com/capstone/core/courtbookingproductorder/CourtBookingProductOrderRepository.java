package com.capstone.core.courtbookingproductorder;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.capstone.core.courtbookingproductorder.projection.AdminCourtBookingStatisticsProjection;
import com.capstone.core.courtbookingproductorder.projection.AdminProductOrderStatisticsProjection;
import com.capstone.core.courtbookingproductorder.projection.AdminProductOrderStatisticsTodayProjection;
import com.capstone.core.courtbookingproductorder.projection.CenterOwnerCourtBookingProductOrderDetailListProjection;
import com.capstone.core.courtbookingproductorder.projection.CenterOwnerCourtBookingStatisticsProjection;
import com.capstone.core.courtbookingproductorder.projection.CenterOwnerProductOrderStatisticsProjection;
import com.capstone.core.courtbookingproductorder.projection.CenterOwnerProductOrderStatisticsTodayProjection;
import com.capstone.core.courtbookingproductorder.projection.UserCourtBookingProductOrderDetailListProjection;
import com.capstone.core.table.CourtBookingProductOrderTable;

@Repository
public interface CourtBookingProductOrderRepository extends JpaRepository<CourtBookingProductOrderTable, Long>, JpaSpecificationExecutor<CourtBookingProductOrderTable> {
    List<UserCourtBookingProductOrderDetailListProjection> findUserCourtBookingProductOrderDetailListByCourtBookingIdAndCourtBookingUserIdAndStatusNot(Long courtBookingId, Long userId, Long status);
    List<CenterOwnerCourtBookingProductOrderDetailListProjection> findCenterOwnerCourtBookingProductOrderDetailListByCourtBookingIdAndStatusNot(Long courtBookingId, Long status);
    @Query(value = "select\r\n"
            + "    p.name,\r\n"
            + "    count(*) as total_orders\r\n"
            + "from\r\n"
            + "    court_booking_product_order_detail cbpod\r\n"
            + "join\r\n"
            + "    court_booking_product_order cbpo\r\n"
            + "on\r\n"
            + "    cbpod.court_booking_product_order_id = cbpo.id\r\n"
            + "join\r\n"
            + "    product_inventory pi2\r\n"
            + "on\r\n"
            + "    cbpod.product_inventory_id = pi2.id\r\n"
            + "join\r\n"
            + "    product p\r\n"
            + "on\r\n"
            + "    pi2.product_id = p.id\r\n"
            + "join\r\n"
            + "    court_booking cb\r\n"
            + "on\r\n"
            + "    cbpo.court_booking_id = cb.id\r\n"
            + "join\r\n"
            + "    court c\r\n"
            + "on\r\n"
            + "    cb.court_id = c.id\r\n"
            + "join\r\n"
            + "    center c2\r\n"
            + "on\r\n"
            + "    c.center_id = c2.id \r\n"
            + "where\r\n"
            + "    c2.\"owner\" = :userId\r\n"
            + "    and\r\n"
            + "        case\r\n"
            + "            when ((:dateFrom <> '') and (:dateTo <> '')) is true then cbpo.create_timestamp between :dateFrom and :dateTo\r\n"
            + "            when (:dateFrom <> '') is true then cbpo.create_timestamp >= :dateFrom\r\n"
            + "            when (:dateTo <> '') is true then cbpo.create_timestamp <= :dateTo\r\n"
            + "            else true\r\n"
            + "        end\r\n"
            + "    and case when :centerId is not null then c.center_id = :centerId else true end\r\n"
            + "group by\r\n"
            + "    p.name", nativeQuery = true)
    List<CenterOwnerProductOrderStatisticsProjection> findCenterOwnerProductOrderStatistics(Long userId, LocalDateTime dateFrom, LocalDateTime dateTo, Long centerId);
    @Query(value = "select\r\n"
            + "    cb.status,\r\n"
            + "    count(*) as total_court_bookings\r\n"
            + "from\r\n"
            + "    court_booking cb\r\n"
            + "join\r\n"
            + "    court c on cb.court_id = c.id\r\n"
            + "join\r\n"
            + "    center c2 on c.center_id = c2.id\r\n"
            + "where\r\n"
            + "    c2.\"owner\" = :userId\r\n"
            + "    and\r\n"
            + "        case\r\n"
            + "            when ((:dateFrom <> '') and (:dateTo <> '')) is true then cb.create_timestamp between :dateFrom and :dateTo\r\n"
            + "            when (:dateFrom <> '') is true then cb.create_timestamp >= :dateFrom\r\n"
            + "            when (:dateTo <> '') is true then cb.create_timestamp <= :dateTo\r\n"
            + "            else true\r\n"
            + "        end\r\n"
            + "    and case when :centerId is not null then c.center_id = :centerId else true end\r\n"
            + "group by\r\n"
            + "    cb.status\r\n"
            + "", nativeQuery = true)
    List<CenterOwnerCourtBookingStatisticsProjection> findCenterOwnerCourtBookingStatistics(Long userId, LocalDateTime dateFrom, LocalDateTime dateTo, Long centerId);
    @Query(value = "select\r\n"
            + "    count(*) as product_order_count,\r\n"
            + "    sum(cbpo.fee) as revenue_count\r\n"
            + "from\r\n"
            + "    court_booking_product_order cbpo\r\n"
            + "join\r\n"
            + "    court_booking cb\r\n"
            + "on\r\n"
            + "    cbpo.court_booking_id = cb.id\r\n"
            + "join\r\n"
            + "    court c\r\n"
            + "on\r\n"
            + "    cb.court_id = c.id\r\n"
            + "join\r\n"
            + "    center c2\r\n"
            + "on\r\n"
            + "    c.center_id = c2.id\r\n"
            + "where\r\n"
            + "    c2.\"owner\" = :userId\r\n"
            + "    and cbpo.create_timestamp >= :dateTimeFrom\r\n"
            + "    and cbpo.create_timestamp <= :dateTimeTo\r\n"
            + "    and cb.user_id <> :userId", nativeQuery = true)
    CenterOwnerProductOrderStatisticsTodayProjection findCenterOwnerProductOrderStatisticsToday(Long userId, LocalDateTime dateTimeFrom, LocalDateTime dateTimeTo);
    @Query(value = "select\r\n"
            + "    p.name,\r\n"
            + "    count(*) as total_orders\r\n"
            + "from\r\n"
            + "    court_booking_product_order_detail cbpod\r\n"
            + "join\r\n"
            + "    court_booking_product_order cbpo\r\n"
            + "on\r\n"
            + "    cbpod.court_booking_product_order_id = cbpo.id\r\n"
            + "join\r\n"
            + "    product_inventory pi2\r\n"
            + "on\r\n"
            + "    cbpod.product_inventory_id = pi2.id\r\n"
            + "join\r\n"
            + "    product p\r\n"
            + "on\r\n"
            + "    pi2.product_id = p.id\r\n"
            + "join\r\n"
            + "    court_booking cb\r\n"
            + "on\r\n"
            + "    cbpo.court_booking_id = cb.id\r\n"
            + "join\r\n"
            + "    court c\r\n"
            + "on\r\n"
            + "    cb.court_id = c.id\r\n"
            + "join\r\n"
            + "    center c2\r\n"
            + "on\r\n"
            + "    c.center_id = c2.id \r\n"
            + "where\r\n"
            + "        case\r\n"
            + "            when ((:dateFrom <> '') and (:dateTo <> '')) is true then cbpo.create_timestamp between :dateFrom and :dateTo\r\n"
            + "            when (:dateFrom <> '') is true then cbpo.create_timestamp >= :dateFrom\r\n"
            + "            when (:dateTo <> '') is true then cbpo.create_timestamp <= :dateTo\r\n"
            + "            else true\r\n"
            + "        end\r\n"
            + "    and case when :centerId is not null then c.center_id = :centerId else true end\r\n"
            + "group by\r\n"
            + "    p.name", nativeQuery = true)
    List<AdminProductOrderStatisticsProjection> findAdminProductOrderStatistics(LocalDateTime dateFrom, LocalDateTime dateTo, Long centerId);
    @Query(value = "select\r\n"
            + "    cb.status,\r\n"
            + "    count(*) as total_court_bookings\r\n"
            + "from\r\n"
            + "    court_booking cb\r\n"
            + "join\r\n"
            + "    court c on cb.court_id = c.id\r\n"
            + "join\r\n"
            + "    center c2 on c.center_id = c2.id\r\n"
            + "where\r\n"
            + "        case\r\n"
            + "            when ((:dateFrom <> '') and (:dateTo <> '')) is true then cb.create_timestamp between :dateFrom and :dateTo\r\n"
            + "            when (:dateFrom <> '') is true then cb.create_timestamp >= :dateFrom\r\n"
            + "            when (:dateTo <> '') is true then cb.create_timestamp <= :dateTo\r\n"
            + "            else true\r\n"
            + "        end\r\n"
            + "    and case when :centerId is not null then c.center_id = :centerId else true end\r\n"
            + "group by\r\n"
            + "    cb.status\r\n"
            + "", nativeQuery = true)
    List<AdminCourtBookingStatisticsProjection> findAdminCourtBookingStatistics(LocalDateTime dateFrom, LocalDateTime dateTo, Long centerId);
    @Query(value = "select\r\n"
            + "    count(*) as product_order_count,\r\n"
            + "    sum(cbpo.fee) as revenue_count\r\n"
            + "from\r\n"
            + "    court_booking_product_order cbpo\r\n"
            + "join\r\n"
            + "    court_booking cb\r\n"
            + "on\r\n"
            + "    cbpo.court_booking_id = cb.id\r\n"
            + "join\r\n"
            + "    court c\r\n"
            + "on\r\n"
            + "    cb.court_id = c.id\r\n"
            + "join\r\n"
            + "    center c2\r\n"
            + "on\r\n"
            + "    c.center_id = c2.id\r\n"
            + "where\r\n"
            + "    cbpo.create_timestamp >= :dateTimeFrom\r\n"
            + "    and cbpo.create_timestamp <= :dateTimeTo\r\n", nativeQuery = true)
    AdminProductOrderStatisticsTodayProjection findAdminProductOrderStatisticsToday(LocalDateTime dateTimeFrom, LocalDateTime dateTimeTo);
}