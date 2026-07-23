package projEBAC3test;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import projEBAC3.jdbc.dao.ClienteDAO;
import projEBAC3.jdbc.dao.InterfaceDAO;
import projEBAC3.jdbc.domain.Cliente;

import java.util.List;

public class ClienteTest {

    private InterfaceDAO clienteDAO;

    @Test
    public void cadastrarTest() throws Exception {
        clienteDAO = new ClienteDAO();

        /// >INSERT<
        Cliente cliente = new Cliente();
        cliente.setCodigo("1010");
        cliente.setNome("Don Lotário");
        Integer qtdCadastrada = clienteDAO.cadastrar(cliente);
        Assertions.assertEquals(1, qtdCadastrada);

        /// SELECT
        cliente = clienteDAO.buscar("1010");
        Assertions.assertNotNull(cliente);
        Assertions.assertEquals("Don Lotário", cliente.getNome());
        Assertions.assertEquals("1010", cliente.getCodigo());

        /// DELETE
        Integer qtdExcluida = clienteDAO.excluir(cliente);
        Assertions.assertEquals(1, qtdExcluida);
    }

    @Test
    public void buscarTest() throws Exception {
        clienteDAO = new ClienteDAO();

        /// INSERT
        Cliente cliente = new Cliente();
        cliente.setCodigo("1010");
        cliente.setNome("Don Lotário");
        Integer qtdCadastrada = clienteDAO.cadastrar(cliente);
        Assertions.assertEquals(1, qtdCadastrada);

        /// >SELECT<
        cliente = clienteDAO.buscar("1010");
        Assertions.assertNotNull(cliente);
        Assertions.assertEquals("Don Lotário", cliente.getNome());
        Assertions.assertEquals("1010", cliente.getCodigo());

        /// DELETE
        Integer qtdExcluida = clienteDAO.excluir(cliente);
        Assertions.assertEquals(1, qtdExcluida);
    }

    @Test
    public void excluirTest() throws Exception {
        clienteDAO = new ClienteDAO();

        /// INSERT
        Cliente cliente = new Cliente();
        cliente.setCodigo("1010");
        cliente.setNome("Don Lotário");
        Integer qtdCadastrada = clienteDAO.cadastrar(cliente);
        Assertions.assertEquals(1, qtdCadastrada);

        /// SELECT
        cliente = clienteDAO.buscar("1010");
        Assertions.assertNotNull(cliente);
        Assertions.assertEquals("Don Lotário", cliente.getNome());
        Assertions.assertEquals("1010", cliente.getCodigo());

        /// >DELETE<
        Integer qtdExcluida = clienteDAO.excluir(cliente);
        Assertions.assertEquals(1, qtdExcluida);
    }

    @Test
    public void buscarTodosTest() throws Exception {
        clienteDAO = new ClienteDAO();

        /// INSERT
        Cliente cliente1 = new Cliente();
        cliente1.setCodigo("1010");
        cliente1.setNome("Don Lotário");
        Integer clienteDON = clienteDAO.cadastrar(cliente1);

        Cliente cliente2 = new Cliente();
        cliente2.setCodigo("1207");
        cliente2.setNome("Dina Caliente");
        Integer clienteDINA = clienteDAO.cadastrar(cliente2);

        Cliente cliente3 = new Cliente();
        cliente3.setCodigo("3110");
        cliente3.setNome("Laura Caixão");
        Integer clienteLAURA = clienteDAO.cadastrar(cliente3);

        Integer qtdCadastradas = clienteDON + clienteDINA + clienteLAURA;
        Assertions.assertEquals(3, qtdCadastradas);

        /// >SELECT * <
        List<Cliente> list = clienteDAO.buscarTodos();
        Assertions.assertNotNull(list);
        Assertions.assertEquals(qtdCadastradas, list.size());

        /// DELETE
        int qtdExcluida = 0;
        for (Cliente c : list) {
            clienteDAO.excluir(c);
            qtdExcluida++;
        }

        Assertions.assertEquals(list.size(), qtdExcluida);
        list = clienteDAO.buscarTodos();
        Assertions.assertEquals(0, list.size());
    }

    @Test
    public void atualizarTest() throws Exception {
        clienteDAO = new ClienteDAO();

        /// >INSERT<
        Cliente cliente = new Cliente();
        cliente.setCodigo("0110");
        cliente.setNome("Don Lotrio");
        Integer qtdCadastrada = clienteDAO.cadastrar(cliente);
        Assertions.assertEquals(1, qtdCadastrada);

        /// SELECT ERROR
        cliente = clienteDAO.buscar("0110");
        Assertions.assertNotNull(cliente);
        Assertions.assertNotEquals("Don Lotário", cliente.getNome());
        Assertions.assertNotEquals("1010", cliente.getCodigo());

        /// UPDATE
        cliente.setCodigo("1010");
        cliente.setNome("Don Lotário");
        Integer qtdAtualizada = clienteDAO.atualizar(cliente);

        /// SELECT
        cliente = clienteDAO.buscar("1010");
        Assertions.assertNotNull(cliente);
        Assertions.assertEquals("Don Lotário", cliente.getNome());
        Assertions.assertEquals("1010", cliente.getCodigo());

        /// >DELETE<
        Integer qtdExcluida = clienteDAO.excluir(cliente);
        Assertions.assertEquals(1, qtdExcluida);
    }

}
