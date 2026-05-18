package br.edu.cruzeirodosul.view;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import br.edu.cruzeirodosul.BibliotecaDigital;
import br.edu.cruzeirodosul.model.ConnectionFactory;
import javafx.animation.TranslateTransition;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

public class Library {
    // Atributos
    @FXML
    private TilePane tpPrateleira;

    @FXML
    private Button btnMenu;

    @FXML
    private AnchorPane apMenuLateral;

    @FXML
    private Label lblUsuario;

    // Inicia com o menu suspenso fechado
    private boolean menuAberto = false;

    // Métodos
    @FXML
    public void initialize() {
        carregarLivrosDoBanco(); // Chama a função para preencher a estante a primeira vez

        // Lê a varável global: Se for COMUM, o botão de menu desaparece!
        if ("COMUM".equals(br.edu.cruzeirodosul.model.Sessao.tipoUsuarioLogado)) {
            btnMenu.setVisible(false);
        }
    }

    // Exibe os livros disponíveis na biblioteca
    private void carregarLivrosDoBanco() {
        // "Limpa" a estante antes de exibir os livros. NOTA: essencial para não duplicar os livros após inserir um novo livro
        tpPrateleira.getChildren().clear();

        // Pega os livros do Banco de Dados e exibe na biblioteca
        String sql = "SELECT * FROM livros";
        ConnectionFactory fabrica = new ConnectionFactory();
        
        // Tenta realizar a conexão com o Banco de Dados
        try (Connection conexao = fabrica.obtemConexao();
             PreparedStatement comando = conexao.prepareStatement(sql);
             ResultSet resultado = comando.executeQuery()) {

            // Para cada livro encontrado no Banco de Dados...
            while (resultado.next()) {
                
                // 1. Cria a caixa do livro
                VBox caixaLivro = new VBox();
                caixaLivro.setPrefSize(120, 180); // Tamanho da "caixa"
                caixaLivro.setAlignment(javafx.geometry.Pos.CENTER); // Centraliza a capa e o título
                caixaLivro.setSpacing(5); // Espaço de 5 pixels entre a imagem e o texto

                // 2. Pega as informações sobre o livro no Banco de Dados
                String nomeNoBanco = resultado.getString("titulo");
                String arquivoImagem = resultado.getString("capa");

                Label tituloLivro = new Label(nomeNoBanco);
                tituloLivro.setStyle("-fx-text-fill: white; -fx-font-weight: bold;"); // Deixa o texto branco para destacar no fundo marrom

                // 3. Monta a imagem da capa
                ImageView capaView = new ImageView();

                // Verifica se o Banco de Dados tem alguma imagem cadastrada para este livro
                if (arquivoImagem != null && !arquivoImagem.isEmpty()) {
                    try {
                        // Busca a imagem (capa do livro) dentro da pasta resources/imagens/
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

                // 4. Exibe o livro com as informações na biblioteca
                caixaLivro.getChildren().addAll(capaView, tituloLivro);
                tpPrateleira.getChildren().add(caixaLivro);
            }

        } catch (Exception e) {
            System.err.println("Erro ao buscar livros: " + e.getMessage());
        }
    }

    // Abre/fecha o menu suspenso
    @FXML
    private void alternarMenu() {        
        // Prepara a animação de deslizar do menu suspenso
        TranslateTransition deslizar = new TranslateTransition(Duration.seconds(0.3), apMenuLateral);

        if (menuAberto) {
            // Fecha o menu suspenso
            deslizar.setToX(-200);
            menuAberto = false;
        } else {
            // Abre menu suspenso
            deslizar.setToX(0);
            menuAberto = true;
        }
        
        // Executa a animação de deslizamento do menu lateral
        deslizar.play();
    }

    // Abre a janela para adicionar novo livro
    @FXML
    private void telaAdicionarLivro() throws IOException {
        // 1. Carrega o design da tela de adicionar livro sem apagar a biblioteca
        javafx.scene.Parent raiz = BibliotecaDigital.loadFXML("add_livro");

        // 2. Cria uma nova janela do zero
        javafx.stage.Stage janelaModal = new javafx.stage.Stage();
        janelaModal.setScene(new javafx.scene.Scene(raiz));
        janelaModal.setTitle("Adicionar Novo Livro");

        // 3. Define a janela como Modal (obriga o usuário a fechar ela antes de voltar a clicar na biblioteca)
        janelaModal.initModality(javafx.stage.Modality.APPLICATION_MODAL);

        // 4. Mostra a janela flutuante na tela
        janelaModal.showAndWait();
        carregarLivrosDoBanco();
    }
}