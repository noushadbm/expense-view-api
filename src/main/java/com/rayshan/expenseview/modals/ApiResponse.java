package com.rayshan.expenseview.modals;

import lombok.Data;

@Data
public class ApiResponse<T> {
    private int statusCode;
    private String statusMsg;
    private String message;
    private T data;
}
