package dominio.excecoes;

public class VeiculoNaoPertenceALoja extends RuntimeException {
	public VeiculoNaoPertenceALoja() {
		super("Ve�culo n�o cadastrado.");
	}
}
