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

    @FXML private TextField txtTitulo;
    @FXML private TextField txtAutor;
    @FXML private TextField txtCaminhoImagem;

    // Esta variável guarda o arquivo temporariamente entre um clique e outro
    private File imagemSelecionada; 

    // Método para o botão "Selecionar Capa"
    @FXML
    private void escolherImagem() {
        FileChooser seletor = new FileChooser();
        seletor.setTitle("Selecione a Capa do Livro");
        seletor.getExtensionFilters().add(
            new FileChooser.ExtensionFilter("Imagens", "*.png", "*.jpg", "*.jpeg")
        );

        // Abre a janela e guarda o arquivo na nossa variável global
        imagemSelecionada = seletor.showOpenDialog(null);

        if (imagemSelecionada != null) {
            txtCaminhoImagem.setText(imagemSelecionada.getAbsolutePath());
        }
    }

    // Método para o botão "Salvar Livro"
    @FXML
    private void salvarLivro() {
        String titulo = txtTitulo.getText();
        String autor = txtAutor.getText();

        if (titulo.isEmpty() || imagemSelecionada == null) {
            System.out.println("Erro: Preencha o título e selecione uma imagem!");
            return;
        }

        // 1. CÓPIA FÍSICA DA IMAGEM
        try {
            // Define o caminho para onde a imagem vai ser copiada (a sua pasta resources)
            Path pastaDestino = Paths.get("src/main/resources/imagens/" + imagemSelecionada.getName());
            
            // Copia o arquivo do PC do usuário para a pasta do projeto
            Files.copy(imagemSelecionada.toPath(), pastaDestino, StandardCopyOption.REPLACE_EXISTING);
            
        } catch (IOException e) {
            System.out.println("Erro ao copiar a imagem: " + e.getMessage());
            return; // Se falhar a cópia, cancela o salvamento no banco
        }

        // 2. SALVAR NO BANCO DE DADOS
        String sql = "INSERT INTO livros (titulo, autor, caminho_imagem) VALUES (?, ?, ?)";
        ConnectionFactory fabrica = new ConnectionFactory();

        try (Connection conexao = fabrica.obtemConexao();
             PreparedStatement comando = conexao.prepareStatement(sql)) {

            comando.setString(1, titulo);
            comando.setString(2, autor);
            comando.setString(3, imagemSelecionada.getName()); // Salva só o nome do arquivo, ex: "capa.jpg"
            comando.executeUpdate();

            System.out.println("Livro salvo com sucesso!");
            
            // Pega a janela atual (onde está o txtTitulo) e fecha ela
            javafx.stage.Stage janelaAtual = (javafx.stage.Stage) txtTitulo.getScene().getWindow();
            janelaAtual.close();

        } catch (SQLException e) {
            System.err.println("Erro ao salvar no banco: " + e.getMessage());
        }
    }
}