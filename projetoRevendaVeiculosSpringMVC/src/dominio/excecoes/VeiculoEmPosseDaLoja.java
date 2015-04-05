package dominio.excecoes;

public class VeiculoEmPosseDaLoja extends RuntimeException{
	public VeiculoEmPosseDaLoja() {
		super("Veículo está em posse da loja");
	}
}
