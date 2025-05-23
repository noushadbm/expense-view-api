package com.rayshan.expenseview.services;

import com.rayshan.expenseview.entities.AuthEntity;
import com.rayshan.expenseview.entities.UserEntity;
import com.rayshan.expenseview.modals.AuthResponse;
import com.rayshan.expenseview.repositories.AuthRepository;
import com.rayshan.expenseview.repositories.UserRepository;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.Instant;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordService passwordService;
    private final AuthRepository authRepository;

    public AuthService(PasswordService passwordService, UserRepository userRepository, AuthRepository authRepository) {
        this.passwordService = passwordService;
        this.userRepository = userRepository;
        this.authRepository = authRepository;
    }
    @Transactional
    public AuthResponse login(String username, String password) {
        List<UserEntity> userList = this.userRepository.findByUserName(username);
        if(userList.isEmpty()) {
            throw new RuntimeException("User not found");
        }

        UserEntity user = userList.get(0);
        String hashedPassword = user.getUserPassword();
        boolean isValid = passwordService.isCorrectPassword(password, hashedPassword);
        if (!isValid) {
            throw new RuntimeException("Invalid password");
        }

        Instant instant = passwordService.getTokenExpiry();
        Map<String, Object> claims = new HashMap<>();
        claims.put("username", username);
        String token = passwordService.generateJwt(claims, instant);
        int userId = user.getId();

        AuthEntity authEntity;
        Optional<AuthEntity> authOpt = authRepository.findById(userId);
        if(authOpt.isPresent() ) {
            authEntity = authOpt.get();
        } else {
            authEntity = new AuthEntity();
            authEntity.setId(userId);
        }
        authEntity.setAuthToken(token);
        authEntity.setTokenExpiryTime(instant);
        authRepository.save(authEntity);

        AuthResponse authResponse = new AuthResponse();
        authResponse.setUserId(userId);
        authResponse.setUserName(username);
        authResponse.setToken(token);
        authResponse.setExpiry(instant);

        return authResponse;
    }


}
