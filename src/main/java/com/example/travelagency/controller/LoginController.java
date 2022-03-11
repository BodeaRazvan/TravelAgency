package com.example.travelagency.controller;

import com.example.travelagency.Main;
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
    private void logIn(){

    }
}
