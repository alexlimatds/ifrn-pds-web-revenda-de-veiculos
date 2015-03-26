package dominio;

import java.util.List;

public interface RepositorioFabricante {
	
	public Integer inserir(Fabricante f);

	public void atualizar(Fabricante fab);

	public void excluir(Integer idFabricante);

	public List<Fabricante> todos();
	
	public Fabricante getPorId(Integer id);
}
