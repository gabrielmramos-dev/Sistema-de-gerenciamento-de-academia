-- Criação das tabelas para o Sistema de Gerenciamento de Academia

CREATE TABLE IF NOT EXISTS plano (
    id SERIAL PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    descricao TEXT,
    valor_mensal DECIMAL(10, 2) NOT NULL,
    duracao_meses INT NOT NULL,
    beneficios TEXT
);

CREATE TABLE IF NOT EXISTS instrutor (
    id SERIAL PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    cpf VARCHAR(14) UNIQUE NOT NULL,
    telefone VARCHAR(20),
    especialidade VARCHAR(100),
    horarios_trabalho TEXT
);

CREATE TABLE IF NOT EXISTS aluno (
    id SERIAL PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    cpf VARCHAR(14) UNIQUE NOT NULL,
    telefone VARCHAR(20),
    email VARCHAR(100),
    data_nascimento DATE,
    data_matricula DATE DEFAULT CURRENT_DATE,
    id_plano INT REFERENCES plano(id)
);

CREATE TABLE IF NOT EXISTS aula (
    id SERIAL PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    descricao TEXT,
    capacidade_maxima INT NOT NULL,
    horario TIMESTAMP NOT NULL,
    duracao_minutos INT NOT NULL,
    id_instrutor INT REFERENCES instrutor(id)
);

CREATE TABLE IF NOT EXISTS frequencia (
    id SERIAL PRIMARY KEY,
    id_aluno INT REFERENCES aluno(id),
    data_hora_entrada TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS inscricao_aula (
    id_aluno INT REFERENCES aluno(id) ON DELETE CASCADE,
    id_aula INT REFERENCES aula(id) ON DELETE CASCADE,
    data_inscricao TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (id_aluno, id_aula)
);
