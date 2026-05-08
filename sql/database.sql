CREATE DATABASE biblioteca;
USE biblioteca;

CREATE TABLE usuarios (
    id_usuario INT PRIMARY KEY AUTO_INCREMENT,
    nome VARCHAR(40)
);

CREATE TABLE bibliotecarios (
    id_bibliotecario INT PRIMARY KEY AUTO_INCREMENT,
    nome VARCHAR(40)
);

SELECT * FROM usuarios;
SELECT * FROM bibliotecarios;