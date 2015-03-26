package dominio;

/**
 * Representa um modelo de veículo como Gol, Uno, Fiesta, etc.
 */
public class Modelo extends Entidade{
	
	private String descricao;
	private Fabricante fabricante;
	private TipoVeiculo tipo;
	
	public Modelo(Integer id, String descricao, Fabricante fabricante, TipoVeiculo tipo) {
		super(id);
		this.descricao = descricao;
		this.fabricante = fabricante;
		this.tipo = tipo;
	}
	
	public Modelo() {
		super();
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
