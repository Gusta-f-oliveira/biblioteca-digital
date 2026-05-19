package br.edu.cruzeirodosul;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class BibliotecaDigital extends Application {

    private static Scene scene;

    // Exibe tela inicial (login) ao executar o programa
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
        } else {
            // Garante que telas de login e cadastro não herdem o modo tela cheia
            janela.setMaximized(false);
            janela.sizeToScene();
            janela.centerOnScreen();
        }
    }

    public static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(BibliotecaDigital.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static void main(String[] args) {
        launch();
    }

}