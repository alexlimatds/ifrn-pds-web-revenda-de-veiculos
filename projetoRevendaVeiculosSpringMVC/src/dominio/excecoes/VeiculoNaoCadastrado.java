package dominio.excecoes;

public class VeiculoNaoCadastrado extends RuntimeException {
	public VeiculoNaoCadastrado() {
		super("Veículo não cadastrado.");
	}
}
