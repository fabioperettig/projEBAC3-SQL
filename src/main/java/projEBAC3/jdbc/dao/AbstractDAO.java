package projEBAC3.jdbc.dao;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.stream.Collectors;

public abstract class AbstractDAO<T> {

    /// CONNECTION SESSION
    protected void executarSchema(Connection connection) throws Exception {

        InputStream input = getClass()
                .getClassLoader()
                .getResourceAsStream(getSchema());

        if (input == null) {
            throw new RuntimeException("ARQUIVO .sql NÃO ENCONTRADO NA PASTA RESOURCE.");
        }

        String sql;

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(input, StandardCharsets.UTF_8))) {
            sql = reader.lines().collect(Collectors.joining("\n"));
        }

        String[] comandos = sql.split(";");

        for (String comando :  comandos) {
            if (!comando.isBlank()) {
                try (PreparedStatement statement = connection.prepareStatement(comando)) {
                    statement.execute();
                }
            }
        }
    }

    protected void closeConnection(Connection connection, PreparedStatement statement, ResultSet result) {
        try {
            if (result != null && !result.isClosed()) {
                result.close();
            }

            if (statement != null && !statement.isClosed()) {
                statement.close();
            }

            if (connection != null && !connection.isClosed()) {
                connection.close();
            }
        } catch (SQLException sqlException) {
            sqlException.printStackTrace();
        }
    }

    /// CRUD SESSION
    protected abstract String getSchema();
    protected abstract String getTabela();
    protected abstract String getInsert();

    protected String getSelect() {
        return String.format("SELECT * FROM %s WHERE CODIGO = ?", getTabela());
    }

    protected String getSelectAll() {
        return String.format("SELECT * FROM %s", getTabela());
    }

    protected String getDelete() {
        return String.format("DELETE FROM %s WHERE CODIGO = ?", getTabela());
    }

    protected abstract String getUpdate();

    protected abstract void addParametrosInsert(PreparedStatement statement, T entidade) throws SQLException;
    protected abstract void addParametrosUpdate(PreparedStatement statement, T entidade) throws SQLException;
    protected abstract void addParametrosDelete(PreparedStatement statement, T entidade) throws SQLException;

    protected void addParametrosSelect(PreparedStatement statement, String codigo) throws SQLException {
        statement.setString(1, codigo);
    }

}
