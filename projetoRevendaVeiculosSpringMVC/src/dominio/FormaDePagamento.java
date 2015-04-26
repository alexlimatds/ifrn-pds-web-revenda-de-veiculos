package dominio;

/**
 * Representa uma forma de pagamento utilizada pelos clientes da loja.
 */
public class FormaDePagamento extends Entidade {
	
	public static final Integer ID_COM_VEICULO = 3;
	
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

	public boolean isComVeiculo() {
		return ID_COM_VEICULO.equals(getId());
	}
}
