package com.rayshan.expenseview.entities;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigInteger;
import java.time.LocalDateTime;

@Entity
@Table(name = "user_expense_data")
@Data
@IdClass(ExpenseEntity.ExpenseEntityId.class)
public class ExpenseEntity {
    @Id
    @Column(name = "metadata_id", nullable = false)
    private Integer metadataId;

    @Id
    @Column(name = "id", nullable = false)
    private Integer id;

    // varchar(50),
    @Column(name = "title", nullable = false)
    private String title;

    // real,
    @Column(name = "amount", nullable = false)
    private Double amount;

    // varchar(15),
    @Column(name = "category", nullable = false)
    private String category;

    // varchar(100),
    @Column(name = "description")
    private String description;

    // bigint,
    @Column(name = "entry_date", nullable = false)
    private BigInteger entryDate;

    @Data
    public static class ExpenseEntityId implements Serializable {
        private Integer metadataId;
        private Integer id;
    }
}
