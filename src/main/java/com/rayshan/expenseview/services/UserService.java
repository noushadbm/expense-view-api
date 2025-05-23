package com.rayshan.expenseview.services;

import com.rayshan.expenseview.entities.UserEntity;
import com.rayshan.expenseview.modals.UserModal;
import com.rayshan.expenseview.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {
    private UserRepository userRepository;
    private PasswordService passwordService;

    @Autowired
    public UserService(UserRepository userRepository, PasswordService passwordService) {
        this.userRepository = userRepository;
        this.passwordService = passwordService;
    }

    public List<UserModal> getAllUsers() {
        List<UserEntity> allUserEntities = userRepository.findAll();
        return allUserEntities.stream().map(userEntity -> toDTO(userEntity))
                .collect(Collectors.toList());
    }

    public UserModal getUserById(int id) {
        Optional<UserEntity> userOpt = userRepository.findById(id);
        UserEntity user = userOpt.orElseThrow(() -> new RuntimeException("User not found"));
        return toDTO(user);
    }

    public UserModal addUser(UserModal userDetail) {
        int maxId = userRepository.findMaxUserId();
        UserEntity userEntity = new UserEntity();
        userEntity.setId(++maxId);
        userEntity.setUserName(userDetail.getName());
        userEntity.setUserPassword(passwordService.encrypt(userDetail.getPassword()));
        userEntity.setEmail(userEntity.getEmail());
        userEntity.setCreateTime(LocalDateTime.now());
        userRepository.save(userEntity);
        return toDTO(userEntity);
    }

    private UserModal toDTO(UserEntity userEntity) {
        return UserModal.builder()
                .userId(userEntity.getId())
                .name(userEntity.getUserName())
                .password("********") // Masking password
                .build();
    }
}
