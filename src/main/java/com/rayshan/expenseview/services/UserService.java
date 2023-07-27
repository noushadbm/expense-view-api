package com.rayshan.expenseview.services;

import com.rayshan.expenseview.entities.UserEntity;
import com.rayshan.expenseview.modals.UserModal;
import com.rayshan.expenseview.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService {
    private UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<UserModal> getAllUsers() {
        List<UserEntity> allUserEntities = userRepository.findAll();
        return allUserEntities.stream().map(userEntity ->
                UserModal.builder()
                        .userId(userEntity.getId())
                        .userName(userEntity.getUserName())
                        .userPassword(userEntity.getUserPassword())
                        .build())
                .collect(Collectors.toList());
    }
}
