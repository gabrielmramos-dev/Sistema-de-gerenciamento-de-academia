package util;

public class ValidadorUtil {
    
    public static boolean validarCpf(String cpf) {
        if (cpf == null) return false;
        // Remove caracteres não numéricos
        String limpo = cpf.replaceAll("[^0-9]", "");
        return limpo.length() == 11;
    }

    public static boolean validarEmail(String email) {
        return email != null && email.contains("@") && email.contains(".");
    }
}
