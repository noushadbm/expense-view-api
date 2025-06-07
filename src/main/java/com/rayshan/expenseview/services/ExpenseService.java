package com.rayshan.expenseview.services;

import com.rayshan.expenseview.entities.ExpenseEntity;
import com.rayshan.expenseview.entities.Metadata;
import com.rayshan.expenseview.exception.ExpenseException;
import com.rayshan.expenseview.modals.ExpenseData;
import com.rayshan.expenseview.modals.UserModal;
import com.rayshan.expenseview.repositories.ExpenseRepository;
import com.rayshan.expenseview.repositories.MetadataRepository;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigInteger;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.rayshan.expenseview.common.Constants.PAGE_SIZE;

@Service
@Log4j2
public class ExpenseService {
    private final UserService userService;
    private final MetadataRepository metadataRepository;
    private final ExpenseRepository expenseRepository;

    public ExpenseService(MetadataRepository metadataRepository, ExpenseRepository expenseRepository, UserService userService) {
        this.metadataRepository = metadataRepository;
        this.expenseRepository = expenseRepository;
        this.userService = userService;
    }
    @Transactional
    public Map<String, Object> createMetadata(Integer userId) throws ExpenseException {
        UserModal user = userService.getUserById(userId); // Ensure user exists
        if (user == null) {
            log.error("User with ID {} not found", userId);
            throw new ExpenseException("User not found");
        }
        Metadata metadata = new Metadata();
        metadata.setUserId(userId);
        Metadata saved = metadataRepository.save(metadata);
        Integer metadataId = saved.getId();
        if (metadataId == null) {
            log.error("Failed to create metadata for user ID: {}", userId);
            throw new RuntimeException("Failed to create metadata");
        }

        log.info("Generated metadata ID: {}", metadataId);
        Map<String, Object> data = new HashMap<>();
        data.put("metadataId", metadataId);
        return data;
    }

    @Transactional
    public Map<String, Object> updateMetadata(Integer userId, Integer metadataId, ExpenseData expenseData) {
        Metadata metaData = metadataRepository.findByIdAndUserId(metadataId, userId);
        if (metaData == null) {
            throw new RuntimeException("Metadata not found for the given user");
        }

        List<ExpenseEntity> expenseEntities = expenseData.getEntries().stream().map(
                entry -> {
                    ExpenseEntity expenseEntity = new ExpenseEntity();
                    expenseEntity.setMetadataId(metadataId);
                    expenseEntity.setId(entry.getId());
                    expenseEntity.setTitle(entry.getTitle());
                    expenseEntity.setAmount(entry.getAmount());
                    expenseEntity.setCategory(entry.getCategory());
                    expenseEntity.setDescription(entry.getDescription());
//                    LocalDateTime entryDate = Instant.ofEpochMilli(entry.getEntryDate())
//                            .atZone(ZoneId.systemDefault())
//                            .toLocalDateTime();
//                    expenseEntity.setEntryDate(entryDate);
                    expenseEntity.setEntryDate(BigInteger.valueOf(entry.getEntryDate()));
                    return expenseEntity;
                }
        ).collect(Collectors.toList());
        List<ExpenseEntity> savedEntities = expenseRepository.saveAll(expenseEntities);
        log.info("Saved {} expense entries for metadata ID {}", savedEntities.size(), metadataId);
        Map<String, Object> data = new HashMap<>();
        data.put("count", savedEntities.size());
        return data;
    }

    @Transactional
    public Map<String, Object> finishSync(Integer userId, Integer metadataId) {
        Metadata metadata = metadataRepository.findByIdAndUserId(metadataId, userId);
        if(metadata == null) {
            throw new RuntimeException("Metadata not found for the given user");
        }
        metadata.setUpdateTime(LocalDateTime.now());
        metadata.setStatus("FINISHED");
        Metadata updatedMetadata = metadataRepository.save(metadata);
        log.info("Metadata updated: {}", updatedMetadata);
        long count = expenseRepository.countByMetadataId(metadataId);
        Map<String, Object> data = new HashMap<>();
        data.put("rowsUpdated", count);
        return data;
    }

    public Map<String, Object> initRestore(Integer userId) throws ExpenseException {
        log.info("Initializing restore process");
        List<Metadata> list = metadataRepository.findByUserIdAndStatusOrderByIdDesc(userId, "FINISHED");
        if(list.isEmpty()) {
            log.warn("No finished metadata found for user ID: {}", userId);
            throw new ExpenseException("No finished metadata found for user ID: " + userId);
        }
        Metadata latest = list.get(0);
        List<ExpenseEntity> records = expenseRepository.findByMetadataId(latest.getId());
        Map<String, Object> response = new HashMap<>();
        response.put("metadataId", latest.getId());
        response.put("totalCount", records.size());
        long totalPage = records.size() / PAGE_SIZE;
        if(records.size() % PAGE_SIZE != 0) {
            totalPage++;
        }
        response.put("totalPages", totalPage);
        return response;
    }
}
