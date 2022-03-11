package com.example.travelagency.controller;

import com.example.travelagency.Main;
import com.example.travelagency.entity.User;
import com.example.travelagency.repository.UserRepository;
import com.example.travelagency.service.UserService;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.io.IOException;

public class RegisterController {
    @FXML
    private TextField registerUsername;
    @FXML
    private PasswordField registerPassword1;
    @FXML
    private PasswordField registerPassword2;
    @FXML
    private TextField registerAddress;
    @FXML
    private TextField registerEmail;
    @FXML
    private TextField errorLogRegister;

    @FXML
    private void goToLoginPage() throws IOException{
        Main.setRoot("loginPage");
    }

    @FXML
    private void clearFields(){
        registerUsername.clear();
        registerPassword1.clear();
        registerPassword2.clear();
        registerAddress.clear();
        registerEmail.clear();
    }

    @FXML
    private int registerUser(){
         UserService userService = new UserService();
         UserRepository userRepository = new UserRepository(userService);
        errorLogRegister.clear();
        if(registerEmail.getText().equals("") || registerAddress.getText().equals("") || registerUsername.getText().equals("") || registerPassword1.getText().equals("") || registerPassword2.getText().equals("")){
            errorLogRegister.setText("All fields must be completed");
            clearFields();
            return 0;
        }
        if(!registerPassword1.getText().equals(registerPassword2.getText())){
            errorLogRegister.setText("Passwords do not match");
            registerPassword2.clear();
            registerPassword1.clear();
            return 0;
        }
        if(!(registerEmail.getText().contains("@") && registerEmail.getText().contains("."))){
            errorLogRegister.setText("Email is not correct");
            registerEmail.clear();
            return 0;
        }
        User user = new User(registerUsername.getText(), registerPassword1.getText(), registerAddress.getText(), registerEmail.getText(), "user", null);

        try{
           userRepository.addUser(user);
        }catch (Exception e){
            errorLogRegister.setText("User already registered");
            return 0;
        }

        errorLogRegister.setText("Registered Successfully");
        return 0;
    }
}
