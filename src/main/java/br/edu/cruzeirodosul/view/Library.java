package br.edu.cruzeirodosul.view;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import br.edu.cruzeirodosul.model.ConnectionFactory;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;

public class Library {
    
    @FXML
    private TilePane tpPrateleira;

    // Este método roda sozinho assim que a tela da biblioteca é carregada
    @FXML
    public void initialize() {
        carregarLivrosDoBanco();
    }

    private void carregarLivrosDoBanco() {
        String sql = "SELECT * FROM livros";
        ConnectionFactory fabrica = new ConnectionFactory();
        
        try (Connection conexao = fabrica.obtemConexao();
             PreparedStatement comando = conexao.prepareStatement(sql);
             ResultSet resultado = comando.executeQuery()) {

            // Para cada livro encontrado no MySQL...
            while (resultado.next()) {
                
                // 1. Cria o esqueleto do livro (VBox) na memória
                VBox caixaLivro = new VBox();
                caixaLivro.setPrefSize(120, 180);

                // 2. Pega o nome do livro no banco e cria o texto (Label)
                // (Lembre-se de mudar "titulo" para o nome exato da coluna na sua tabela)
                String nomeNoBanco = resultado.getString("titulo");
                Label tituloLivro = new Label(nomeNoBanco);

                // 3. Monta o lego: põe o texto no VBox, e o VBox na prateleira
                caixaLivro.getChildren().add(tituloLivro);
                tpPrateleira.getChildren().add(caixaLivro);
            }

        } catch (Exception e) {
            System.err.println("Erro ao buscar livros: " + e.getMessage());
        }
    }
}