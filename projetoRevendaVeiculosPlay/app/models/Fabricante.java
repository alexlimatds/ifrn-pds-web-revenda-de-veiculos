package models;

/**
 * Representa um fabricante de veículos como Ford ou Chevrolet.
 */
public class Fabricante extends Model{
	
	public String descricao;
	
	public Fabricante(Integer id, String descricao){
		super(id);
		this.descricao = descricao;
	}
	
	public Fabricante() {
		super();
	}
}
