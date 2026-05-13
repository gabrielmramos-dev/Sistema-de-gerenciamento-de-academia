package util;

/**
 * Interface Auditavel — CP4
 *
 * Define o contrato de auditoria de ações realizadas em entidades do sistema.
 * Toda classe que representa uma entidade sujeita a registro de log deve
 * implementar esta interface.
 */
public interface Auditavel {

    /**
     * Registra uma ação realizada sobre a entidade.
     *
     * @param acao Descrição da ação executada (ex: "CADASTRO", "ATUALIZAÇÃO")
     */
    void registrarLog(String acao);

    /**
     * Retorna o histórico completo de ações registradas.
     *
     * @return String formatada com todas as entradas de log
     */
    String obterHistorico();
}
