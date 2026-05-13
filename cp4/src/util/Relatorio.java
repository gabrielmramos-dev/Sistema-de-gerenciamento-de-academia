package util;

/**
 * Interface Relatorio — CP4
 *
 * Define o contrato para geração de relatórios do sistema.
 * Classes que produzem saídas estruturadas de dados devem implementar
 * esta interface para garantir consistência na apresentação de informações.
 */
public interface Relatorio {

    /**
     * Exibe o relatório formatado no console.
     */
    void gerarRelatorio();

    /**
     * Exporta o conteúdo do relatório para um arquivo de texto.
     *
     * @param nomeArquivo Nome (com extensão) do arquivo de saída
     */
    void exportarParaArquivo(String nomeArquivo);
}
