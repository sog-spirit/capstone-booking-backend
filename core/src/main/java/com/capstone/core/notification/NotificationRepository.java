package com.capstone.core.notification;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.capstone.core.table.NotificationTable;

@Repository
public interface NotificationRepository extends JpaRepository<NotificationTable, Long> {

}
