package dominio.excecoes;

public class VeiculoNaoCadastrado extends RuntimeException {
	public VeiculoNaoCadastrado() {
		super("Ve�culo n�o cadastrado.");
	}
}
