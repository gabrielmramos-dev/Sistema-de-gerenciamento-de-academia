# Sistema de Gerenciamento de Academia

Este projeto consiste em um sistema de gerenciamento para academias de ginástica, integrando conceitos avançados de Programação Orientada a Objetos (POO) em Java e persistência de dados com PostgreSQL.

## Integrantes do Grupo
- Gabriel Macena Ramos - 45157286
- Leonardo Vitale dos Santos - 45607028
- Juliann Nicolas Oliveira Foster de Moraes - 44705280
- Carlos Henryque de Melo Alcantara - 45711402

---

## Checkpoint 3 - Classes Abstratas + DAO + JDBC

Nesta fase do projeto, a aplicação foi reestruturada para uma arquitetura em camadas, garantindo a separação de responsabilidades e a persistência real dos dados.

### 1. Classes Abstratas (POO Avançada)
A classe Pessoa foi redefinida como abstract:
- Justificativa: Pessoa representa um conceito genérico e não deve ser instanciada diretamente.
- Métodos Abstratos: exibirDetalhes() e calcularCustoMensal() agora são abstratos, obrigando as subclasses (Aluno, Instrutor, Funcionario) a implementarem suas lógicas específicas.
- Atributos Protected: Atributos como id, nome, cpf e telefone possuem visibilidade protegida para acesso direto pelas subclasses.

### 2. Padrão DAO (Data Access Object)
Implementação da camada de persistência para isolar o acesso ao banco de dados:
- ConexaoBD: Gerenciamento de conexão JDBC com PostgreSQL.
- DAOs Implementados: AlunoDAO, PlanoDAO, InstrutorDAO, AulaDAO, FrequenciaDAO e InscricaoDAO.
- Segurança: Utilização de PreparedStatement em todas as consultas para prevenção de SQL Injection.

### 3. Regra de Negócio Complexa
O sistema realiza o controle de inscrições em aulas coletivas através das seguintes validações:
- Verificação de Plano: Bloqueio de inscrição caso o plano do aluno esteja vencido (calculado com base na data de matrícula e duração do plano).
- Controle de Capacidade: Restrição de novas inscrições caso a aula atinja a capacidade máxima definida.
- Conflito de Horário: Verificação de duplicidade de horários para o mesmo aluno no momento da inscrição.
- Estatísticas do Aluno: Exibição dinâmica de dados como total de visitas e aulas inscritas através de consultas SQL.

---

## Estrutura de Pacotes (src/)
- model/: Classes de domínio e entidades.
- dao/: Camada de acesso a dados e configuração JDBC.
- util/: Classes utilitárias para validação de dados.
- Main.java: Interface de linha de comando para interação com o usuário.

---

## Instruções para Execução
1. Banco de Dados: Criar o banco de dados 'academia_db' no PostgreSQL e executar o script 'init.sql'.
2. Driver JDBC: O arquivo 'postgresql-42.7.3.jar' deve estar presente no diretório para permitir a conexão.
3. Compilação:
   javac -d bin -sourcepath src src/Main.java
4. Execução:
   java -cp "bin;postgresql-42.7.3.jar" Main

---

## Requisitos CP3 Atendidos
- Criação de classe abstrata com métodos abstratos e concretos.
- Implementação do padrão DAO com operações de CRUD completo.
- Conexão e persistência em banco de dados PostgreSQL via JDBC.
- Organização do projeto em pacotes (model, dao, util).
- Implementação de regras de negócio complexas integradas ao banco.
