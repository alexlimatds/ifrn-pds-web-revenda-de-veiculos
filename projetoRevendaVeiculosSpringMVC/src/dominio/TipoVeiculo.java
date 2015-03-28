package dominio;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

/**
 * Representa um tipo, ou categoria, de veículo como sedan, perua, jipe, SUV, etc.
 */
public class TipoVeiculo extends Entidade{
	
	@NotNull
	@NotEmpty
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
