package models;

import java.util.Date;

import dao.DAOVeiculo;


public class RepositorioVeiculo extends Repositorio<Veiculo> {
	
	public static DAOVeiculo daoVeiculo = new DAOVeiculo();
	
	public RepositorioVeiculo(){
		super(daoVeiculo);
	}

	public Veiculo getPorPlaca(String placa){
		return ((DAOVeiculo)dao).getPorPlaca(placa);
	}
	
	public Date[] getDatasUltimasTransacoes(Integer idVeiculo){
		Date[] datas = ((DAOVeiculo)dao).getDatasUltimasTransacoes(idVeiculo);
		if(datas == null){ //não há veículo com o id informado
			throw new ModeloException("Não há veículo com o id informado");
		}
		return datas;
	}
}