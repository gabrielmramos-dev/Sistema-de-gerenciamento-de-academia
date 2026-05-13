# Sistema de Gerenciamento de Academia

> Projeto Integrado — Programacao Orientada a Objetos + Banco de Dados  
> Universidade Braz Cubas — CP4: Interfaces + Sistema Completo

---

## Equipe

| Nome | RA |
|------|----|
| Gabriel Macena Ramos | 45157286 |
| Leonardo Vitale dos Santos | 45607028 |
| Juliann Nicolas Oliveira Foster de Moraes | 44705280 |
| Carlos Henryque de Melo Alcantara | 45711402 |
| Pedro Maia | 45265844 |
| Gabriel Martins Reis | 45510938 |

---

## Sobre o Sistema

Sistema de linha de comando (CLI) em Java que gerencia uma academia de ginástica, integrando **POO** com **banco de dados relacional (PostgreSQL)** via JDBC.

**Funcionalidades:**
- Cadastro e gerenciamento de Planos, Alunos, Instrutores e Aulas
- Controle de frequência (registro de entradas)
- Inscrição em aulas com validação completa de regras de negócio
- Relatório consolidado com exportação para arquivo `.txt`
- Auditoria automática de ações (interface `Auditavel`)

---

## Video Demonstrativo

> Link do video: [Demonstração](https://www.youtube.com/watch?v=cqGaUFr7tq8)  

---

## Conceitos POO Aplicados

| Conceito | Onde | Checkpoint |
|----------|------|-----------|
| Encapsulamento | Atributos `private` + getters/setters em todas as classes `model/` | CP1 |
| Herança | `Pessoa` (abstrata) → `Aluno`, `Instrutor`, `Funcionario` | CP2 |
| Polimorfismo | `@Override exibirDetalhes()` e `calcularCustoMensal()` em cada subclasse | CP2 |
| Classe Abstrata | `Pessoa` nao pode ser instanciada diretamente | CP3 |
| Padrao DAO | Separacao entre acesso ao banco (`dao/`) e regras de negocio (`service/`) | CP3 |
| Interfaces | `Auditavel` e `Relatorio` como contratos de comportamento | **CP4** |
| Camada Service | `InscricaoService` centraliza as 5 validacoes da regra complexa | **CP4** |

---

## CP4 — Interfaces Implementadas

### `Auditavel` (util/Auditavel.java)

```java
public interface Auditavel {
    void registrarLog(String acao);
    String obterHistorico();
}
```

**Implementado por:** `Aluno` e `Instrutor`

Toda inscricao em aula, atualizacao de plano e registro de frequencia gera um log automatico.
O historico completo e exibido em **Menu Alunos → Detalhe**.

---

### `Relatorio` (util/Relatorio.java)

```java
public interface Relatorio {
    void gerarRelatorio();
    void exportarParaArquivo(String nomeArquivo);
}
```

**Implementado por:** `RelatorioService`

Gera relatorio consolidado de alunos, aulas e instrutores, com opcao de exportar para `.txt`.

---

## Regra de Negocio Complexa — Inscricao em Aulas

Implementada em `service/InscricaoService.java`. Executa **5 validacoes em cascata** antes de persistir:

| # | Validacao | Resultado se falhar |
|---|-----------|---------------------|
| 1 | Aluno existe no banco | [ERRO] Aluno nao encontrado |
| 2 | Plano do aluno esta ativo (nao vencido) | [ERRO] Plano vencido |
| 3 | Aula existe no banco | [ERRO] Aula nao encontrada |
| 4 | Aula tem vagas disponiveis | [ERRO] Capacidade maxima atingida |
| 5 | Sem conflito de horario com outra aula | [ERRO] Conflito de horario |

Somente apos todas as validacoes a inscricao e persistida e a acao e auditada via `Auditavel`.

---

## CRUD por Entidade

| Entidade | Create | Read | Update | Delete |
|----------|:------:|:----:|:------:|:------:|
| Planos | Sim | Sim | Sim | Sim |
| Alunos | Sim | Sim | Sim | Sim |
| Instrutores | Sim | Sim | Sim | Sim |
| Aulas | Sim | Sim | Sim | Sim |
| Frequencia | Sim | Sim | — | — |
| Inscricoes | Sim | Sim | — | Sim |

---

## Estrutura do Projeto

```
cp4/
├── config.properties          <- Credenciais do banco (nao vai ao Git)
├── config.properties.example  <- Modelo de configuracao
├── init.sql                   <- Script de criacao das tabelas e dados
└── src/
    ├── Main.java              <- Ponto de entrada / menus CLI
    ├── model/                 <- Entidades de dominio
    │   ├── Pessoa.java        <- Superclasse abstrata
    │   ├── Aluno.java         <- implements Auditavel (CP4)
    │   ├── Instrutor.java     <- implements Auditavel (CP4)
    │   ├── Funcionario.java   <- Heranca (demonstrativo)
    │   ├── Plano.java
    │   ├── Aula.java
    │   └── Frequencia.java
    ├── dao/                   <- Acesso ao banco via JDBC
    │   ├── ConexaoBD.java     <- Le credenciais do config.properties
    │   ├── AlunoDAO.java
    │   ├── PlanoDAO.java
    │   ├── InstrutorDAO.java
    │   ├── AulaDAO.java
    │   ├── FrequenciaDAO.java
    │   └── InscricaoDAO.java
    ├── service/               <- Regras de negocio (CP4)
    │   ├── InscricaoService.java  <- 5 validacoes de inscricao
    │   └── RelatorioService.java  <- implements Relatorio (CP4)
    └── util/                  <- Interfaces e utilitarios
        ├── Auditavel.java     <- Interface CP4
        ├── Relatorio.java     <- Interface CP4
        └── ValidadorUtil.java <- Validacao de CPF e email
```

---

## Como Executar

### Pre-requisitos

- Java 8 ou superior
- PostgreSQL instalado e rodando em `localhost:5432`
- Driver JDBC: [`postgresql-42.7.11.jar`](https://jdbc.postgresql.org/download/)

### 1. Configurar o banco

No pgAdmin ou psql, crie o banco e execute o script:

```sql
CREATE DATABASE academia_db;
```

Depois conecte ao `academia_db` e execute o `init.sql` (cria tabelas e dados de teste).

### 2. Configurar credenciais

Copie o arquivo de exemplo e edite com sua senha:

```
cp4/config.properties
```

```properties
db.url=jdbc:postgresql://localhost:5432/academia_db
db.user=postgres
db.password=SUA_SENHA
```

### 3. Compilar (dentro da pasta `cp4/`)

```cmd
javac -encoding UTF-8 -cp ".;..\postgresql-42.7.11.jar" -d out src\util\*.java src\model\*.java src\dao\*.java src\service\*.java src\Main.java
```

### 4. Rodar

```cmd
java -Dfile.encoding=UTF-8 -cp "out;..\postgresql-42.7.11.jar" Main
```

---

## Historico dos Checkpoints

| Checkpoint | Conteudo |
|-----------|----------|
| CP1 | Modelagem inicial, encapsulamento, getters/setters |
| CP2 | Heranca (`Pessoa` abstrata), polimorfismo, sobrecarga |
| CP3 | Integracao PostgreSQL via JDBC, padrao DAO |
| **CP4** | **Interfaces `Auditavel` e `Relatorio`, camada Service, CRUD completo** |
