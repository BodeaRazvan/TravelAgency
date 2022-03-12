package com.example.travelagency.controller;

import com.example.travelagency.Main;
import com.example.travelagency.entity.User;
import com.example.travelagency.repository.UserRepository;
import com.example.travelagency.service.UserService;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;


import java.io.IOException;

public class LoginController {
    @FXML
    private TextField loginUsername;
    @FXML
    private TextField loginPassword;
    @FXML
    private TextField loginErrorLog;

    @FXML
    private void goToRegisterPage() throws IOException {
        Main.setRoot("registerPage");
    }

    @FXML
    private int logIn() throws IOException {
        UserService userService = new UserService();
        UserRepository userRepository = new UserRepository(userService);
        String username = loginUsername.getText();
        String password = loginPassword.getText();
        if(username.equals("") || password.equals("")){
            loginErrorLog.setText("All fields must be completed");
            return 0;
        }
        try{
            User newUser = userRepository.findUserByUsername(username);
            if(!newUser.getPassword().equals(password)){
                loginErrorLog.setText("Username or password incorrect");
                return 0;
            }
            if(newUser.getRole().equals("admin")){
                Main.setRoot("adminPage");
            }
            if(newUser.getRole().equals("user")){
                Main.setRoot("userPage");
            }

        } catch (Exception e){
            loginErrorLog.setText("Username or password incorrect");
            return 0;
        }
        return 0;
    }

}
