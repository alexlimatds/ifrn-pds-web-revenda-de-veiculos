package dominio;

import java.util.Date;
import java.util.List;


public interface RepositorioVeiculo{
	
	/**
	 * @param obj
	 * @return o id gerado para o veículo.
	 */
	public Integer inserir(Veiculo v);
	
	public void atualizar(Veiculo v);
	
	public void excluir(Integer id);
	
	public List<Veiculo> todos();

	public Veiculo getPorPlaca(String placa);
	
	/**
	 * Retorna as datas de última venda e última compra deste veículo.
	 * @param idVeiculo
	 * @return 	um array com dois elementos, sendo que o primeiro corresponde à 
	 * 			data da última compra e o segundo corresponde à data de última venda 
	 * 			(quando não houver última venda, o segundo elemento será nulo).
	 */
	public Date[] getDatasUltimasTransacoes(Integer idVeiculo);
	
	public Veiculo getPorId(Integer id);
}