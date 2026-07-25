package projEBAC3.jdbc.domain;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Produto {

    private Long id;
    private String nome;
    private String codigo;
    private double preco;
    private int estoque;
    private boolean emEstoque;

}