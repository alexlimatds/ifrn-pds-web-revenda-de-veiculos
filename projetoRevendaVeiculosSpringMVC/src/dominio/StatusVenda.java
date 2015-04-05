package dominio;

public enum StatusVenda{
	
	AGUARDANDO_AUTORIZACAO(1, "AGUARDANDO AUTORIZA��O"), 
	AUTORIZADA(2, "AUTORIZADA"), 
	FINALIZADA(3, "FINALIZADA");
	
	private String descricao;
	private Integer id;
	
	StatusVenda(Integer id, String descricao) {
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
