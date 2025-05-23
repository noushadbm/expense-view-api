package com.rayshan.expenseview.controllers;

import com.rayshan.expenseview.modals.ApiResponse;
import com.rayshan.expenseview.modals.AuthRequest;
import com.rayshan.expenseview.modals.AuthResponse;
import com.rayshan.expenseview.modals.UserModal;
import com.rayshan.expenseview.services.AuthService;
import com.rayshan.expenseview.services.UserService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
@Log4j2
public class UserController {
    private UserService userService;
    private AuthService authService;

    @Autowired
    public UserController(UserService userService, AuthService authService) {
        this.userService = userService;
        this.authService = authService;
    }

    @PostMapping("/authenticate")
    public ApiResponse<AuthResponse> login(@RequestBody AuthRequest authRequest) {
        log.info("Request received");
        AuthResponse autResponse = authService.login(authRequest.getUsername(), authRequest.getPassword());
        ApiResponse<AuthResponse> response = new ApiResponse<>();
        response.setStatusCode(200);
        response.setStatusText("SUCCESS");
        response.setData(autResponse);
        log.info("Returning response.");
        return response;

    }

    @GetMapping("/users")
    public ApiResponse<List<UserModal>> getAllUsers() {
        log.info("Request received");
        List<UserModal> allUsers = userService.getAllUsers();
        log.info("Returning response.");
        ApiResponse<List<UserModal>> response = new ApiResponse<>();
        response.setStatusCode(200);
        response.setStatusText("SUCCESS");
        response.setData(allUsers);
        return response;
    }

    @GetMapping("/users/{id}")
    public ApiResponse<UserModal> getUserById(@PathVariable int id) {
        log.info("Request received");
        UserModal user = userService.getUserById(id);
        ApiResponse<UserModal> response = new ApiResponse<>();
        response.setStatusCode(200);
        response.setStatusText("SUCCESS");
        response.setData(user);
        log.info("Returning response.");
        return response;
    }

    @PostMapping("/users")
    public ApiResponse<UserModal> addUser(@RequestBody UserModal userDetails) {
        log.info("Request received");
        UserModal newUser = userService.addUser(userDetails);
        ApiResponse<UserModal> response = new ApiResponse<>();
        response.setStatusCode(200);
        response.setStatusText("SUCCESS");
        response.setData(newUser);
        log.info("Returning response.");
        return response;
    }
}
