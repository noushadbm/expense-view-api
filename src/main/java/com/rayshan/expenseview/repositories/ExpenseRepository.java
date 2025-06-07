package com.rayshan.expenseview.repositories;

import com.rayshan.expenseview.entities.ExpenseEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ExpenseRepository extends JpaRepository<ExpenseEntity, ExpenseEntity.ExpenseEntityId>  {

    long countByMetadataId(Integer metadataId);
    List<ExpenseEntity> findByMetadataId(Integer metadataId);
}
