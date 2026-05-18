package br.edu.cruzeirodosul.model;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionFactory {
    // Conecta a bibioteca com o Banco de Dados
    public Connection obtemConexao() {
        Properties props = new Properties();
        
        try (InputStream is = getClass().getClassLoader().getResourceAsStream("config.properties")) {
            if (is == null) {
                throw new IOException("Arquivo config.properties não encontrado!");
            }
            
            props.load(is);
            
            return DriverManager.getConnection(
                props.getProperty("db.url"),
                props.getProperty("db.user"),
                props.getProperty("db.password")
            );
            
        } catch (IOException | SQLException e) {
            e.printStackTrace();
            return null;
        }
    }
}
