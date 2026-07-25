package projEBAC3test;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import projEBAC3.jdbc.dao.InterfaceDAO;
import projEBAC3.jdbc.dao.ProdutoDAO;
import projEBAC3.jdbc.domain.Produto;

import java.util.List;

public class ProdutoTest {

    private InterfaceDAO<Produto> produtoDAO;
    private Produto produto;

    @BeforeEach
    public void initDAO(){
        produtoDAO = new ProdutoDAO();
        produto = new Produto();
    }

    private void cadastrarProdutoPadrao() throws Exception {
        /// INSERT
        produto.setNome("Cama Sonho Napoleônico");
        produto.setCodigo("TS1S01");
        produto.setPreco(990.90);
        produto.setEstoque(1);

        produtoDAO.cadastrar(produto);
    }

    @Test
    public void cadastrarTest() throws Exception {
        cadastrarProdutoPadrao();
        Assertions.assertNotNull(produto);
        produtoDAO.excluir(produto);
    }

    @Test
    public void atualizarTest() throws Exception {
        cadastrarProdutoPadrao();

        /// Sempre buscar o objeto com primary key antes de testar update
        produto = produtoDAO.buscar(produto.getCodigo());
        /// UPDATE
        produto.setCodigo("TS1C01");
        produto.setPreco(899.90);
        produtoDAO.atualizar(produto);

        Assertions.assertEquals(899.90, produto.getPreco());
        Assertions.assertEquals("TS1C01", produto.getCodigo());

        produtoDAO.excluir(produto);
    }

    @Test
    public void buscarTest() throws Exception {
        cadastrarProdutoPadrao();

        Produto pComparador = produtoDAO.buscar(produto.getCodigo());

        Assertions.assertNotNull(pComparador);
        Assertions.assertEquals(produto.getNome(), pComparador.getNome());
        Assertions.assertEquals("TS1S01", pComparador.getCodigo());

        produtoDAO.excluir(produto);
    }

    @Test
    public void excluirTest() throws Exception {
        cadastrarProdutoPadrao();

        produto = produtoDAO.buscar("TS1S01");

        int qtdExcluida = produtoDAO.excluir(produto);
        Assertions.assertEquals(1, qtdExcluida);

        Assertions.assertNull(produtoDAO.buscar("TS1D01"));
    }

    @Test
    public void buscarTodosTest() throws Exception {
        int prd1 = cadastrarProduto("Cama Sonho Napoleônico", "TS1S01", 990.90, 1);
        int prd2 = cadastrarProduto("Cafeteira Sempre Alerta", "TS1C01", 85.0, 5);
        int prd3 = cadastrarProduto("Tripé Dobrável Diamante", "TS1L01", 249.99, 2);
        int prd4 = cadastrarProduto("Computador JCN 48 1/2", "TS1E01", 1500.00, 3);

        Integer qtdCadastradas = prd1 + prd2 + prd3 + prd4;

        List<Produto> list = produtoDAO.buscarTodos();
        Assertions.assertNotNull(list);
        Assertions.assertEquals(qtdCadastradas, list.size());

        for (Produto p : list) {
            produtoDAO.excluir(p);
        }

        list = produtoDAO.buscarTodos();
        Assertions.assertEquals(0, list.size());
    }

    public int cadastrarProduto(String nome, String codigo, double preco, int estoque) throws Exception {
        Produto p = new Produto();

        p.setNome(nome);
        p.setCodigo(codigo);
        p.setPreco(preco);
        p.setEstoque(estoque);

        return produtoDAO.cadastrar(p);
    }
}
