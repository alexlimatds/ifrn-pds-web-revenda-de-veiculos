package dominio;

import java.util.List;

public interface RepositorioModelo {
	
	public Integer inserir(Modelo m);

	public void atualizar(Modelo m);

	public void excluir(Integer id);

	public List<Modelo> todos();
	
	public Modelo getPorId(Integer id);
}
