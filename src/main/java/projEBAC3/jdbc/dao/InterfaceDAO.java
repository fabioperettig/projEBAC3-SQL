package projEBAC3.jdbc.dao;
import projEBAC3.jdbc.domain.Cliente;
import java.util.List;

public interface InterfaceDAO<T> {
    public Integer cadastrar(T entidade) throws Exception;
    public Integer atualizar(T entidade) throws Exception;
    public T buscar(String codigo) throws Exception;
    public List<T> buscarTodos() throws Exception;
    public Integer excluir (T entidade) throws Exception;
}
