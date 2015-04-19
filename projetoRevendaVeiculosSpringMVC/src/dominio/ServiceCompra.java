package dominio;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dominio.excecoes.VeiculoEmPosseDaLoja;

@Service
public class ServiceCompra {
	
	@Autowired
	private RepositorioCompra repositorioCompra;
	void setRepositorioCompra(RepositorioCompra repositorioCompra) {
		this.repositorioCompra = repositorioCompra;
	}
	@Autowired
	private RepositorioVeiculo repositorioVeiculo;
	public void setRepositorioVeiculo(RepositorioVeiculo repositorioVeiculo) {
		this.repositorioVeiculo = repositorioVeiculo;
	}
	
	public Integer registrar(Compra c, Integer idVeiculo) {
		//A compra somente pode ser registrada caso o veículo não 
		//pertença à loja.
		Veiculo v = repositorioVeiculo.getPorId(idVeiculo);
		if(v.getStatus() == StatusVeiculo.NAO_PERTENCE_A_LOJA){
			c.setVeiculo(v);
			return repositorioCompra.inserir(c);
		}
		throw new VeiculoEmPosseDaLoja();
	}
}
