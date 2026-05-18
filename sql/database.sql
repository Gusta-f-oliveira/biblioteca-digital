-- Criação do Banco de Dados "Biblioteca"
CREATE DATABASE biblioteca;

-- Selecionar Banco de Dados "Biblioteca" para começar a inserir os dados
USE biblioteca;

-- Tabela necessária para realizar login/cadastro
CREATE TABLE usuarios (
    id_usuario INT PRIMARY KEY AUTO_INCREMENT,
    nome VARCHAR(40) NOT NULL,
    email VARCHAR(100) NOT NULL,
    telefone VARCHAR(20) NOT NULL,
    senha VARCHAR(40) NOT NULL,
    tipo_usuario ENUM('COMUM', 'BIBLIOTECARIO') DEFAULT 'COMUM'
);

-- Tabela de livros
CREATE TABLE livros (
    id_livro INT PRIMARY KEY AUTO_INCREMENT,
    titulo VARCHAR(100) NOT NULL,
    autor VARCHAR(100),
    caminho_imagem VARCHAR(255) -- Para futuramente carregar a capa do livro no Scene Builder
);

-- Tabela para gerenciar a lista de favoritos
CREATE TABLE favoritos (
    id_usuario INT,
    id_livro INT,
    PRIMARY KEY (id_usuario, id_livro),
    FOREIGN KEY (id_usuario) REFERENCES usuarios(id_usuario),
    FOREIGN KEY (id_livro) REFERENCES livros(id_livro)
);

-- Inserção dos livros para a biblioteca
INSERT INTO livros (titulo, autor, capa) VALUES 
('O Senhor dos Anéis', 'J.R.R. Tolkien', 'senhor-dos-aneis.jpg'),
('Duna', 'Frank Herbert', 'duna.jpg'),
('Fundação', 'Isaac Asimov', 'fundacao.jpg'),
('Neuromancer', 'William Gibson', 'neuromancer.jpg');

-- Visualizar a tabela livros
SELECT * FROM livros;