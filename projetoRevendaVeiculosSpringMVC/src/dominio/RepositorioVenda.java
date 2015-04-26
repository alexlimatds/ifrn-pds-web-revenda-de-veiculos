package dominio;

public interface RepositorioVenda {
	
	public Venda getUltimaVendaDoVeiculo(Integer idVeiculo);
	
	public Integer inserir(Venda v);
}
