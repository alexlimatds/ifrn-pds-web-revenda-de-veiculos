package models;

import java.util.Date;

import play.data.validation.Constraints.Required;

/**
 * Representa um veículo comercializado pela loja.
 *
 */
public class Veiculo extends Model {
	@Required
	public int anoFabricacao;
	@Required
	public String placa;
	public String chassi;
	public byte[] foto;
	public int cilindradas;
	public Modelo modelo;

	public Veiculo() {
		super();
	}

	/**
	 * Retorna true caso o veículo esteja em posse da loja e false em caso contrário.
	 * @return
	 */
	public boolean isEmPosseDaLoja() {
		final RepositorioVeiculo repositorio = new RepositorioVeiculo();
		final Date[] datas = repositorio.getDatasUltimasTransacoes(id);
		final Date dataCompra = datas[0];
		final Date dataVenda = datas[1];
		if(dataCompra == null){
			//veículo nunca foi comprado pela loja, portanto não está em posse da loja
			return false;
		}
		else if(dataVenda == null){
			//veículo foi comprado pela loja mas não foi vendido
			return true;
		}
		else{
			//se dataCompra for posterior a dataVenda, veículo está em posse da loja
			return dataCompra.getTime() > dataVenda.getTime();
		}
	}
}