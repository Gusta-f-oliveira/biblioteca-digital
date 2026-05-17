package br.edu.cruzeirodosul.view;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import br.edu.cruzeirodosul.BibliotecaDigital;
import br.edu.cruzeirodosul.model.ConnectionFactory;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class Register {

    @FXML
    private TextField txtNome;

    @FXML
    private TextField txtEmail;

    @FXML
    private TextField txtTel;

    @FXML
    private PasswordField pfSenhaOculta;

    @FXML
    private TextField txtSenhaVisivel;

    @FXML
    private PasswordField pfConfirmaOculta;

    @FXML
    private TextField txtConfirmaVisivel;

    @FXML
    private CheckBox chkMostrarSenha;

    @FXML
    private CheckBox chkMostrarConfirma;
    
    @FXML
    private ComboBox<String> cbTipo;

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

    @FXML
    private void mostrarConfirma() throws IOException {
        if (chkMostrarConfirma.isSelected()) {
            txtConfirmaVisivel.setText(pfConfirmaOculta.getText());
            pfConfirmaOculta.setVisible(false);
            txtConfirmaVisivel.setVisible(true);
        } else {
            pfConfirmaOculta.setText(txtConfirmaVisivel.getText());
            pfConfirmaOculta.setVisible(true);
            txtConfirmaVisivel.setVisible(false);
        }
    }
    
    @FXML
    private void telaLogin() throws IOException {
        BibliotecaDigital.setRoot("login");
    }

    @FXML
    private void cadastrarUsuario() throws IOException {
        pfSenhaOculta.setVisible(true);
        pfConfirmaOculta.setVisible(true);
        
        String nome = txtNome.getText();
        String email = txtEmail.getText();
        String tel = txtTel.getText();
        String senha = pfSenhaOculta.getText();
        String confirmaSenha = pfConfirmaOculta.getText();
        String tipo = cbTipo.getValue(); 

        // 1. VERIFICAÇÃO DE SENHA
        if (!senha.equals(confirmaSenha)) {
            System.out.println("Erro: As senhas não coincidem!");
            return; 
        }

        // 2. VERIFICAÇÃO DO TIPO
        if (tipo == null) {
            System.out.println("Erro: Selecione o tipo de usuário (COMUM ou BIBLIOTECARIO)!");
            return;
        }

        // Se passar nas verificações, faz o INSERT no banco de dados
        String sql = "INSERT INTO usuarios (nome, email, telefone, senha, tipo_usuario) VALUES (?, ?, ?, ?, ?)";
        ConnectionFactory fabrica = new ConnectionFactory();

        try (Connection conexao = fabrica.obtemConexao();
             PreparedStatement comando = conexao.prepareStatement(sql)) {

            comando.setString(1, nome);
            comando.setString(2, email);
            comando.setString(3, tel);
            comando.setString(4, senha);
            comando.setString(5, tipo);
            comando.executeUpdate();

            System.out.println("Usuário cadastrado com sucesso!");

            // Após cadastrar, volta para a tela de login
            BibliotecaDigital.setRoot("login");

        } catch (SQLException e) {
            System.err.println("Erro ao cadastrar: " + e.getMessage());
        }
    }

    @FXML
    public void initialize() {
        cbTipo.getItems().addAll("COMUM", "BIBLIOTECÁRIO");
    }
    
}