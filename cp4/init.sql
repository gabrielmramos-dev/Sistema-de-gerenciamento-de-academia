-- ============================================================
-- Sistema de Gerenciamento de Academia — CP4
-- Script de criação de tabelas e dados de teste
-- ============================================================

-- Criação das tabelas (idempotente com IF NOT EXISTS)

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
    id_aluno INT REFERENCES aluno(id) ON DELETE CASCADE,
    data_hora_entrada TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS inscricao_aula (
    id_aluno INT REFERENCES aluno(id) ON DELETE CASCADE,
    id_aula INT REFERENCES aula(id) ON DELETE CASCADE,
    data_inscricao TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (id_aluno, id_aula)
);

-- ============================================================
-- DADOS DE TESTE
-- ============================================================

-- Planos
INSERT INTO plano (nome, descricao, valor_mensal, duracao_meses, beneficios) VALUES
('Básico',    'Acesso à musculação',              99.90,  1, 'Musculação livre'),
('Intermediário', 'Acesso completo + 2 aulas',   149.90, 3, 'Musculação + 2 aulas coletivas/semana'),
('Premium',   'Acesso total ilimitado',           199.90, 6, 'Musculação + aulas ilimitadas + avaliação física');

-- Instrutores
INSERT INTO instrutor (nome, cpf, telefone, especialidade, horarios_trabalho) VALUES
('Carlos Silva',   '12345678901', '11-91111-1111', 'Musculação e Hipertrofia', 'Seg-Sex 06h-14h'),
('Ana Pereira',    '23456789012', '11-92222-2222', 'Yoga e Pilates',           'Seg-Qua-Sex 08h-12h'),
('Roberto Costa',  '34567890123', '11-93333-3333', 'Spinning e Cardio',        'Ter-Qui 17h-21h');

-- Alunos
INSERT INTO aluno (nome, cpf, telefone, email, data_nascimento, data_matricula, id_plano) VALUES
('João Santos',    '45678901234', '11-94444-4444', 'joao@email.com',   '1995-03-15', CURRENT_DATE - INTERVAL '60 days',  2),
('Maria Lima',     '56789012345', '11-95555-5555', 'maria@email.com',  '1998-07-22', CURRENT_DATE - INTERVAL '10 days',  3),
('Pedro Oliveira', '67890123456', '11-96666-6666', 'pedro@email.com',  '1990-11-05', CURRENT_DATE - INTERVAL '200 days', 1),
('Luisa Mendes',   '78901234567', '11-97777-7777', 'luisa@email.com',  '2000-01-30', CURRENT_DATE,                       3);

-- Aulas
INSERT INTO aula (nome, descricao, capacidade_maxima, horario, duracao_minutos, id_instrutor) VALUES
('Yoga Matinal',   'Aula de yoga para iniciantes', 15, NOW() + INTERVAL '1 day 08:00:00',  60, 2),
('Spinning Noturno','Alta intensidade cardio',      20, NOW() + INTERVAL '1 day 19:00:00',  45, 3),
('Pilates Core',   'Foco em core e equilíbrio',    12, NOW() + INTERVAL '2 days 10:00:00', 55, 2);

-- Inscrições de exemplo
INSERT INTO inscricao_aula (id_aluno, id_aula) VALUES
(1, 1),  -- João inscrito em Yoga Matinal
(2, 1),  -- Maria inscrita em Yoga Matinal
(2, 2),  -- Maria inscrita em Spinning Noturno
(4, 3);  -- Luisa inscrita em Pilates Core

-- Frequências de exemplo
INSERT INTO frequencia (id_aluno, data_hora_entrada) VALUES
(1, CURRENT_TIMESTAMP - INTERVAL '5 days'),
(1, CURRENT_TIMESTAMP - INTERVAL '3 days'),
(1, CURRENT_TIMESTAMP - INTERVAL '1 day'),
(2, CURRENT_TIMESTAMP - INTERVAL '2 days'),
(3, CURRENT_TIMESTAMP - INTERVAL '7 days');
