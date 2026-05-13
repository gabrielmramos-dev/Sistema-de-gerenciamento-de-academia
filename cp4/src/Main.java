import dao.*;
import model.*;
import service.InscricaoService;
import service.RelatorioService;
import util.ValidadorUtil;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Scanner;

public class Main {

    private static Scanner sc = new Scanner(System.in);
    private static AlunoDAO alunoDAO = new AlunoDAO();
    private static PlanoDAO planoDAO = new PlanoDAO();
    private static InstrutorDAO instrutorDAO = new InstrutorDAO();
    private static AulaDAO aulaDAO = new AulaDAO();
    private static FrequenciaDAO frequenciaDAO = new FrequenciaDAO();
    private static InscricaoDAO inscricaoDAO = new InscricaoDAO();
    private static InscricaoService inscricaoService = new InscricaoService();
    private static RelatorioService relatorioService = new RelatorioService();

    public static void main(String[] args) {
        int opcao = -1;
        do {
            System.out.println("\n========= SISTEMA DE ACADEMIA =========");
            System.out.println("1. Planos    | 2. Alunos    | 3. Instrutores");
            System.out.println("4. Aulas     | 5. Frequencia| 6. Inscricoes");
            System.out.println("7. Relatorio | 0. Sair");
            System.out.print("Escolha: ");

            String entrada = sc.nextLine();
            if (entrada.trim().isEmpty())
                continue;

            try {
                opcao = Integer.parseInt(entrada.trim());
                switch (opcao) {
                    case 1:
                        menuPlanos();
                        break;
                    case 2:
                        menuAlunos();
                        break;
                    case 3:
                        menuInstrutores();
                        break;
                    case 4:
                        menuAulas();
                        break;
                    case 5:
                        menuFrequencia();
                        break;
                    case 6:
                        menuInscricoes();
                        break;
                    case 7:
                        menuRelatorio();
                        break;
                    case 0:
                        System.out.println("Encerrando...");
                        break;
                    default:
                        System.out.println("Opcao invalida!");
                }
            } catch (NumberFormatException e) {
                System.out.println("Digite apenas numeros.");
            } catch (Exception e) {
                System.out.println("Erro: " + e.getMessage());
            }
        } while (opcao != 0);
    }

    private static int lerInteiro() {
        while (true) {
            try {
                return Integer.parseInt(sc.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.print("Digite um numero valido: ");
            }
        }
    }

    private static double lerDouble() {
        while (true) {
            try {
                return Double.parseDouble(sc.nextLine().trim().replace(",", "."));
            } catch (NumberFormatException e) {
                System.out.print("Digite um valor numerico valido: ");
            }
        }
    }

    // ─── MENU PLANOS ───────────────────────────────────

    private static void menuPlanos() {
        int op;
        do {
            System.out.println("\n--- Gerenciar Planos ---");
            System.out.println("1. Cadastrar | 2. Listar | 3. Atualizar | 4. Excluir | 0. Voltar");
            op = lerInteiro();
            switch (op) {
                case 1: {
                    System.out.print("Nome: ");
                    String nome = sc.nextLine();
                    System.out.print("Descricao: ");
                    String desc = sc.nextLine();
                    System.out.print("Valor Mensal (R$): ");
                    double valor = lerDouble();
                    System.out.print("Duracao (meses): ");
                    int dur = lerInteiro();
                    System.out.print("Beneficios: ");
                    String ben = sc.nextLine();
                    if (planoDAO.inserir(new Plano(0, nome, desc, valor, dur, ben)))
                        System.out.println("Plano cadastrado!");
                    break;
                }
                case 2: {
                    List<Plano> planos = planoDAO.listarTodos();
                    if (planos.isEmpty()) {
                        System.out.println("Nenhum plano cadastrado.");
                        break;
                    }
                    System.out.println("\n--- Lista de Planos ---");
                    for (Plano p : planos)
                        System.out.printf("  [%d] %-20s | R$ %.2f/mes | %d meses | %s%n",
                                p.getId(), p.getNome(), p.getValorMensal(), p.getDuracaoMeses(), p.getBeneficios());
                    break;
                }
                case 3: {
                    System.out.print("ID do Plano a atualizar: ");
                    int idAt = lerInteiro();
                    Plano pAt = planoDAO.buscarPorId(idAt);
                    if (pAt == null) {
                        System.out.println("Plano nao encontrado.");
                        break;
                    }
                    System.out.print("Novo Nome [" + pAt.getNome() + "]: ");
                    String nomeAt = sc.nextLine();
                    System.out.print("Nova Descricao [" + pAt.getDescricao() + "]: ");
                    String descAt = sc.nextLine();
                    System.out.print("Novo Valor Mensal [" + pAt.getValorMensal() + "]: ");
                    double valorAt = lerDouble();
                    System.out.print("Nova Duracao meses [" + pAt.getDuracaoMeses() + "]: ");
                    int durAt = lerInteiro();
                    System.out.print("Novos Beneficios [" + pAt.getBeneficios() + "]: ");
                    String benAt = sc.nextLine();
                    if (!nomeAt.isEmpty())
                        pAt.setNome(nomeAt);
                    if (!descAt.isEmpty())
                        pAt.setDescricao(descAt);
                    if (valorAt > 0)
                        pAt.setValorMensal(valorAt);
                    if (durAt > 0)
                        pAt.setDuracaoMeses(durAt);
                    if (!benAt.isEmpty())
                        pAt.setBeneficios(benAt);
                    if (planoDAO.atualizar(pAt))
                        System.out.println("Plano atualizado!");
                    break;
                }
                case 4: {
                    System.out.print("ID do Plano a excluir: ");
                    int idEx = lerInteiro();
                    if (planoDAO.excluir(idEx))
                        System.out.println("Plano excluido!");
                    else
                        System.out.println("Plano nao encontrado.");
                    break;
                }
                case 0:
                    break;
                default:
                    System.out.println("Opcao invalida!");
            }
        } while (op != 0);
    }

    // ─── MENU ALUNOS ───────────────────────────────────

    private static void menuAlunos() {
        int op;
        do {
            System.out.println("\n--- Gerenciar Alunos ---");
            System.out.println("1. Cadastrar | 2. Listar | 3. Detalhe | 4. Atualizar Plano | 5. Excluir | 0. Voltar");
            op = lerInteiro();
            switch (op) {
                case 1: {
                    System.out.print("Nome: ");
                    String nome = sc.nextLine();
                    System.out.print("CPF (11 digitos): ");
                    String cpf = sc.nextLine();
                    if (!ValidadorUtil.validarCpf(cpf)) {
                        System.out.println("CPF invalido!");
                        break;
                    }
                    System.out.print("Telefone: ");
                    String tel = sc.nextLine();
                    System.out.print("Email: ");
                    String email = sc.nextLine();
                    if (!ValidadorUtil.validarEmail(email)) {
                        System.out.println("Email invalido!");
                        break;
                    }
                    System.out.print("Nascimento (AAAA-MM-DD): ");
                    LocalDate nasc;
                    try {
                        nasc = LocalDate.parse(sc.nextLine().trim());
                    } catch (Exception e) {
                        System.out.println("Data invalida!");
                        break;
                    }
                    System.out.println("Planos disponiveis:");
                    List<Plano> planos = planoDAO.listarTodos();
                    for (Plano p : planos)
                        System.out.println("  " + p.getId() + ": " + p.getNome() + " - R$ " + p.getValorMensal());
                    System.out.print("ID do Plano: ");
                    int idP = lerInteiro();
                    Plano plano = planoDAO.buscarPorId(idP);
                    Aluno novo = new Aluno(0, nome, cpf, tel, email, nasc, LocalDate.now(), plano);
                    if (alunoDAO.inserir(novo)) {
                        novo.registrarLog("CADASTRO no sistema");
                        System.out.println("Aluno cadastrado! ID: " + novo.getId());
                    }
                    break;
                }
                case 2: {
                    List<Aluno> alunos = alunoDAO.listarTodos();
                    if (alunos.isEmpty()) {
                        System.out.println("Nenhum aluno cadastrado.");
                        break;
                    }
                    System.out.println("\n--- Lista de Alunos ---");
                    for (Aluno a : alunos)
                        System.out.printf("  [%d] %-25s | Plano: %-15s | %s%n",
                                a.getId(), a.getNome(),
                                (a.getPlano() != null ? a.getPlano().getNome() : "N/A"),
                                (a.isPlanoAtivo() ? "Ativo" : "Vencido"));
                    break;
                }
                case 3: {
                    System.out.print("ID do Aluno: ");
                    int id = lerInteiro();
                    Aluno a = alunoDAO.buscarPorId(id);
                    if (a == null) {
                        System.out.println("Aluno nao encontrado.");
                        break;
                    }
                    a.exibirDetalhes();
                    System.out.println("Total de visitas: " + frequenciaDAO.contarVisitas(id));
                    LocalDateTime ultima = frequenciaDAO.buscarUltimaVisita(id);
                    System.out.println("Ultima visita: " + (ultima != null ? ultima : "Nunca"));
                    List<Aula> aulasIns = inscricaoDAO.listarAulasPorAluno(id);
                    System.out.println("Aulas inscritas: " + aulasIns.size());
                    for (Aula au : aulasIns)
                        System.out.println("  - " + au.getNome() + " (" + au.getHorario() + ")");
                    System.out.println(a.obterHistorico());
                    break;
                }
                case 4: {
                    System.out.print("ID do Aluno: ");
                    int id = lerInteiro();
                    Aluno a = alunoDAO.buscarPorId(id);
                    if (a == null) {
                        System.out.println("Aluno nao encontrado.");
                        break;
                    }
                    System.out.println("Planos disponiveis:");
                    List<Plano> planos = planoDAO.listarTodos();
                    for (Plano p : planos)
                        System.out.println("  " + p.getId() + ": " + p.getNome() + " - R$ " + p.getValorMensal());
                    System.out.print("Novo ID do Plano: ");
                    int idP = lerInteiro();
                    Plano plano = planoDAO.buscarPorId(idP);
                    if (plano == null) {
                        System.out.println("Plano nao encontrado.");
                        break;
                    }
                    a.setPlano(plano);
                    a.setDataMatricula(LocalDate.now());
                    if (alunoDAO.atualizar(a)) {
                        a.registrarLog("ATUALIZACAO de plano para " + plano.getNome());
                        System.out.println("Plano atualizado! Novo vencimento: " + a.getDataVencimentoPlano());
                    }
                    break;
                }
                case 5: {
                    System.out.print("ID do Aluno a excluir: ");
                    int id = lerInteiro();
                    if (alunoDAO.excluir(id))
                        System.out.println("Aluno excluido!");
                    else
                        System.out.println("Aluno nao encontrado.");
                    break;
                }
                case 0:
                    break;
                default:
                    System.out.println("Opcao invalida!");
            }
        } while (op != 0);
    }

    // ─── MENU INSTRUTORES ──────────────────────────────

    private static void menuInstrutores() {
        int op;
        do {
            System.out.println("\n--- Gerenciar Instrutores ---");
            System.out.println("1. Cadastrar | 2. Listar | 3. Atualizar | 4. Excluir | 0. Voltar");
            op = lerInteiro();
            switch (op) {
                case 1: {
                    System.out.print("Nome: ");
                    String nome = sc.nextLine();
                    System.out.print("CPF (11 digitos): ");
                    String cpf = sc.nextLine();
                    System.out.print("Telefone: ");
                    String tel = sc.nextLine();
                    System.out.print("Especialidade: ");
                    String esp = sc.nextLine();
                    System.out.print("Horarios de Trabalho: ");
                    String hor = sc.nextLine();
                    Instrutor novo = new Instrutor(0, nome, cpf, tel, esp, hor);
                    if (instrutorDAO.inserir(novo)) {
                        novo.registrarLog("CADASTRO no sistema");
                        System.out.println("Instrutor cadastrado! ID: " + novo.getId());
                    }
                    break;
                }
                case 2: {
                    List<Instrutor> instrutores = instrutorDAO.listarTodos();
                    if (instrutores.isEmpty()) {
                        System.out.println("Nenhum instrutor cadastrado.");
                        break;
                    }
                    System.out.println("\n--- Lista de Instrutores ---");
                    for (Instrutor i : instrutores)
                        System.out.printf("  [%d] %-25s | Especialidade: %-20s | Horarios: %s%n",
                                i.getId(), i.getNome(), i.getEspecialidade(), i.getHorariosTrabalho());
                    break;
                }
                case 3: {
                    System.out.print("ID do Instrutor a atualizar: ");
                    int id = lerInteiro();
                    Instrutor i = instrutorDAO.buscarPorId(id);
                    if (i == null) {
                        System.out.println("Instrutor nao encontrado.");
                        break;
                    }
                    System.out.print("Nova Especialidade [" + i.getEspecialidade() + "]: ");
                    String esp = sc.nextLine();
                    System.out.print("Novos Horarios [" + i.getHorariosTrabalho() + "]: ");
                    String hor = sc.nextLine();
                    if (!esp.isEmpty())
                        i.setEspecialidade(esp);
                    if (!hor.isEmpty())
                        i.setHorariosTrabalho(hor);
                    if (instrutorDAO.atualizar(i)) {
                        i.registrarLog("ATUALIZACAO de dados");
                        System.out.println("Instrutor atualizado!");
                    }
                    break;
                }
                case 4: {
                    System.out.print("ID do Instrutor a excluir: ");
                    int id = lerInteiro();
                    if (instrutorDAO.excluir(id))
                        System.out.println("Instrutor excluido!");
                    else
                        System.out.println("Instrutor nao encontrado.");
                    break;
                }
                case 0:
                    break;
                default:
                    System.out.println("Opcao invalida!");
            }
        } while (op != 0);
    }

    // ─── MENU AULAS ────────────────────────────────────

    private static void menuAulas() {
        int op;
        do {
            System.out.println("\n--- Gerenciar Aulas ---");
            System.out.println("1. Cadastrar | 2. Listar | 3. Atualizar Capacidade | 4. Excluir | 0. Voltar");
            op = lerInteiro();
            switch (op) {
                case 1: {
                    System.out.print("Nome da Aula: ");
                    String nome = sc.nextLine();
                    System.out.print("Descricao: ");
                    String desc = sc.nextLine();
                    System.out.print("Capacidade Maxima: ");
                    int cap = lerInteiro();
                    System.out.print("Data/Hora (AAAA-MM-DDTHH:MM): ");
                    LocalDateTime dt;
                    try {
                        dt = LocalDateTime.parse(sc.nextLine().trim());
                    } catch (Exception e) {
                        System.out.println("Formato invalido! Use AAAA-MM-DDTHH:MM");
                        break;
                    }
                    System.out.print("Duracao (minutos): ");
                    int dur = lerInteiro();
                    System.out.println("Instrutores disponiveis:");
                    List<Instrutor> instrutores = instrutorDAO.listarTodos();
                    for (Instrutor i : instrutores)
                        System.out.println("  " + i.getId() + ": " + i.getNome());
                    System.out.print("ID do Instrutor: ");
                    int idIns = lerInteiro();
                    Instrutor ins = instrutorDAO.buscarPorId(idIns);
                    if (aulaDAO.inserir(new Aula(0, nome, desc, cap, dt, dur, ins)))
                        System.out.println("Aula cadastrada!");
                    break;
                }
                case 2: {
                    List<Aula> aulas = aulaDAO.listarTodas();
                    if (aulas.isEmpty()) {
                        System.out.println("Nenhuma aula cadastrada.");
                        break;
                    }
                    System.out.println("\n--- Lista de Aulas ---");
                    for (Aula a : aulas) {
                        int inscritos = inscricaoDAO.contarInscritosNaAula(a.getId());
                        System.out.printf("  [%d] %-20s | %s | Vagas: %d/%d | Instrutor: %s%n",
                                a.getId(), a.getNome(), a.getHorario(),
                                inscritos, a.getCapacidadeMaxima(),
                                (a.getInstrutor() != null ? a.getInstrutor().getNome() : "N/A"));
                    }
                    break;
                }
                case 3: {
                    System.out.print("ID da Aula a atualizar: ");
                    int id = lerInteiro();
                    Aula a = aulaDAO.buscarPorId(id);
                    if (a == null) {
                        System.out.println("Aula nao encontrada.");
                        break;
                    }
                    System.out.print("Nova Capacidade Maxima [" + a.getCapacidadeMaxima() + "]: ");
                    int cap = lerInteiro();
                    if (cap > 0)
                        a.setCapacidadeMaxima(cap);
                    if (aulaDAO.atualizar(a))
                        System.out.println("Aula atualizada!");
                    break;
                }
                case 4: {
                    System.out.print("ID da Aula a excluir: ");
                    int id = lerInteiro();
                    if (aulaDAO.excluir(id))
                        System.out.println("Aula excluida!");
                    else
                        System.out.println("Aula nao encontrada.");
                    break;
                }
                case 0:
                    break;
                default:
                    System.out.println("Opcao invalida!");
            }
        } while (op != 0);
    }

    // ─── MENU FREQUÊNCIA ───────────────────────────────

    private static void menuFrequencia() {
        int op;
        do {
            System.out.println("\n--- Controle de Frequencia ---");
            System.out.println("1. Registrar Entrada | 2. Ver Historico | 0. Voltar");
            op = lerInteiro();
            switch (op) {
                case 1: {
                    System.out.print("ID do Aluno: ");
                    int id = lerInteiro();
                    Aluno a = alunoDAO.buscarPorId(id);
                    if (a == null) {
                        System.out.println("Aluno nao encontrado.");
                        break;
                    }
                    if (!a.isPlanoAtivo())
                        System.out.println("ATENCAO: Plano VENCIDO em " + a.getDataVencimentoPlano());
                    if (frequenciaDAO.registrarEntrada(id)) {
                        a.registrarLog("ENTRADA registrada na academia");
                        System.out.println("Entrada de " + a.getNome() + " registrada!");
                    }
                    break;
                }
                case 2: {
                    System.out.print("ID do Aluno: ");
                    int id = lerInteiro();
                    Aluno a = alunoDAO.buscarPorId(id);
                    if (a == null) {
                        System.out.println("Aluno nao encontrado.");
                        break;
                    }
                    List<Frequencia> frequencias = frequenciaDAO.listarPorAluno(id);
                    System.out.println("\n--- Historico: " + a.getNome() + " ---");
                    System.out.println("Total de visitas: " + frequencias.size());
                    int count = 0;
                    for (Frequencia f : frequencias) {
                        if (count >= 10)
                            break;
                        System.out.println("  " + f.getDataHoraEntrada());
                        count++;
                    }
                    if (frequencias.size() > 10)
                        System.out.println("  ... (exibindo 10 de " + frequencias.size() + ")");
                    break;
                }
                case 0:
                    break;
                default:
                    System.out.println("Opcao invalida!");
            }
        } while (op != 0);
    }

    // ─── MENU INSCRIÇÕES ───────────────────────────────

    private static void menuInscricoes() {
        int op;
        do {
            System.out.println("\n--- Inscricao em Aulas ---");
            System.out.println("1. Inscrever Aluno | 2. Cancelar Inscricao | 3. Ver Aulas do Aluno | 0. Voltar");
            op = lerInteiro();
            switch (op) {
                case 1: {
                    System.out.println("\nAulas disponiveis:");
                    List<Aula> aulas = aulaDAO.listarTodas();
                    for (Aula a : aulas) {
                        int inscritos = inscricaoDAO.contarInscritosNaAula(a.getId());
                        System.out.printf("  [%d] %-20s | %s | Vagas: %d/%d%n",
                                a.getId(), a.getNome(), a.getHorario(), inscritos, a.getCapacidadeMaxima());
                    }
                    System.out.print("ID do Aluno: ");
                    int idAlu = lerInteiro();
                    System.out.print("ID da Aula: ");
                    int idAul = lerInteiro();
                    System.out.println(inscricaoService.inscreverAluno(idAlu, idAul));
                    break;
                }
                case 2: {
                    System.out.print("ID do Aluno: ");
                    int idAlu = lerInteiro();
                    System.out.print("ID da Aula: ");
                    int idAul = lerInteiro();
                    System.out.println(inscricaoService.cancelarInscricao(idAlu, idAul));
                    break;
                }
                case 3: {
                    System.out.print("ID do Aluno: ");
                    int idAlu = lerInteiro();
                    Aluno a = alunoDAO.buscarPorId(idAlu);
                    if (a == null) {
                        System.out.println("Aluno nao encontrado.");
                        break;
                    }
                    List<Aula> aulas = inscricaoDAO.listarAulasPorAluno(idAlu);
                    System.out.println("\n--- Aulas de " + a.getNome() + " ---");
                    if (aulas.isEmpty()) {
                        System.out.println("Nenhuma inscricao encontrada.");
                        break;
                    }
                    for (Aula au : aulas)
                        System.out.printf("  [%d] %-20s | %s%n", au.getId(), au.getNome(), au.getHorario());
                    break;
                }
                case 0:
                    break;
                default:
                    System.out.println("Opcao invalida!");
            }
        } while (op != 0);
    }

    // ─── MENU RELATÓRIO ────────────────────────────────

    private static void menuRelatorio() {
        int op;
        do {
            System.out.println("\n--- Relatorios ---");
            System.out.println("1. Exibir Relatorio Geral | 2. Exportar para Arquivo | 0. Voltar");
            op = lerInteiro();
            switch (op) {
                case 1:
                    relatorioService.gerarRelatorio();
                    break;
                case 2: {
                    System.out.print("Nome do arquivo (ex: relatorio.txt): ");
                    String arq = sc.nextLine();
                    if (arq.isEmpty())
                        arq = "relatorio_academia.txt";
                    relatorioService.exportarParaArquivo(arq);
                    break;
                }
                case 0:
                    break;
                default:
                    System.out.println("Opcao invalida!");
            }
        } while (op != 0);
    }
}
