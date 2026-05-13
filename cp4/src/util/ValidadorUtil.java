package util;

/**
 * Utilitários de validação de dados de entrada.
 * Centraliza as regras de validação para evitar duplicação de código.
 */
public class ValidadorUtil {

    /**
     * Valida CPF verificando se possui 11 dígitos numéricos.
     *
     * @param cpf String com o CPF (aceita formatado ou apenas números)
     * @return true se válido, false caso contrário
     */
    public static boolean validarCpf(String cpf) {
        if (cpf == null) return false;
        // Remove caracteres não numéricos
        String limpo = cpf.replaceAll("[^0-9]", "");
        return limpo.length() == 11;
    }

    /**
     * Valida e-mail verificando presença de '@' e '.'.
     *
     * @param email String com o endereço de e-mail
     * @return true se válido, false caso contrário
     */
    public static boolean validarEmail(String email) {
        return email != null && email.contains("@") && email.contains(".");
    }

    /**
     * Verifica se uma string não é nula nem vazia.
     *
     * @param valor Valor a ser verificado
     * @return true se não vazia, false caso contrário
     */
    public static boolean naoVazio(String valor) {
        return valor != null && !valor.trim().isEmpty();
    }
}
