package br.edu.cruzeirodosul.view;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import br.edu.cruzeirodosul.BibliotecaDigital;
import br.edu.cruzeirodosul.model.ConnectionFactory;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

public class Register {

    @FXML
    private TextField txtNome;

    @FXML
    private TextField txtSenha;

    // @FXML
    // private TextField txtSenhaConfirma;

    @FXML
    private ComboBox<String> cbTipo;

    @FXML
    public void initialize() {
        cbTipo.getItems().addAll("COMUM", "BIBLIOTECÁRIO");
    }
    
    @FXML
    private void telaLogin() throws IOException {
        BibliotecaDigital.setRoot("login");
    }

    @FXML
    private void cadastrarUsuario() throws IOException {
        String nome = txtNome.getText();
        String senha = txtSenha.getText();
        // No Scene Builder, adicione um ComboBox chamado 'cbTipo'
        String tipo = cbTipo.getValue(); 

        String sql = "INSERT INTO usuarios (nome, senha, tipo_usuario) VALUES (?, ?, ?)";
        ConnectionFactory fabrica = new ConnectionFactory();

        try (Connection conexao = fabrica.obtemConexao();
            PreparedStatement comando = conexao.prepareStatement(sql)) {

            comando.setString(1, nome);
            comando.setString(2, senha);
            comando.setString(3, tipo);
            comando.executeUpdate();

            // Após cadastrar, volta para o login
            BibliotecaDigital.setRoot("login");

        } catch (SQLException e) {
            System.err.println("Erro ao cadastrar: " + e.getMessage());
        }
    }
    
}