package dominio;

/**
 * Representa uma forma de pagamento utilizada pelos clientes da loja.
 */
public class FormaDePagamento extends Entidade {
	
	private String descricao;
	
	public FormaDePagamento() {
		super();
	}
	
	public FormaDePagamento(Integer id, String descricao){
		super(id);
		this.descricao = descricao;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
}