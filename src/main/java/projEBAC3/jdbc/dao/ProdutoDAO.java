package projEBAC3.jdbc.dao;

import projEBAC3.factory.ConnectionFactory;
import projEBAC3.jdbc.domain.Produto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProdutoDAO extends AbstractDAO<Produto> implements InterfaceDAO<Produto> {

    /// CRUD SESSION
    /// futuramente analisar abstração
    @Override
    public Integer cadastrar(Produto produto) throws Exception {
        Connection connection = null;
        PreparedStatement statement = null;

        try {
            connection = ConnectionFactory.getInstance().getConnection();
            executarSchema(connection);

            String sql = getInsert();
            statement = connection.prepareStatement(sql);
            addParametrosInsert(statement, produto);

            return statement.executeUpdate();
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            closeConnection(connection, statement, null);
        }

    }

    @Override
    public Integer atualizar(Produto produto) throws Exception {
        Connection connection = null;
        PreparedStatement statement = null;

        try {
            connection = ConnectionFactory.getInstance().getConnection();
            executarSchema(connection);

            String sql = getUpdate();
            statement = connection.prepareStatement(sql);
            addParametrosInsert(statement, produto);

            return statement.executeUpdate();
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            closeConnection(connection, statement, null);
        }
    }

    @Override
    public Produto buscar(String codigo) throws Exception {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet result = null;
        Produto produto = null;

        try {
            connection = ConnectionFactory.getInstance().getConnection();
            String sql = getSelect();
            statement = connection.prepareStatement(sql);
            addParametrosSelect(statement, codigo);
            result = statement.executeQuery();

            if (result.next()) {
                produto = new Produto();
                Long id = result.getLong("ID");
                String nome = result.getString("NOME");
                String cd = result.getString("CODIGO");
                Double preco = result.getDouble("PREÇO");
                Integer estoque = result.getInt("ESTOQUE");

                produto.setId(id);
                produto.setNome(nome);
                produto.setCodigo(cd);
                produto.setPreco(preco);
                produto.setEstoque(estoque);
            }
        } catch (Exception e) {
            throw e;
        } finally {
            /// sempre fechar a conexão após execução.
            closeConnection(connection, statement, result);
        }
        return produto;
    }

    @Override
    public List<Produto> buscarTodos() throws Exception {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet result = null;
        List<Produto> list = new ArrayList<>();
        Produto produto;

        try {
            connection = ConnectionFactory.getInstance().getConnection();
            String sql = getSelectAll();
            statement = connection.prepareStatement(sql);
            result = statement.executeQuery();

            while (result.next()) {
                produto = new Produto();
                Long id = result.getLong("ID");
                String nome = result.getString("NOME");
                String cd = result.getString("CODIGO");
                Double preco = result.getDouble("PREÇO");
                Integer estoque = result.getInt("ESTOQUE");

                produto.setId(id);
                produto.setNome(nome);
                produto.setCodigo(cd);
                produto.setPreco(preco);
                produto.setEstoque(estoque);

                list.add(produto);
            }
        } catch (Exception e) {
            throw e;
        } finally {
            /// sempre fechar a conexão após execução.
            closeConnection(connection, statement, result);
        }
        return list;
    }

    @Override
    public Integer excluir(Produto produto) throws Exception {
        Connection connection = null;
        PreparedStatement statement = null;

        try {
            connection = ConnectionFactory.getInstance().getConnection();
            String sql = getDelete();
            statement = connection.prepareStatement(sql);
            addParametrosDelete(statement, produto);
            return statement.executeUpdate();
        } catch (Exception e) {
            throw e;
        } finally {
            closeConnection(connection, statement, null);
        }
    }

    /// SQL session
    @Override
    protected String getInsert() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("INSERT INTO TB_PRODUTO (ID, CODIGO, NOME, PREÇO, ESTOQUE) ");
        stringBuilder.append("VALUES (nextval('SQ_PRODUTO'),?,?,?,?)");

        return stringBuilder.toString();
    }

    @Override
    protected String getUpdate() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("UPDATE TB_PRODUTO ");
        stringBuilder.append("SET NOME = ?, CODIGO = ?, ");
        stringBuilder.append("SET PREÇO = ?, ESTOQUE = ? ");
        stringBuilder.append("WHERE ID = ?");

        return stringBuilder.toString();
    }

    @Override
    protected void addParametrosInsert(PreparedStatement statement, Produto produto) throws SQLException {
        statement.setString(1, produto.getNome());
        statement.setString(2, produto.getCodigo());
        statement.setDouble(3, produto.getPreco());
        statement.setInt(4, produto.getEstoque());
    }

    @Override
    protected void addParametrosUpdate(PreparedStatement statement, Produto produto) throws SQLException {
        statement.setString(1, produto.getNome());
        statement.setString(2, produto.getCodigo());
        statement.setDouble(3, produto.getPreco());
        statement.setInt(4, produto.getEstoque());
        statement.setLong(5, produto.getId());
    }

    @Override
    protected void addParametrosDelete(PreparedStatement statement, Produto produto) throws SQLException {
        statement.setLong(1, produto.getId());
    }

    @Override
    protected String getSchema() {
        return "database/schemaProduto.sql";
    }

    @Override
    protected String getTabela() {
        return "TB_PRODUTO";
    }
}
