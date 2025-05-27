package com.rayshan.expenseview.modals;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class ExpenseData {
    private List<Expense> entries;

    @Data
    public static class Expense {
        private Integer id;
        private String title;
        private Double amount;
        private String category;
        private String description;
        @JsonProperty("entry_date")
        private Long entryDate;
    }
}
