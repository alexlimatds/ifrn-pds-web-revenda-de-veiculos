package models;

/**
 * Representa uma forma de pagamento utilizada pelos clientes da loja.
 *
 */
public class FormaDePagamento extends Model {
	
	public String descricao;
	
	public FormaDePagamento() {
		super();
	}
	
	public FormaDePagamento(Integer id, String descricao){
		super(id);
		this.descricao = descricao;
	}
}
