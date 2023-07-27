package com.rayshan.expenseview.modals;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserModal {
    private int userId;
    private String userName;
    private String userPassword;
}
