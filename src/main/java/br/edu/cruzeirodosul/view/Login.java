package br.edu.cruzeirodosul.view;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import br.edu.cruzeirodosul.BibliotecaDigital;
import br.edu.cruzeirodosul.model.ConnectionFactory;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class Login {

    @FXML
    private TextField txtNome;

    @FXML
    private TextField txtSenha;

    @FXML
    private void login() throws IOException {
        String nomeUsuario = txtNome.getText();
        String senhaUsuario = txtSenha.getText();

        String sql = "SELECT * FROM usuarios WHERE nome = ? AND senha = ?";
        
        ConnectionFactory fabrica = new ConnectionFactory();
        
        try (Connection conexao = fabrica.obtemConexao()) {

            if (conexao == null) {
                System.out.println("Erro crítico: Banco de dados inoperante.");
            }

            PreparedStatement comando = conexao.prepareStatement(sql);

            comando.setString(1, nomeUsuario);
            comando.setString(2, senhaUsuario);

            ResultSet resultado = comando.executeQuery();

            if (resultado.next()) {
                BibliotecaDigital.setRoot("library");
            } else {
                System.out.println("Usuário ou senha inválidos!");
            }
        } catch (SQLException e) {
            System.err.println("Erro ao processar login: " + e.getMessage());
        }
        
    }

    @FXML
    private void register() throws IOException {
        BibliotecaDigital.setRoot("register");
    }
}
