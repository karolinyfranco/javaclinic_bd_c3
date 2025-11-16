CREATE DATABASE IF NOT EXISTS javaclinic;
USE javaclinic;

CREATE TABLE medico (
    crm VARCHAR(10) PRIMARY KEY NOT NULL,
    nome VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL,
    especialidade VARCHAR(50) NOT NULL,
    telefone VARCHAR(15) NOT NULL,
    endereco VARCHAR(150) NOT NULL
);

CREATE TABLE paciente (
    cpf VARCHAR(14) PRIMARY KEY NOT NULL,
    nome VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL,
    telefone VARCHAR(15) NOT NULL,
    endereco VARCHAR(150) NOT NULL
);

CREATE TABLE consulta (
    id_consulta INT AUTO_INCREMENT PRIMARY KEY NOT NULL,
    cpf_paciente VARCHAR(14) NOT NULL,
    crm_medico VARCHAR(10) NOT NULL,
    especialidade VARCHAR(50) NOT NULL,
    data DATETIME NOT NULL,
    FOREIGN KEY (cpf_paciente) REFERENCES paciente(cpf),
    FOREIGN KEY (crm_medico) REFERENCES medico(crm)
);