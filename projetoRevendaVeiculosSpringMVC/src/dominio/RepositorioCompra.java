package dominio;

public interface RepositorioCompra{
	
	Integer inserir(Compra c);
	
	public void excluir(Integer id);
	
	public Compra getUltimaCompraDoVeiculo(Integer idVeiculo);
}
