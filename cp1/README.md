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

O sistema permite o controle completo de alunos, instrutores, planos de assinatura e aulas coletivas. Com ele, é possível cadastrar e atualizar informações, gerenciar inscrições em aulas e acompanhar a frequência dos alunos de forma prática.

Além disso, o sistema aplica regras de negócio importantes, como verificação de plano ativo, controle de capacidade de aulas e detecção de conflitos de horário, garantindo que as operações da academia ocorram com organização e segurança.

## Funcionalidades Principais
1. Cadastrar, atualizar e excluir alunos
2. Cadastrar, atualizar e excluir instrutores
3. Cadastrar e gerenciar planos de assinatura
4. Cadastrar e gerenciar aulas coletivas
5. Inscrever/cancelar aluno em aulas (com validação de plano, capacidade e conflito de horário)
6. Registrar frequência (entrada) de alunos na academia
7. Visualizar detalhes de aluno (plano, aulas inscritas, histórico de frequência)
8. Listar aulas disponíveis por horário ou instrutor
9. Gerar relatório de frequência de um aluno em um período
10. Gerar relatório de ocupação das aulas

## Estrutura de Classes (Planejada)
- **Classe 1:** Aluno - Representa o aluno da academia com seus dados pessoais e plano ativo
- **Classe 2:** Instrutor - Representa o instrutor com especialidade e horários de trabalho
- **Classe 3:** Plano - Representa o plano de assinatura com valor, duração e benefícios
- **Classe 4:** Aula - Representa uma aula coletiva com capacidade, horário e instrutor responsável
- **Classe 5:** Frequencia - Registra a presença de um aluno na academia em data e hora específicas

## Regra de Negócio Complexa
**Controle de Inscrições em Aulas com Verificação de Plano:**
Ao inscrever um aluno em uma aula, o sistema verifica:
1. Se o plano do aluno está ativo (calcula a data de vencimento com base na data de matrícula + duração do plano em meses). Se estiver vencido, bloqueia a inscrição informando a data de vencimento.
2. Se a aula não atingiu a capacidade máxima. Se atingiu, bloqueia a inscrição informando a quantidade atual de inscritos.
3. Se o aluno já não está inscrito em outra aula no mesmo horário. Se houver conflito, bloqueia a inscrição informando qual aula conflita.
4. Se todas as condições forem atendidas, confirma a inscrição com sucesso.
