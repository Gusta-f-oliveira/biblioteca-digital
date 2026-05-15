package br.edu.cruzeirodosul.view;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import br.edu.cruzeirodosul.BibliotecaDigital;
import br.edu.cruzeirodosul.model.ConnectionFactory;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class Register {

    @FXML
    private TextField txtNome;

    @FXML
    private TextField txtSenha;

    @FXML
    private PasswordField pfConfirmaSenha;

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
        String confirmaSenha = pfConfirmaSenha.getText();
        String tipo = cbTipo.getValue(); 

        // 1. VERIFICAÇÃO DE SENHA: Se forem diferentes, interrompe o cadastro
        if (!senha.equals(confirmaSenha)) {
            System.out.println("Erro: As senhas não coincidem!");
            // Aqui futuramente você pode colocar um Label vermelho na tela para avisar o usuário
            return; 
        }

        // 2. VERIFICAÇÃO DO TIPO: Evita que o usuário esqueça de escolher no ComboBox
        if (tipo == null) {
            System.out.println("Erro: Selecione o tipo de usuário (COMUM ou BIBLIOTECARIO)!");
            return;
        }

        // Se passar nas verificações, faz o INSERT no banco
        String sql = "INSERT INTO usuarios (nome, senha, tipo_usuario) VALUES (?, ?, ?)";
        ConnectionFactory fabrica = new ConnectionFactory();

        try (Connection conexao = fabrica.obtemConexao();
             PreparedStatement comando = conexao.prepareStatement(sql)) {

            comando.setString(1, nome);
            comando.setString(2, senha);
            comando.setString(3, tipo);
            comando.executeUpdate();

            System.out.println("Usuário cadastrado com sucesso!");

            // Após cadastrar, volta para a tela de login
            BibliotecaDigital.setRoot("login");

        } catch (SQLException e) {
            System.err.println("Erro ao cadastrar: " + e.getMessage());
        }
    }
    
}