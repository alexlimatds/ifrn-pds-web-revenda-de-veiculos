package dominio;

public enum StatusVeiculo{
	
	DISPONIVEL_PARA_VENDA(1, "DISPONÍVEL PARA VENDA"), 
	EM_PROCESSO_DE_VENDA(2, "EM PROCESSO DE VENDA"), 
	NAO_PERTENCE_A_LOJA(3, "NÃO PERTENCE À LOJA");
	
	private String descricao;
	private Integer id;
	
	StatusVeiculo(Integer id, String descricao) {
		this.id = id;
		this.descricao = descricao;
	}

	public String getDescricao() {
		return descricao;
	}

	public Integer getId() {
		return id;
	}
}
