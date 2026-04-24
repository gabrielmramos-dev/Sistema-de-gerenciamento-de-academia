import java.util.ArrayList;
import java.util.Scanner;

public class SistemaMain {

    private static Scanner scanner = new Scanner(System.in);
    private static ArrayList<Aluno> alunos = new ArrayList<>();
    private static ArrayList<Instrutor> instrutores = new ArrayList<>();
    private static ArrayList<Plano> planos = new ArrayList<>();
    private static ArrayList<Aula> aulas = new ArrayList<>();
    private static ArrayList<Funcionario> funcionarios = new ArrayList<>();

    public static void main(String[] args) {
        int opcao;
        do {
            exibirMenu();
            opcao = lerInteiro();
            switch (opcao) {
                case 1: cadastrarPlano(); break;
                case 2: listarPlanos(); break;
                case 3: cadastrarInstrutor(); break;
                case 4: listarInstrutores(); break;
                case 5: cadastrarAluno(); break;
                case 6: listarAlunos(); break;
                case 7: cadastrarAula(); break;
                case 8: listarAulas(); break;
                case 9: cadastrarFuncionario(); break;
                case 10: listarFuncionarios(); break;
                case 11: demonstrarPolimorfismo(); break;
                case 12: demonstrarSobrecarga(); break;
                case 0: System.out.println("Encerrando sistema..."); break;
                default: System.out.println("Opção inválida!");
            }
        } while (opcao != 0);
        scanner.close();
    }

    private static void exibirMenu() {
        System.out.println("\n=== SISTEMA DE GESTÃO DE ACADEMIA ===");
        System.out.println("--- Planos ---");
        System.out.println("1. Cadastrar Plano");
        System.out.println("2. Listar Planos");
        System.out.println("--- Instrutores ---");
        System.out.println("3. Cadastrar Instrutor");
        System.out.println("4. Listar Instrutores");
        System.out.println("--- Alunos ---");
        System.out.println("5. Cadastrar Aluno");
        System.out.println("6. Listar Alunos");
        System.out.println("--- Aulas ---");
        System.out.println("7. Cadastrar Aula");
        System.out.println("8. Listar Aulas");
        System.out.println("--- Funcionários ---");
        System.out.println("9. Cadastrar Funcionário");
        System.out.println("10. Listar Funcionários");
        System.out.println("--- CP2: Herança e Polimorfismo ---");
        System.out.println("11. Demonstrar Polimorfismo (ArrayList<Pessoa>)");
        System.out.println("12. Demonstrar Sobrecarga (calcularMensalidade)");
        System.out.println("0. Sair");
        System.out.print("Escolha uma opção: ");
    }

    // =========================================================
    // OPÇÕES DO CP1 (mantidas sem alteração)
    // =========================================================

    private static void cadastrarPlano() {
        System.out.println("\n--- CADASTRAR PLANO ---");
        System.out.print("Nome: ");
        String nome = scanner.nextLine();
        System.out.print("Descrição: ");
        String descricao = scanner.nextLine();
        System.out.print("Valor mensal: ");
        double valor = Double.parseDouble(scanner.nextLine().replace(",", "."));
        System.out.print("Duração em meses: ");
        int duracao = Integer.parseInt(scanner.nextLine());
        System.out.print("Benefícios: ");
        String beneficios = scanner.nextLine();

        Plano plano = new Plano(planos.size() + 1, nome, descricao, valor, duracao, beneficios);
        planos.add(plano);
        System.out.println("Plano cadastrado com sucesso!");
    }

    private static void listarPlanos() {
        System.out.println("\n--- LISTA DE PLANOS ---");
        if (planos.isEmpty()) {
            System.out.println("Nenhum plano cadastrado.");
        } else {
            for (Plano p : planos) System.out.println(p);
        }
    }

    private static void cadastrarInstrutor() {
        System.out.println("\n--- CADASTRAR INSTRUTOR ---");
        System.out.print("Nome: ");
        String nome = scanner.nextLine();
        System.out.print("CPF: ");
        String cpf = scanner.nextLine();
        System.out.print("Telefone: ");
        String telefone = scanner.nextLine();
        System.out.print("Especialidade: ");
        String especialidade = scanner.nextLine();
        System.out.print("Horários de trabalho: ");
        String horarios = scanner.nextLine();

        Instrutor instrutor = new Instrutor(instrutores.size() + 1, nome, cpf, telefone, especialidade, horarios);
        instrutores.add(instrutor);
        System.out.println("Instrutor cadastrado com sucesso!");
    }

    private static void listarInstrutores() {
        System.out.println("\n--- LISTA DE INSTRUTORES ---");
        if (instrutores.isEmpty()) {
            System.out.println("Nenhum instrutor cadastrado.");
        } else {
            for (Instrutor i : instrutores) System.out.println(i);
        }
    }

    private static void cadastrarAluno() {
        System.out.println("\n--- CADASTRAR ALUNO ---");
        if (planos.isEmpty()) {
            System.out.println("Cadastre um plano antes de cadastrar alunos.");
            return;
        }
        System.out.print("Nome: ");
        String nome = scanner.nextLine();
        System.out.print("CPF: ");
        String cpf = scanner.nextLine();
        System.out.print("Telefone: ");
        String telefone = scanner.nextLine();
        System.out.print("Email: ");
        String email = scanner.nextLine();

        listarPlanos();
        System.out.print("ID do plano: ");
        int idPlano = lerInteiro();
        Plano plano = null;
        for (Plano p : planos) {
            if (p.getId() == idPlano) { plano = p; break; }
        }
        if (plano == null) { System.out.println("Plano não encontrado."); return; }

        Aluno aluno = new Aluno(alunos.size() + 1, nome, cpf,
                java.time.LocalDate.of(2000, 1, 1),
                telefone, email,
                java.time.LocalDate.now(), plano);
        alunos.add(aluno);
        System.out.println("Aluno cadastrado com sucesso!");
    }

    private static void listarAlunos() {
        System.out.println("\n--- LISTA DE ALUNOS ---");
        if (alunos.isEmpty()) {
            System.out.println("Nenhum aluno cadastrado.");
        } else {
            for (Aluno a : alunos) System.out.println(a);
        }
    }

    private static void cadastrarAula() {
        System.out.println("\n--- CADASTRAR AULA ---");
        if (instrutores.isEmpty()) {
            System.out.println("Cadastre um instrutor antes de cadastrar aulas.");
            return;
        }
        System.out.print("Nome: ");
        String nome = scanner.nextLine();
        System.out.print("Descrição: ");
        String descricao = scanner.nextLine();
        System.out.print("Capacidade máxima: ");
        int capacidade = Integer.parseInt(scanner.nextLine());
        System.out.print("Horário (ex: Segunda 08:00): ");
        String horario = scanner.nextLine();
        System.out.print("Duração em minutos: ");
        int duracao = Integer.parseInt(scanner.nextLine());

        listarInstrutores();
        System.out.print("ID do instrutor: ");
        int idInstrutor = lerInteiro();
        Instrutor instrutor = null;
        for (Instrutor i : instrutores) {
            if (i.getId() == idInstrutor) { instrutor = i; break; }
        }
        if (instrutor == null) { System.out.println("Instrutor não encontrado."); return; }

        Aula aula = new Aula(aulas.size() + 1, nome, descricao, capacidade, horario, duracao, instrutor);
        aulas.add(aula);
        System.out.println("Aula cadastrada com sucesso!");
    }

    private static void listarAulas() {
        System.out.println("\n--- LISTA DE AULAS ---");
        if (aulas.isEmpty()) {
            System.out.println("Nenhuma aula cadastrada.");
        } else {
            for (Aula a : aulas) System.out.println(a);
        }
    }

    // =========================================================
    // OPÇÕES NOVAS DO CP2
    // =========================================================

    private static void cadastrarFuncionario() {
        System.out.println("\n--- CADASTRAR FUNCIONÁRIO ---");
        System.out.print("Nome: ");
        String nome = scanner.nextLine();
        System.out.print("CPF: ");
        String cpf = scanner.nextLine();
        System.out.print("Telefone: ");
        String telefone = scanner.nextLine();
        System.out.print("Cargo (ex: Recepcionista, Gerente): ");
        String cargo = scanner.nextLine();
        System.out.print("Turno (Manhã/Tarde/Noite): ");
        String turno = scanner.nextLine();
        System.out.print("Salário (R$): ");
        double salario = Double.parseDouble(scanner.nextLine().replace(",", "."));

        Funcionario f = new Funcionario(funcionarios.size() + 1, nome, cpf, telefone, cargo, turno, salario);
        funcionarios.add(f);
        System.out.println("Funcionário cadastrado com sucesso!");
    }

    private static void listarFuncionarios() {
        System.out.println("\n--- LISTA DE FUNCIONÁRIOS ---");
        if (funcionarios.isEmpty()) {
            System.out.println("Nenhum funcionário cadastrado.");
        } else {
            for (Funcionario f : funcionarios) System.out.println(f);
        }
    }

    /**
     * POLIMORFISMO: ArrayList<Pessoa> armazena Aluno, Instrutor e Funcionario.
     * O método correto de cada subclasse é chamado automaticamente.
     */
    private static void demonstrarPolimorfismo() {
        System.out.println("\n=== DEMONSTRAÇÃO DE POLIMORFISMO ===");
        System.out.println("ArrayList<Pessoa> armazenando todos os tipos:\n");

        // Cria dados de demonstração
        Plano planoDemo = new Plano(99, "Mensal Demo", "Plano demo", 120.00, 1, "Musculação");

        ArrayList<Pessoa> pessoas = new ArrayList<>();
        pessoas.add(new Aluno(101, "Ana Silva", "111.111.111-11",
                java.time.LocalDate.of(1998, 5, 20),
                "11 91111-1111", "ana@email.com",
                java.time.LocalDate.now(), planoDemo));
        pessoas.add(new Instrutor(102, "Carlos Lima", "222.222.222-22",
                "11 92222-2222", "Musculação", "Seg-Sex 06:00-14:00"));
        pessoas.add(new Funcionario(103, "Beatriz Souza", "333.333.333-33",
                "11 93333-3333", "Recepcionista", "Manhã", 2000.00));

        // Polimorfismo: itera sobre Pessoa, chama versão específica de cada subclasse
        for (Pessoa p : pessoas) {
            p.exibirInfo(); // @Override — chama a versão correta de cada classe
            System.out.printf(">> Custo mensal: R$%.2f%n", p.calcularCustoMensal());
            System.out.println("Tipo real: " + p.getClass().getSimpleName());
            System.out.println("-----------------------------");
        }
    }

    /**
     * SOBRECARGA: demonstra os 4 métodos calcularMensalidade() de Aluno.
     */
    private static void demonstrarSobrecarga() {
        System.out.println("\n=== DEMONSTRAÇÃO DE SOBRECARGA ===");
        System.out.println("Método calcularMensalidade() com diferentes parâmetros:\n");

        Plano plano = new Plano(99, "Premium", "Plano completo", 200.00, 12, "Tudo incluso");
        Aluno aluno = new Aluno(101, "João Demo", "000.000.000-00",
                java.time.LocalDate.of(2000, 1, 1),
                "11 90000-0000", "joao@email.com",
                java.time.LocalDate.now(), plano);

        System.out.println("Plano: " + plano.getNome() + " - R$" + plano.getValorMensal());
        System.out.println();
        // Sobrecarga 1: sem parâmetros
        System.out.printf("1. calcularMensalidade()                    → R$%.2f%n",
                aluno.calcularMensalidade());
        // Sobrecarga 2: percentual de desconto
        System.out.printf("2. calcularMensalidade(0.10)  [10%% desc]   → R$%.2f%n",
                aluno.calcularMensalidade(0.10));
        // Sobrecarga 3: desconto fixo + taxa
        System.out.printf("3. calcularMensalidade(30.0, 15.0) [R$-30+15] → R$%.2f%n",
                aluno.calcularMensalidade(30.0, 15.0));
        // Sobrecarga 4: plano promocional por nome
        System.out.printf("4. calcularMensalidade(\"ESTUDANTE\") [20%% desc] → R$%.2f%n",
                aluno.calcularMensalidade("ESTUDANTE"));
        System.out.printf("4. calcularMensalidade(\"SENIOR\")    [25%% desc] → R$%.2f%n",
                aluno.calcularMensalidade("SENIOR"));
        System.out.printf("4. calcularMensalidade(\"CONVENIO\")  [30%% desc] → R$%.2f%n",
                aluno.calcularMensalidade("CONVENIO"));
    }

    private static int lerInteiro() {
        try {
            return Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            return -1;
        }
    }
}
