package com.example.travelagency.repository;

import com.example.travelagency.entity.User;
import com.example.travelagency.service.UserService;

public class UserRepository {
    private final UserService userService;

    public UserRepository(UserService userService) {
        this.userService = userService;
    }

    public void addUser(User user){
        userService.addUser(user);
    }
    public User findUserByUsername(String username){
        return userService.findUser(username);
    }

}
