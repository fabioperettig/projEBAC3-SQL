CREATE TABLE Produto(
    id SERIAL PRIMARY KEY,
    nome VARCHAR(100),
    descrição VARCHAR(255),
    preço TYPE DECIMAL(10,2),
    estoque INTEGER,
    em_estoque BOOLEAN DEFAULT TRUE;
);