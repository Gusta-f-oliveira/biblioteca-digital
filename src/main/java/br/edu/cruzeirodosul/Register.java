package br.edu.cruzeirodosul;

import java.io.IOException;

import javafx.fxml.FXML;

public class Register {

    @FXML
    private void switchToPrimary() throws IOException {
        App.setRoot("Login");
    }
}