package com.rayshan.expenseview.entities;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.Instant;
import java.time.LocalDateTime;

@Entity
@Table(name = "auth")
@Data
public class AuthEntity {
    @Id
    @Column(name = "user_id")
    private int id;

    @Column(name = "verification_code")
    private String verificationCode;

    @Column(name = "auth_token")
    private String authToken;

    @Column(name = "token_expiry_time")
    private Instant tokenExpiryTime;
}
