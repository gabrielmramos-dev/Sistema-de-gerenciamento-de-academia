package dao;

import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

/**
 * Gerencia a conexão com o banco de dados PostgreSQL.
 *
 * As credenciais são lidas do arquivo "config.properties" na raiz do projeto,
 * seguindo a boa prática de não expor senhas no código-fonte.
 */
public class ConexaoBD {

    private static String url;
    private static String user;
    private static String password;

    // Bloco estático: carrega as configurações uma única vez quando a classe é
    // usada
    static {
        Properties props = new Properties();
        // Tenta carregar o arquivo de configuração externo
        try (FileInputStream fis = new FileInputStream("config.properties")) {
            props.load(fis);
            url = props.getProperty("db.url");
            user = props.getProperty("db.user");
            password = props.getProperty("db.password");
        } catch (IOException e) {
            // Fallback: usa valores padrão caso o arquivo não seja encontrado
            System.err.println("⚠️ config.properties não encontrado. Usando valores padrão.");
            url = "jdbc:postgresql://localhost:5432/academia_db";
            user = "postgres";
            password = "root";
        }
    }

    /**
     * Abre e retorna uma conexão com o banco de dados.
     * O chamador é responsável por fechar a conexão (try-with-resources).
     */
    public static Connection getConexao() throws SQLException {
        try {
            Class.forName("org.postgresql.Driver");
            return DriverManager.getConnection(url, user, password);
        } catch (ClassNotFoundException e) {
            throw new SQLException("Driver PostgreSQL não encontrado. Verifique o .jar no classpath.", e);
        }
    }
}
