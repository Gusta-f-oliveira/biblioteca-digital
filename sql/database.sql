CREATE DATABASE biblioteca;
USE biblioteca;

-- Tabela unificada para facilitar o Login no Java
CREATE TABLE usuarios (
    id_usuario INT PRIMARY KEY AUTO_INCREMENT,
    nome VARCHAR(40) NOT NULL,
    senha VARCHAR(40) NOT NULL,
    tipo_usuario ENUM('COMUM', 'BIBLIOTECARIO') DEFAULT 'COMUM'
);

-- Tabela de livros (o seu Library.java faz "SELECT * FROM livros")
CREATE TABLE livros (
    id_livro INT PRIMARY KEY AUTO_INCREMENT,
    titulo VARCHAR(100) NOT NULL,
    autor VARCHAR(100),
    caminho_imagem VARCHAR(255) -- Para futuramente carregar a capa do livro no Scene Builder
);

-- Tabela para gerenciar a lista de favoritos (Relacionamento N para N)
CREATE TABLE favoritos (
    id_usuario INT,
    id_livro INT,
    PRIMARY KEY (id_usuario, id_livro),
    FOREIGN KEY (id_usuario) REFERENCES usuarios(id_usuario),
    FOREIGN KEY (id_livro) REFERENCES livros(id_livro)
);

INSERT INTO livros (titulo, autor) VALUES 
('O Senhor dos Anéis', 'J.R.R. Tolkien'),
('Duna', 'Frank Herbert'),
('Fundação', 'Isaac Asimov'),
('Neuromancer', 'William Gibson');

SELECT * FROM livros;