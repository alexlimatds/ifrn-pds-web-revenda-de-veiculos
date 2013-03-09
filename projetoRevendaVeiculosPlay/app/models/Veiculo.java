package models;

/**
 * Representa um veículo comercializado pela loja.
 *
 */
public class Veiculo extends Model {
	
	public int anoFabricacao;
	public String placa;
	public String chassi;
	public byte[] foto;
	public int cilindradas;
	public Modelo modelo;

	public Veiculo() {
		super();
	}

	public boolean isEmPosseDaLoja() {
		// TODO
		return false;
	}
}