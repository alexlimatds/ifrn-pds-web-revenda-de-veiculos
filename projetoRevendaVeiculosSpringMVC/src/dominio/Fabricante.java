package dominio;

import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotEmpty;

/**
 * Representa um fabricante de veículos como Ford ou Chevrolet.
 * 
 * @author Alexandre
 */
public class Fabricante extends Entidade{
	
	@NotNull
	@NotEmpty
	private String descricao;
	
	public Fabricante(Integer id, String descricao){
		super(id);
		this.descricao = descricao;
	}
	
	public Fabricante() {
		super();
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
}
