package dominio;

import java.util.Date;
import java.util.List;


public interface RepositorioVeiculo{
	
	public final String PLACA="PLACA";
	public final String ANO="ANO";
	public final String ID_MODELO="ID_MODELO";
	
	/**
	 * @param v
	 * @return o id gerado para o ve�culo.
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
	 * Retorna as datas de �ltima venda e �ltima compra deste ve�culo.
	 * @param idVeiculo
	 * @return 	um array com dois elementos, sendo que o primeiro corresponde � 
	 * 			data da �ltima compra e o segundo corresponde � data de �ltima venda 
	 * 			(quando n�o houver �ltima venda, o segundo elemento ser� nulo).
	 */
	public Date[] getDatasUltimasTransacoes(Integer idVeiculo);
	
	public Foto getFoto(Integer idVeiculo);
}