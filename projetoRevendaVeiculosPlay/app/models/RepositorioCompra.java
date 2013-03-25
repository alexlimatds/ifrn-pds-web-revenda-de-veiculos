package models;

import dao.DAO;
import dao.DAOCompra;

public class RepositorioCompra extends Repositorio<Compra> {
	
	public RepositorioCompra() {
		super(new DAOCompra());
	}
	
	public RepositorioCompra(DAO<Compra> dao) {
		super(dao);
	}
	
	@Override
	public Integer inserir(Compra c) {
		//A compra somente pode ser registrada caso o veículo não 
		//esteja em posse da loja.
		if(!c.veiculo.isEmPosseDaLoja()){
			return dao.inserir(c);
		}
		throw new ModeloException("Veículo em posse da loja. Não pode ser comprado.");
	}
}
