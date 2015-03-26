package dominio;

/**
 * Representa um tipo, ou categoria, de veículo como sedan, perua, jipe, SUV, etc.
 */
public class TipoVeiculo extends Entidade{
	
	private String descricao;
	
	public TipoVeiculo(Integer id, String descricao){
		super(id);
		this.descricao = descricao;
	}
	
	public TipoVeiculo() {
		super();
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
}
