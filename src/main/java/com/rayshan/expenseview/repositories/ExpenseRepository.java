package com.rayshan.expenseview.repositories;

import com.rayshan.expenseview.entities.ExpenseEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExpenseRepository extends JpaRepository<ExpenseEntity, Integer>  {
}
