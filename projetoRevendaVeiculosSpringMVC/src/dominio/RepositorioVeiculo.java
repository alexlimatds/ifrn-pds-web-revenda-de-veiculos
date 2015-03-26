package dominio;

import java.util.Date;
import java.util.List;


public interface RepositorioVeiculo{
	
	/**
	 * @param obj
	 * @return o id gerado para o ve�culo.
	 */
	public Integer inserir(Veiculo v);
	
	public void atualizar(Veiculo v);
	
	public void excluir(Integer id);
	
	public List<Veiculo> todos();

	public Veiculo getPorPlaca(String placa);
	
	/**
	 * Retorna as datas de �ltima venda e �ltima compra deste ve�culo.
	 * @param idVeiculo
	 * @return 	um array com dois elementos, sendo que o primeiro corresponde � 
	 * 			data da �ltima compra e o segundo corresponde � data de �ltima venda 
	 * 			(quando n�o houver �ltima venda, o segundo elemento ser� nulo).
	 */
	public Date[] getDatasUltimasTransacoes(Integer idVeiculo);
	
	public Veiculo getPorId(Integer id);
}