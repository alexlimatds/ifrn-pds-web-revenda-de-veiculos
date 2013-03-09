package models;

/**
 * Representa um modelo de veículo como Gol, Uno, Fiesta, etc.
 *
 */
public class Modelo extends Model{
	
	public String descricao;
	public Fabricante fabricante;
	public TipoVeiculo tipo;
	
	public Modelo(Integer id, String descricao, Fabricante fabricante, TipoVeiculo tipo) {
		super(id);
		this.descricao = descricao;
		this.fabricante = fabricante;
		this.tipo = tipo;
	}
	
	public Modelo() {
		super();
	}
}
