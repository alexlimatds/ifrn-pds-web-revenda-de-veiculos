package dominio.excecoes;

public class VeiculoEmPosseDaLoja extends RuntimeException{
	public VeiculoEmPosseDaLoja() {
		super("Ve�culo est� em posse da loja");
	}
}
