package com.rayshan.expenseview.entities;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigInteger;
import java.time.LocalDateTime;

@Entity
@Table(name = "user_expense_data")
@Data
public class ExpenseEntity {
     //integer REFERENCES metadata(id) ON DELETE CASCADE,
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
    @Column(name = "description", nullable = false)
    private String description;

    // bigint,
    @Column(name = "entry_date", nullable = false)
    private BigInteger entryDate;
}
