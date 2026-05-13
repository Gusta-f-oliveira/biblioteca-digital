package br.edu.cruzeirodosul;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * JavaFX App
 */
public class BibliotecaDigital extends Application {

    private static Scene scene;

    @Override
    public void start(Stage stage) throws IOException { 
        scene = new Scene(loadFXML("login"));
        stage.setScene(scene);
        stage.show();
    }

    public static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));

        // Pega a janela atual
        Stage janela = (Stage) scene.getWindow();
        
        // Manda a janela se reajustar ao tamanho do novo FXML
        janela.sizeToScene();
        
        // Centraliza a nova janela no meio do monitor
        janela.centerOnScreen();

        if (fxml.equals("library")) {
            // Maximiza a tela
            janela.setMaximized(true);
            
            // Mensagem de aviso no topo
            janela.setFullScreenExitHint("Pressione ESC para sair da estante");
        } else {
            // Garante que telas de login/cadastro não herdem o modo tela cheia
            janela.setFullScreen(false);
            janela.sizeToScene();
            janela.centerOnScreen();
        }
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(BibliotecaDigital.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static void main(String[] args) {
        launch();
    }

}