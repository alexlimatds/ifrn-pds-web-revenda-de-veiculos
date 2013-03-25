package models;

import java.util.Date;

import dao.DAOVeiculo;


public class RepositorioVeiculo extends Repositorio<Veiculo> {

	public RepositorioVeiculo(){
		super(new DAOVeiculo());
	}
	
	public RepositorioVeiculo(DAOVeiculo dao) {
		super(dao);
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