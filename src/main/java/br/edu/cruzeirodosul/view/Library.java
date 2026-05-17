package br.edu.cruzeirodosul.view;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import br.edu.cruzeirodosul.model.ConnectionFactory;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
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
                
                // 1. Cria a caixa do livro e centraliza os itens dentro dela
                VBox caixaLivro = new VBox();
                caixaLivro.setPrefSize(120, 180);
                caixaLivro.setAlignment(javafx.geometry.Pos.CENTER); // Centraliza a capa e o título
                caixaLivro.setSpacing(5); // Dá um pequeno espaço de 5 pixels entre a imagem e o texto

                // 2. Pega os dados do banco
                String nomeNoBanco = resultado.getString("titulo");
                String arquivoImagem = resultado.getString("caminho_imagem");

                Label tituloLivro = new Label(nomeNoBanco);
                tituloLivro.setStyle("-fx-text-fill: white; -fx-font-weight: bold;"); // Deixa o texto branco para destacar no fundo marrom

                // 3. Monta a imagem da capa
                ImageView capaView = new ImageView();

                // Verifica se o banco de dados tem alguma imagem cadastrada para este livro
                if (arquivoImagem != null && !arquivoImagem.isEmpty()) {
                    try {
                        // Busca a imagem dentro da pasta resources/imagens/
                        String caminhoCompleto = "/imagens/" + arquivoImagem;
                        javafx.scene.image.Image capa = new javafx.scene.image.Image(getClass().getResourceAsStream(caminhoCompleto));
                        
                        capaView.setImage(capa);
                        capaView.setFitWidth(100);  // Deixa a imagem um pouco menor que o VBox para dar margem
                        capaView.setFitHeight(140);
                        capaView.setPreserveRatio(true); // Evita que a capa fique esticada/deformada
                    } catch (Exception e) {
                        System.out.println("Não foi possível carregar a imagem: " + arquivoImagem);
                    }
                }

                // 4. Monta o lego: Adiciona PRIMEIRO a imagem, DEPOIS o título, e joga na prateleira
                caixaLivro.getChildren().addAll(capaView, tituloLivro);
                tpPrateleira.getChildren().add(caixaLivro);
            }

        } catch (Exception e) {
            System.err.println("Erro ao buscar livros: " + e.getMessage());
        }
    }
}