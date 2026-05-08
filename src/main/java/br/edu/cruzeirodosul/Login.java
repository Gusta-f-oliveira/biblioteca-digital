package br.edu.cruzeirodosul;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class Login {

    @FXML
    private TextField txtNome;

    @FXML
    private TextField txtSenha;

    @FXML
    private void login() throws IOException {
        String nome = txtNome.getText();
        String senha = txtSenha.getText();

        if (nome.equals(username) && senha.equals(userpassword)) {
            App.setRoot("library");
        } else {
            
        }
        
    }

    @FXML
    private void register() throws IOException {
        App.setRoot("register");
    }
}
