package projEBAC3.jdbc.dao;

import projEBAC3.factory.ConnectionFactory;
import projEBAC3.jdbc.domain.Cliente;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ClienteDAO implements IClienteDAO{

    /// CRUD session
    @Override
    public Integer cadastrar(Cliente cliente) throws Exception {
        Connection connection = null;
        PreparedStatement statement = null;

        try {
            connection = ConnectionFactory.getInstance().getConnection();
            String sql = getInsert();
            statement = connection.prepareStatement(sql);
            addParametrosInsert(statement, cliente);
            return statement.executeUpdate();
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            /// sempre fechar a conexão após execução.
            closeConnection(connection, statement, null);
        }
    }

    @Override
    public Integer atualizar(Cliente cliente) throws Exception {
        Connection connection = null;
        PreparedStatement statement = null;

        try {
            connection = ConnectionFactory.getInstance().getConnection();
            String sql = getUpdate();
            statement = connection.prepareStatement(sql);
            addParametrosUpdate(statement, cliente);
            return statement.executeUpdate();
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            /// sempre fechar a conexão após execução.
            closeConnection(connection, statement, null);
        }
    }

    @Override
    public Cliente buscar(String codigo) throws Exception {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet result = null;
        Cliente cliente = null;

        try {
            connection = ConnectionFactory.getInstance().getConnection();
            String sql = getSelect();
            statement = connection.prepareStatement(sql);
            addParametrosSelect(statement, codigo);
            result = statement.executeQuery();

            if (result.next()) {
                cliente = new Cliente();
                Long id = result.getLong("ID");
                String nome = result.getString("NOME");
                String cd = result.getString("CODIGO");

                cliente.setId(id);
                cliente.setNome(nome);
                cliente.setCodigo(cd);
            }
        } catch (Exception e) {
            throw e;
        } finally {
            /// sempre fechar a conexão após execução.
            closeConnection(connection, statement, null);
        }
        return cliente;
    }

    @Override
    public List<Cliente> buscarTodos() throws Exception {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet result = null;
        List<Cliente> list = new ArrayList<>();
        Cliente cliente = null;

        try {
            connection = ConnectionFactory.getInstance().getConnection();
            String sql = getSelectAll();
            statement = connection.prepareStatement(sql);
            result = statement.executeQuery();

            while (result.next()) {
                cliente = new Cliente();
                Long id = result.getLong("ID");
                String nome = result.getString("NOME");
                String cd = result.getString("CODIGO");

                cliente.setId(id);
                cliente.setNome(nome);
                cliente.setCodigo(cd);
                list.add(cliente);
            }
        } catch (Exception e) {
            throw e;
        } finally {
            /// sempre fechar a conexão após execução.
            closeConnection(connection, statement, null);
        }
        return list;
    }

    @Override
    public Integer excluir(Cliente cliente) throws Exception {
        Connection connection = null;
        PreparedStatement statement = null;

        try {
            connection = ConnectionFactory.getInstance().getConnection();
            String sql = getDelete();
            statement = connection.prepareStatement(sql);
            addParametrosDelete(statement, cliente);
            return statement.executeUpdate();
        } catch (Exception e) {
            throw e;
        } finally {
            /// sempre fechar a conexão após execução.
            closeConnection(connection, statement, null);
        }
    }

    /// SQL session
    private String getInsert() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("INSERT INTO TB_CLIENTE (ID, CODIGO, NOME) ");
        stringBuilder.append("VALUES (nextval('SQ_CLIENTE'),?,?)");

        return stringBuilder.toString();
    }

    private void addParametrosInsert(PreparedStatement statement, Cliente cliente) throws SQLException {
        statement.setString(1, cliente.getCodigo());
        statement.setString(2, cliente.getNome());
    }

    private String getUpdate() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("UPDATE TB_CLIENTE ");
        stringBuilder.append("SET NOME = ?, CODIGO = ? ");
        stringBuilder.append("WHERE ID = ?");

        return stringBuilder.toString();
    }

    private void addParametrosUpdate(PreparedStatement statement, Cliente cliente) throws SQLException {
        statement.setString(1, cliente.getNome());
        statement.setString(2, cliente.getCodigo());
        statement.setLong(3, cliente.getId());
    }

    private String getSelect() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("SELECT * FROM TB_CLIENTE");
        stringBuilder.append("WHERE CODIGO = ?");

        return stringBuilder.toString();
    }

    private void addParametrosSelect(PreparedStatement statement, String codigo) throws SQLException {
        statement.setString(1, codigo);
    }

    private String getSelectAll() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("SELECT * FROM TB_CLIENTE");

        return stringBuilder.toString();
    }

    private String getDelete() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("DELETE FROM TB_CLIENTE");
        stringBuilder.append("WHERE CODIGO = ?");

        return stringBuilder.toString();
    }

    private void addParametrosDelete(PreparedStatement statement, Cliente cliente) throws SQLException {
        statement.setString(1, cliente.getCodigo());
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
        } finally {
            /// sempre fechar a conexão após execução.
            closeConnection(connection, statement, null);
        }
    }
}
