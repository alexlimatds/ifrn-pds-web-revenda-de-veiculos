package dominio;

import java.util.List;

public interface RepositorioCompra{
	
	Integer inserir(Compra c);
	
	public void atualizar(Compra c);
	
	public void excluir(Integer id);
	
	public List<Compra> todos();
	
	public Compra getPorId(Integer id);
}
