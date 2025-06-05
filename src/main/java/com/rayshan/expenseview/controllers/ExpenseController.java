package com.rayshan.expenseview.controllers;

import com.rayshan.expenseview.exception.ExpenseException;
import com.rayshan.expenseview.modals.ApiResponse;
import com.rayshan.expenseview.modals.ExpenseData;
import com.rayshan.expenseview.services.ExpenseService;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/expenses")
@Log4j2
public class ExpenseController {
    private ExpenseService expenseService;
    public ExpenseController(ExpenseService expenseService) {
        this.expenseService = expenseService;
    }
    @GetMapping("/{userId}/sync/begin")
    public ApiResponse<Map<String, Object>> syncInit(@PathVariable Integer userId) throws ExpenseException {
        log.info("[Sync Init] Request received, userId: {}", userId);
        Map<String, Object> metadata = expenseService.createMetadata(userId);
        ApiResponse<Map<String, Object>> response = new ApiResponse<>();
        response.setStatusCode(0);
        response.setStatusMsg("SUCCESS");
        response.setMessage("Metadata created successfully");
        response.setData(metadata);
        log.info("Returning sync init response.");
        return response;
    }

    @PostMapping("/{userId}/sync/{metadataId}")
    public ApiResponse<Map<String, Object>> syncUpdate(@PathVariable Integer userId,
                                                       @PathVariable Integer metadataId,
                                                       @RequestBody ExpenseData expenseData) {
        log.info("Request received to sync update for user with userID: {}, metadataId: {}", userId, metadataId);
        Map<String, Object> metadata = expenseService.updateMetadata(userId, metadataId, expenseData);
        ApiResponse<Map<String, Object>> response = new ApiResponse<>();
        response.setStatusCode(0);
        response.setStatusMsg("SUCCESS");
        response.setMessage("Expense data updated successfully");
        response.setData(metadata);
        log.info("Returning sync update response.");
        return response;
    }

    @PostMapping("/{userId}/sync/{metadataId}/finish")
    public ApiResponse<Map<String, Object>> syncFinish(@PathVariable Integer userId, @PathVariable Integer metadataId) {
        log.info("Request received to sync finish for user with ID: {}", userId);
        Map<String, Object> metadata = expenseService.finishSync(userId, metadataId);
        ApiResponse<Map<String, Object>> response = new ApiResponse<>();
        response.setStatusCode(0);
        response.setStatusMsg("SUCCESS");
        response.setMessage("Expense sync finished successfully");
        response.setData(metadata);
        log.info("Returning response from syncFinish");
        return response;
    }
}
