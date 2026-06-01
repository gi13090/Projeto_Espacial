package infra;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.io.InputStream;

public class DataBaseConfigs {

    private static String url;
    private static String user;
    private static String password;

    static {
        try (InputStream input = DataBaseConfigs.class.getClassLoader().getResourceAsStream("database.properties")) {
            Properties props = new Properties();
            if (input == null) {
                throw new RuntimeException("Arquivo database.properties não encontrado!");
            }
            props.load(input);

            url = props.getProperty("db.url");
            user = props.getProperty("db.user");
            password = props.getProperty("db.password");

        } catch (Exception e) {
            throw new RuntimeException("Erro ao carregar configurações do banco", e);
        }
    }
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url, user, password);
    }
}