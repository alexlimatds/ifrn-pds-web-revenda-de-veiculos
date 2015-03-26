package dominio;

import org.springframework.beans.factory.annotation.Autowired;

public class ServiceCompra {
	
	@Autowired
	private RepositorioCompra repositorio;
	
	public Integer registrar(Compra c, Veiculo veiculo) {
		//A compra somente pode ser registrada caso o ve�culo n�o 
		//esteja em posse da loja.
		if(!veiculo.isEmPosseDaLoja()){
			c.setVeiculo( veiculo );
			return repositorio.inserir(c);
		}
		throw new ModeloException("Ve�culo em posse da loja. N�o pode ser comprado.");
	}
}
