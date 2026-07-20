package projEBAC3.factory;

import io.github.cdimascio.dotenv.Dotenv;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionFactory {

    private static volatile ConnectionFactory instance;
    private static Connection connection;

    private ConnectionFactory() {
        initConnection();
    }

    /// Método double-checked locking para garantir uma única instância
    public static ConnectionFactory getInstance() {
        ConnectionFactory result = instance;

        if (result == null) {
            synchronized (ConnectionFactory.class) {
                result = instance;
                if (result == null) {
                    instance = result = new ConnectionFactory();
                }
            }
        }
        return result;
    }

    private synchronized void initConnection() {

        Dotenv dotenv = Dotenv.load();
        String url = dotenv.get("DB_URL");
        String user = dotenv.get("DB_USER");
        String password = dotenv.get("DB_PASSWORD");

        try {
            connection = DriverManager.getConnection(url, user, password);
        } catch (SQLException e) {
            throw new RuntimeException("NÃO FOI POSSÍVEL ESTABELECER UMA CONEXÃO AO BANCO DE DADOS.", e);
        }
    }

    public synchronized Connection getConnection() {
        try {
            if (connection == null || connection.isClosed()) {
                initConnection();
            }
        } catch (SQLException e) {
            throw new RuntimeException("ERRO AO VERIFICAR O STATUS DA CONEXÃO COM O BANCO DE DADOS.", e);
        }
        return connection;
    }

}
