package dominio;

import java.util.Date;
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
	
	/**
	 * Retorna as datas de última venda e última compra deste veículo.
	 * @param idVeiculo
	 * @return 	um array com dois elementos, sendo que o primeiro corresponde à 
	 * 			data da última compra e o segundo corresponde à data de última venda 
	 * 			(quando não houver última venda, o segundo elemento será nulo).
	 */
	public Date[] getDatasUltimasTransacoes(Integer idVeiculo);
	
	public Foto getFoto(Integer idVeiculo);
}