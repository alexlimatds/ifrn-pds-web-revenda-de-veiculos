package dominio;

public class StatusVenda extends Entidade {
	
	private String descricao;
	
	public StatusVenda() {
		super();
	}
	
	public StatusVenda(Integer id, String descricao){
		super(id);
		this.descricao = descricao;
	}
}
