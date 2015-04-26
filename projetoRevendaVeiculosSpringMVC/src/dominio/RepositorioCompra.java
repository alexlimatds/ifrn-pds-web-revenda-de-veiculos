package dominio;

public interface RepositorioCompra{
	
	public Integer inserir(Compra c);
	
	public void excluir(Integer id);
	
	public Compra getUltimaCompraDoVeiculo(Integer idVeiculo);
}
