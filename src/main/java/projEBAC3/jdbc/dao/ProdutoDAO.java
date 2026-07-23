package projEBAC3.jdbc.dao;

import projEBAC3.factory.ConnectionFactory;
import projEBAC3.jdbc.domain.Cliente;
import projEBAC3.jdbc.domain.Produto;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

public class ProdutoDAO implements InterfaceDAO<Produto> {

    /// CRUD session
    @Override
    public Integer cadastrar(Produto produto) throws Exception {
        Connection connection = null;
        PreparedStatement statement = null;

        try {
            connection = ConnectionFactory.getInstance().getConnection();

            /// executa schemaProduto.sql em 'Resources'
            executarSchema(connection);

            String sql = getInsert();
            statement = connection.prepareStatement(sql);
            //addParametrosInsert(statement, produto);

            return statement.executeUpdate();
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            /// sempre fechar a conexão após execução.
            closeConnection(connection, statement, null);
        }
    }

    @Override
    public Integer atualizar(Produto produto) throws Exception {
        return 0;
    }

    @Override
    public Produto buscar(String codigo) throws Exception {
        return null;
    }

    @Override
    public List<Produto> buscarTodos() throws Exception {
        return List.of();
    }

    @Override
    public Integer excluir(Produto produto) throws Exception {
        return 0;
    }

    /// SQL session
    private String getInsert() {
        StringBuilder stringBuilder = new StringBuilder();
        //stringBuilder.append();
        return null;
    }

    /// connection session
    private void executarSchema(Connection connection) throws Exception {

        InputStream input = ClienteDAO.class
                            .getClassLoader()
                            .getResourceAsStream("database/schemaProduto.sql");

        if (input == null) {
            throw new RuntimeException("ARQUIVO schemaProduto.sql NÃO ENCONTRADO.");
        }

        String sql;

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(input, StandardCharsets.UTF_8))) {
            sql = reader.lines().collect(Collectors.joining("\n"));
        }

        String[] comandos = sql.split(";");

        for (String comando : comandos) {
            if (!comando.isBlank()) {
                try (PreparedStatement statement = connection.prepareStatement(comando)) {
                    statement.execute();
                }
            }
        }
    }

    private void closeConnection(Connection connection, PreparedStatement statement, ResultSet result) {
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
}
