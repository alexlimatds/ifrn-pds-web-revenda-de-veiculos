package models;

/**
 * Representa um tipo, ou categoria, de veículo como sedan, perua, jipe, SUV, etc.
 *
 */
public class TipoVeiculo extends Model{
	
	public String descricao;
	
	public TipoVeiculo(Integer id, String descricao){
		super(id);
		this.descricao = descricao;
	}
	
	public TipoVeiculo() {
		super();
	}
}
