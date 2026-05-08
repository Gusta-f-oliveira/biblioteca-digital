package br.edu.cruzeirodosul.view;

import java.io.IOException;

import br.edu.cruzeirodosul.BibliotecaDigital;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class Register {

    @FXML
    private TextField txtNome;

    @FXML
    private TextField txtSenha;

    @FXML
    private TextField txtSenhaConfirma;
    
    @FXML
    private void telaLogin() throws IOException {
        BibliotecaDigital.setRoot("Login");
    }
}