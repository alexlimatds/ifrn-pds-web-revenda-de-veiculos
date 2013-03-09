package models;

import dao.DAOVeiculo;


public class RepositorioVeiculo extends Repositorio<Veiculo> {

	public RepositorioVeiculo(DAOVeiculo dao) {
		super(dao);
	}

	public Veiculo getPorPlaca(String placa){
		return ((DAOVeiculo)dao).getPorPlaca(placa);
	}
}