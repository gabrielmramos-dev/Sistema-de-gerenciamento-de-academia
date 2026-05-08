# Sistema de Gerenciamento de Academia

## Integrantes do Grupo
- Gabriel Macena Ramos - 45157286
- Leonardo Vitale dos Santos - 45607028
- Juliann Nicolas Oliveira Foster de Moraes - 44705280
- Carlos Henryque de Melo Alcantara - 45711402

## Tema Escolhido
Academia

## Objetivo do Sistema
O Sistema de Gerenciamento de Academia tem como objetivo automatizar e centralizar as operações de uma academia de ginástica, facilitando o trabalho dos funcionários da recepção e da gerência.

O sistema permite o controle completo de alunos, instrutores, funcionários, planos de assinatura e aulas coletivas.

---

## CP2 — Herança e Polimorfismo

### Hierarquia de Classes implementada

```
Pessoa (superclasse)
├── Aluno      (subclasse)
├── Instrutor  (subclasse)
└── Funcionario (subclasse) ← novo no CP2
```

`Pessoa` centraliza os atributos comuns (`id`, `nome`, `cpf`, `telefone`) e define os métodos `exibirInfo()` e `calcularCustoMensal()`, que são sobrescritos por cada subclasse.

### Requisitos atendidos

#### 1. Herança (`extends` + `super`)
- `Aluno`, `Instrutor` e `Funcionario` estendem `Pessoa`
- Todos os construtores chamam `super(id, nome, cpf, telefone)` para inicializar os atributos herdados
- Os atributos comuns foram movidos para `Pessoa` com visibilidade `protected`

#### 2. Sobrescrita de Métodos (`@Override`)
Cada subclasse sobrescreve dois métodos de `Pessoa`:

| Método | Aluno | Instrutor | Funcionario |
|--------|-------|-----------|-------------|
| `exibirInfo()` | Chama `super.exibirInfo()` e adiciona email, plano, vencimento | Chama `super.exibirInfo()` e adiciona especialidade, horários, salário | Chama `super.exibirInfo()` e adiciona cargo, turno, salário |
| `calcularCustoMensal()` | Retorna valor do plano | Retorna salário base | Retorna salário |

#### 3. Polimorfismo — `ArrayList<Pessoa>`
No menu opção **11**, o sistema demonstra polimorfismo real:
```java
ArrayList<Pessoa> pessoas = new ArrayList<>();
pessoas.add(new Aluno(...));
pessoas.add(new Instrutor(...));
pessoas.add(new Funcionario(...));

for (Pessoa p : pessoas) {
    p.exibirInfo();          // chama versão específica de cada subclasse
    p.calcularCustoMensal(); // idem
}
```

#### 4. Sobrecarga de Métodos (`calcularMensalidade` em `Aluno`)
Quatro versões do método, demonstradas no menu opção **12**:

```java
calcularMensalidade()                        // valor base do plano
calcularMensalidade(double percentual)        // desconto percentual (0.0 a 1.0)
calcularMensalidade(double desconto, double taxa) // desconto fixo + taxa adicional
calcularMensalidade(String promocao)         // "ESTUDANTE" | "SENIOR" | "CONVENIO"
```

---

## Funcionalidades do Sistema (CP1 + CP2)

1. Cadastrar, atualizar e excluir alunos
2. Cadastrar, atualizar e excluir instrutores
3. Cadastrar e gerenciar planos de assinatura
4. Cadastrar e gerenciar aulas coletivas
5. Cadastrar e gerenciar funcionários administrativos *(novo CP2)*
6. Inscrever/cancelar aluno em aulas
7. Registrar frequência de alunos
8. Demonstrar polimorfismo com `ArrayList<Pessoa>` *(novo CP2)*
9. Demonstrar sobrecarga de `calcularMensalidade` *(novo CP2)*

## Estrutura de Arquivos

```
cp2/
└── src/
    ├── Pessoa.java        ← superclasse (novo CP2)
    ├── Aluno.java         ← subclasse (refatorado CP2)
    ├── Instrutor.java     ← subclasse (refatorado CP2)
    ├── Funcionario.java   ← subclasse (novo CP2)
    ├── Plano.java         ← sem alterações do CP1
    ├── Aula.java          ← sem alterações do CP1
    ├── Frequencia.java    ← sem alterações do CP1
    └── SistemaMain.java   ← menu expandido com opções CP2
```
