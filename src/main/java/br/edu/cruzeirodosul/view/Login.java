package br.edu.cruzeirodosul.view;

import java.io.IOException;

import br.edu.cruzeirodosul.BibliotecaDigital;
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

        try {
            Connection c = DriverManager.getConnection(
                    "jdbc:mysql://" + host + ":" + porta + "/" + bd,
                    usuario,
                    senha
            );
            return c;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        
        // try (Connection conexao = DriverManager.getConnection("jdbc:mysql://localhost:3306/sua_biblioteca\", \"seu_user\", \"sua_senha")) {
        //     PreparedStatement comando = conexao.preparedStatement(sql);

        //     comando.setString(1, nomeUsuario);
        //     comando.setString(2, senhaUsuario);

        //     ResultSet resultado = comando.executeQuery();

        //     if (resultado.next()) {
        //         App.setRoot("library");
        //     } else {
        //         System.out.println("Usuário ou senha inválidos!");
        //     }
        // } catch (SQLException e) {
        //     System.err.println("Erro de conexão com o banco de dados: " + e.getMessage());
        // }
        
    }

    @FXML
    private void register() throws IOException {
        BibliotecaDigital.setRoot("register");
    }
}
