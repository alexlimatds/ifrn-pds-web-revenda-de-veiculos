package dominio;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

/**
 * Representa um modelo de veículo como Gol, Uno, Fiesta, etc.
 */
public class Modelo extends Entidade{
	
	@NotNull
	@NotEmpty
	private String descricao;
	@NotNull
	private Fabricante fabricante;
	@NotNull
	private TipoVeiculo tipo;
	
	public Modelo(Integer id, String descricao, Fabricante fabricante, 
			TipoVeiculo tipo) {
		super(id);
		this.descricao = descricao;
		this.fabricante = fabricante;
		this.tipo = tipo;
	}
	
	public Modelo() {
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public Fabricante getFabricante() {
		return fabricante;
	}

	public void setFabricante(Fabricante fabricante) {
		this.fabricante = fabricante;
	}

	public TipoVeiculo getTipo() {
		return tipo;
	}

	public void setTipo(TipoVeiculo tipo) {
		this.tipo = tipo;
	}
}
