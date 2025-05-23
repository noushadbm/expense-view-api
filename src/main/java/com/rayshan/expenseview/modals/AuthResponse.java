package com.rayshan.expenseview.modals;

import lombok.Data;

import java.time.Instant;

@Data
public class AuthResponse {
    private int userId;
    private String userName;
    private String token;
    private Instant expiry;
}
