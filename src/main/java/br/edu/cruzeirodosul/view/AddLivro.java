package br.edu.cruzeirodosul.view;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import br.edu.cruzeirodosul.model.ConnectionFactory;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;

public class AddLivro {
    // Atributos
    @FXML
    private TextField txtTitulo;
    
    @FXML
    private TextField txtAutor;
    
    @FXML
    private TextField txtCapa;
    
    // Guarda o arquivo temporariamente entre um clique e outro
    private File capaSelecionada; 

    // Métodos
    // Seleção da capa para o livro
    @FXML
    private void escolherImagem() {
        FileChooser seletor = new FileChooser();
        seletor.setTitle("Selecione a Capa do Livro");
        seletor.getExtensionFilters().add(
            new FileChooser.ExtensionFilter("Imagens", "*.png", "*.jpg", "*.jpeg")
        );

        // Abre a janela e guarda o arquivo na variável global
        capaSelecionada = seletor.showOpenDialog(null);

        if (capaSelecionada != null) {
            txtCapa.setText(capaSelecionada.getAbsolutePath());
        }
    }

    // Enviar novo livro para a biblioteca
    @FXML
    private void salvarLivro() {
        String titulo = txtTitulo.getText();
        String autor = txtAutor.getText();

        if (titulo.isEmpty() || capaSelecionada == null) {
            System.out.println("Erro: Preencha o título e selecione uma imagem!");
            return;
        }

        // 1. Cópia da capa do livro para o Banco de Dados
        try {
            // Define o caminho para onde a imagem vai ser copiada
            Path pastaDestino = Paths.get("src/main/resources/imagens/" + capaSelecionada.getName());
            
            // Copia o arquivo do PC do usuário para a pasta do projeto
            Files.copy(capaSelecionada.toPath(), pastaDestino, StandardCopyOption.REPLACE_EXISTING);
            
        } catch (IOException e) {
            System.out.println("Erro ao copiar a imagem: " + e.getMessage());
            return; // Se falhar a cópia, cancela o salvamento no banco
        }

        // 2. Salva as informações sobre o livro no Banco de Dados
        String sql = "INSERT INTO livros (titulo, autor, caminho_imagem) VALUES (?, ?, ?)";
        ConnectionFactory fabrica = new ConnectionFactory();

        try (Connection conexao = fabrica.obtemConexao();
             PreparedStatement comando = conexao.prepareStatement(sql)) {

            comando.setString(1, titulo);
            comando.setString(2, autor);
            comando.setString(3, capaSelecionada.getName()); // Salva só o nome do arquivo, ex: "capa.jpg"
            comando.executeUpdate();

            System.out.println("Livro salvo com sucesso!");
            
            // Fecha a janela após enviar o livro para o Banco de Dados
            javafx.stage.Stage janelaAtual = (javafx.stage.Stage) txtTitulo.getScene().getWindow();
            janelaAtual.close();

        } catch (SQLException e) {
            System.err.println("Erro ao salvar no banco: " + e.getMessage());
        }
    }
}