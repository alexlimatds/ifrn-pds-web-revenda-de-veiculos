package models;

public class StatusVenda extends Model {
	
	public String descricao;
	
	public StatusVenda() {
		super();
	}
	
	public StatusVenda(Integer id, String descricao){
		super(id);
		this.descricao = descricao;
	}
}
