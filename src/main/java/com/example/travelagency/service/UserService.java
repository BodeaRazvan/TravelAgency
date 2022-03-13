package com.example.travelagency.service;

import com.example.travelagency.entity.User;
import com.example.travelagency.repository.UserRepository;

public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void addUser(User user){
        userRepository.addUser(user);
    }
    public User findUserByUsername(String username){
        return userRepository.findUser(username);
    }
    public void modifyUser(User user){
        userRepository.modifyUser(user);
    }

}
