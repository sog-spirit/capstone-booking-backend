package com.capstone.core.table;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "blacklist_jwt_token", schema = "public")
@Data
public class BlacklistJWTTokenTable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String jwtToken;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private UserTable user;
}
