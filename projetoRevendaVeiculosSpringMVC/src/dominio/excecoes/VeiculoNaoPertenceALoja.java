package dominio.excecoes;

public class VeiculoNaoPertenceALoja extends RuntimeException {
	public VeiculoNaoPertenceALoja() {
		super("Veículo não cadastrado.");
	}
}
