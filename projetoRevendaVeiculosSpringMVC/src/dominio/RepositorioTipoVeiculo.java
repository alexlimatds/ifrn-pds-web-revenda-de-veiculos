package dominio;

import java.util.List;

public interface RepositorioTipoVeiculo {
	
	public Integer inserir(TipoVeiculo t);

	public void atualizar(TipoVeiculo t);

	public void excluir(Integer idTipo);

	public List<TipoVeiculo> todos();
	
	public TipoVeiculo getPorId(Integer id);
}
