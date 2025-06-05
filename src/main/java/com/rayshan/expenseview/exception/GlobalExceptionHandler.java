package com.rayshan.expenseview.exception;

import com.rayshan.expenseview.modals.ApiResponse;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
@Log4j2
public class GlobalExceptionHandler {

    // This class can be used to handle global exceptions in the application.
    // You can define methods annotated with @ExceptionHandler to handle specific exceptions.
    // For example, you can handle ExpenseException or any other custom exceptions here.

     @ExceptionHandler(ExpenseException.class)
     public ResponseEntity<ApiResponse> handleExpenseException(ExpenseException ex) {
         log.error("[GlobalExceptionHandler] ExpenseException occurred: {}", ex.getMessage());
         ApiResponse <String> response = new ApiResponse<>();
         response.setStatusCode(HttpStatus.BAD_REQUEST.value());
         response.setStatusMsg("FAILED");
         response.setMessage(ex.getMessage());
         return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
     }
}
