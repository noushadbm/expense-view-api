package com.rayshan.expenseview.controllers;

import com.rayshan.expenseview.modals.UserModal;
import com.rayshan.expenseview.services.UserService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
@Log4j2
public class UserController {
    private UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/users")
    public List<UserModal> getAllUsers() {
        log.info("Request received");
        System.out.println("sout: Request received");
        List<UserModal> allUsers = userService.getAllUsers();
        log.info("Returning response.");
        System.out.println("sout: Returning response.");
        return allUsers;
    }
}
