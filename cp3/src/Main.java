import dao.*;
import model.*;
import util.ValidadorUtil;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Scanner;

public class Main {
    private static Scanner sc = new Scanner(System.in);
    private static AlunoDAO alunoDAO = new AlunoDAO();
    private static PlanoDAO planoDAO = new PlanoDAO();
    private static InstrutorDAO instrutorDAO = new InstrutorDAO();
    private static AulaDAO aulaDAO = new AulaDAO();
    private static FrequenciaDAO frequenciaDAO = new FrequenciaDAO();
    private static InscricaoDAO inscricaoDAO = new InscricaoDAO();

    public static void main(String[] args) {
        int opcao = -1;
        do {
            System.out.println("\n========= SISTEMA DE ACADEMIA - CP3 =========");
            System.out.println("1. Planos | 2. Alunos | 3. Instrutores | 4. Aulas | 5. Frequência | 6. Inscrições | 0. Sair");
            System.out.print("Escolha uma opção: ");
            
            String entrada = sc.nextLine();
            if (entrada.trim().isEmpty()) continue;

            try {
                opcao = Integer.parseInt(entrada);
                switch (opcao) {
                    case 1 -> menuPlanos();
                    case 2 -> menuAlunos();
                    case 3 -> menuInstrutores();
                    case 4 -> menuAulas();
                    case 5 -> menuFrequencia();
                    case 6 -> menuInscricoes();
                    case 0 -> System.out.println("Saindo...");
                    default -> System.out.println("⚠️ Opção inválida!");
                }
            } catch (NumberFormatException e) {
                System.out.println("⚠️ Erro: Digite apenas números.");
            } catch (Exception e) {
                System.out.println("⚠️ Ocorreu um erro: " + e.getMessage());
            }
        } while (opcao != 0);
    }

    private static int lerInteiro() {
        while (true) {
            try {
                return Integer.parseInt(sc.nextLine());
            } catch (NumberFormatException e) {
                System.out.print("⚠️ Digite um número válido: ");
            }
        }
    }

    private static void menuPlanos() {
        System.out.println("\n--- Gerenciar Planos ---");
        System.out.println("1. Novo | 2. Listar | 3. Excluir | 0. Voltar");
        int op = lerInteiro();
        switch (op) {
            case 1 -> {
                System.out.print("Nome: "); String nome = sc.nextLine();
                System.out.print("Descrição: "); String desc = sc.nextLine();
                System.out.print("Valor Mensal: "); double valor = Double.parseDouble(sc.nextLine());
                System.out.print("Duração (meses): "); int duracao = lerInteiro();
                System.out.print("Benefícios: "); String ben = sc.nextLine();
                if (planoDAO.inserir(new Plano(0, nome, desc, valor, duracao, ben))) System.out.println("✅ Plano salvo!");
            }
            case 2 -> planoDAO.listarTodos().forEach(p -> System.out.println(p.getId() + " - " + p.getNome() + " (R$ " + p.getValorMensal() + ")"));
            case 3 -> {
                System.out.print("ID para excluir: ");
                int id = lerInteiro();
                if (planoDAO.excluir(id)) System.out.println("✅ Excluído!");
            }
        }
    }

    private static void menuAlunos() {
        System.out.println("\n--- Gerenciar Alunos ---");
        System.out.println("1. Novo | 2. Listar | 3. Ver Detalhe | 4. Excluir | 0. Voltar");
        int op = lerInteiro();
        switch (op) {
            case 1 -> {
                System.out.print("Nome: "); String nome = sc.nextLine();
                System.out.print("CPF (Apenas números): "); String cpf = sc.nextLine();
                if (!ValidadorUtil.validarCpf(cpf)) {
                    System.out.println("⚠️ CPF inválido! Tente novamente.");
                    return;
                }
                System.out.print("Telefone: "); String tel = sc.nextLine();
                System.out.print("Email: "); String email = sc.nextLine();
                System.out.print("Nascimento (AAAA-MM-DD): "); LocalDate nasc = LocalDate.parse(sc.nextLine());
                System.out.println("Planos disponíveis:");
                planoDAO.listarTodos().forEach(p -> System.out.println(p.getId() + ": " + p.getNome()));
                System.out.print("ID do Plano: "); int idPlano = lerInteiro();
                Plano p = planoDAO.buscarPorId(idPlano);
                if (alunoDAO.inserir(new Aluno(0, nome, cpf, tel, email, nasc, LocalDate.now(), p))) System.out.println("✅ Aluno salvo!");
            }
            case 2 -> alunoDAO.listarTodos().forEach(a -> System.out.println(a.getId() + " - " + a.getNome()));
            case 3 -> {
                System.out.print("ID do Aluno: ");
                int id = lerInteiro();
                Aluno a = alunoDAO.buscarPorId(id);
                if (a != null) {
                    a.exibirDetalhes();
                    System.out.println("Total de visitas: " + frequenciaDAO.contarVisitas(id));
                    System.out.println("Última visita: " + (frequenciaDAO.buscarUltimaVisita(id) != null ? frequenciaDAO.buscarUltimaVisita(id) : "Nunca"));
                    System.out.println("Aulas inscritas: " + inscricaoDAO.listarAulasPorAluno(id).size());
                } else System.out.println("❌ Aluno não encontrado.");
            }
            case 4 -> {
                System.out.print("ID para excluir: ");
                int id = lerInteiro();
                if (alunoDAO.excluir(id)) System.out.println("✅ Excluído!");
            }
        }
    }

    private static void menuInstrutores() {
        System.out.println("\n--- Gerenciar Instrutores ---");
        System.out.println("1. Novo | 2. Listar | 3. Excluir | 0. Voltar");
        int op = lerInteiro();
        switch (op) {
            case 1 -> {
                System.out.print("Nome: "); String nome = sc.nextLine();
                System.out.print("CPF: "); String cpf = sc.nextLine();
                System.out.print("Especialidade: "); String esp = sc.nextLine();
                if (instrutorDAO.inserir(new Instrutor(0, nome, cpf, "", esp, "Manhã/Noite"))) System.out.println("✅ Instrutor salvo!");
            }
            case 2 -> instrutorDAO.listarTodos().forEach(i -> i.exibirDetalhes());
            case 3 -> {
                System.out.print("ID para excluir: ");
                int id = lerInteiro();
                if (instrutorDAO.excluir(id)) System.out.println("✅ Excluído!");
            }
        }
    }

    private static void menuAulas() {
        System.out.println("\n--- Gerenciar Aulas ---");
        System.out.println("1. Nova | 2. Listar | 3. Excluir | 0. Voltar");
        int op = lerInteiro();
        switch (op) {
            case 1 -> {
                System.out.print("Nome: "); String nome = sc.nextLine();
                System.out.print("Capacidade Máxima: "); int cap = lerInteiro();
                System.out.print("Data/Hora (AAAA-MM-DDTHH:MM): "); LocalDateTime data = LocalDateTime.parse(sc.nextLine());
                System.out.println("Instrutores disponíveis:");
                instrutorDAO.listarTodos().forEach(i -> System.out.println(i.getId() + ": " + i.getNome()));
                System.out.print("ID do Instrutor: "); int idIns = lerInteiro();
                Instrutor ins = instrutorDAO.buscarPorId(idIns);
                if (aulaDAO.inserir(new Aula(0, nome, "", cap, data, 60, ins))) System.out.println("✅ Aula salva!");
            }
            case 2 -> aulaDAO.listarTodas().forEach(a -> System.out.println(a.getId() + " - " + a.getNome() + " [" + a.getHorario() + "] - Instrutor: " + (a.getInstrutor() != null ? a.getInstrutor().getNome() : "N/A")));
            case 3 -> {
                System.out.print("ID para excluir: ");
                int id = lerInteiro();
                if (aulaDAO.excluir(id)) System.out.println("✅ Excluída!");
            }
        }
    }

    private static void menuFrequencia() {
        System.out.println("\n--- Controle de Frequência ---");
        System.out.print("ID do Aluno para registrar entrada: ");
        int id = lerInteiro();
        if (frequenciaDAO.registrarEntrada(id)) System.out.println("✅ Entrada registrada!");
    }

    private static void menuInscricoes() {
        System.out.println("\n--- Inscrição em Aulas ---");
        System.out.println("1. Inscrever Aluno | 2. Ver Aulas do Aluno | 0. Voltar");
        int op = lerInteiro();
        switch (op) {
            case 1 -> {
                System.out.print("ID do Aluno: "); int idAlu = lerInteiro();
                System.out.print("ID da Aula: "); int idAul = lerInteiro();
                String resultado = inscricaoDAO.inscreverAluno(idAlu, idAul);
                System.out.println(resultado);
            }
            case 2 -> {
                System.out.print("ID do Aluno: "); int idAlu = lerInteiro();
                inscricaoDAO.listarAulasPorAluno(idAlu).forEach(a -> System.out.println("- " + a.getNome() + " (" + a.getHorario() + ")"));
            }
        }
    }
}