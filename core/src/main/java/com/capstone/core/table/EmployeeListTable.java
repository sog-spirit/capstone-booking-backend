package com.capstone.core.table;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.AssertFalse;
import lombok.Data;

@Entity
@Table(name = "employee_list", schema = "public")
@Data
public class EmployeeListTable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(optional = false)
    @JoinColumn(name = "employee_id", nullable = false)
    private UserTable employee;
    @ManyToOne(optional = false)
    @JoinColumn(name = "center_owner_id", nullable = false)
    private UserTable centerOwner;
}
