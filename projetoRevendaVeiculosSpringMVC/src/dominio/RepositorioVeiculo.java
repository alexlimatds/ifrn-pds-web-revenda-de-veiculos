package dominio;

import java.util.List;


public interface RepositorioVeiculo{
	
	public final String PLACA="PLACA";
	public final String ANO="ANO";
	public final String ID_MODELO="ID_MODELO";
	
	/**
	 * @param v
	 * @return o id gerado para o veículo.
	 */
	public Integer inserir(Veiculo v);
	
	public void atualizar(Veiculo v, Foto novaFoto);
	
	public void excluir(Integer id);
	
	public List<Veiculo> todos();
	
	/**
	 * Realizar uma busca por determinado campo.
	 * @param campo uma das constantes desta interface.
	 * @param valor
	 * @return
	 */
	public List<Veiculo> getPor(String campo, Object valor);
	
	public Veiculo getPorId(Integer id);
	
	public Veiculo getPorPlaca(String placa);
	
	public Foto getFoto(Integer idVeiculo);
}