package br.edu.cruzeirodosul.view;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import br.edu.cruzeirodosul.BibliotecaDigital;
import br.edu.cruzeirodosul.model.ConnectionFactory;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class Login {
    // Atributos
    @FXML
    private TextField txtNome;

    @FXML
    private PasswordField pfSenhaOculta;

    @FXML
    private TextField txtSenhaVisivel;

    @FXML
    private CheckBox chkMostrarSenha;

    // Métodos
    // Botão exibir/ocultar senha
    @FXML
    private void mostrarSenha() throws  IOException {
        if (chkMostrarSenha.isSelected()) {
            txtSenhaVisivel.setText(pfSenhaOculta.getText());
            pfSenhaOculta.setVisible(false);
            txtSenhaVisivel.setVisible(true);
        } else {
            pfSenhaOculta.setText(txtSenhaVisivel.getText());
            pfSenhaOculta.setVisible(true);
            txtSenhaVisivel.setVisible(false);
        }
    }

    // Botão de fazer login
    @FXML
    private void login() throws IOException {
        // Lê o campo da senha invisível. NOTA: essencial para não bugar ao tentar fazer login
        pfSenhaOculta.setVisible(true);
        
        String nomeUsuario = txtNome.getText();
        String senhaUsuario = pfSenhaOculta.getText();

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
                // Salva o tipo na variável global
                br.edu.cruzeirodosul.model.Sessao.tipoUsuarioLogado = resultado.getString("tipo_usuario");
                
                // Exibe a biblioteca após login bem sucessedido
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
