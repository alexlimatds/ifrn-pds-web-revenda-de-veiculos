package dominio;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import dominio.excecoes.VeiculoEmPosseDaLoja;

public class ServiceCompraTest {
	
	private ServiceCompra service;
	private RepositorioVeiculo repoVeiculo;
	private RepositorioCompra repoCompra;
	
	@Before
	public void setUp(){
		service = new ServiceCompra();
		repoVeiculo = Mockito.mock(RepositorioVeiculo.class);
		service.setRepositorioVeiculo(repoVeiculo);
		repoCompra = Mockito.mock(RepositorioCompra.class);
		service.setRepositorioCompra(repoCompra);
	}
	
	@Test(expected=VeiculoEmPosseDaLoja.class)
	public void testRegistrarCompra_1(){
		//Veículo em posse da loja
		Compra compra = new Compra();
		Integer idVeiculo = 108;
		
		Veiculo veiculo = Mockito.mock(Veiculo.class);
		Mockito.when(veiculo.getStatus()).thenReturn(StatusVeiculo.DISPONIVEL_PARA_VENDA);
		Mockito.when(repoVeiculo.getPorId(idVeiculo)).thenReturn(veiculo);
		
		service.registrar(compra, idVeiculo);
	}
	
	@Test(expected=VeiculoEmPosseDaLoja.class)
	public void testRegistrarCompra_2(){
		//Veículo em posse da loja
		Compra compra = new Compra();
		Integer idVeiculo = 108;
		
		Veiculo veiculo = Mockito.mock(Veiculo.class);
		Mockito.when(veiculo.getStatus()).thenReturn(StatusVeiculo.EM_PROCESSO_DE_VENDA);
		Mockito.when(repoVeiculo.getPorId(idVeiculo)).thenReturn(veiculo);
		
		service.registrar(compra, idVeiculo);
	}
	
	@Test
	public void testRegistrarCompra_3(){
		//Veículo não está em posse da loja
		Compra compra = new Compra();
		Integer idVeiculo = 108;
		
		Veiculo veiculo = Mockito.mock(Veiculo.class);
		Mockito.when(veiculo.getStatus()).thenReturn(StatusVeiculo.NAO_PERTENCE_A_LOJA);
		Mockito.when(repoVeiculo.getPorId(idVeiculo)).thenReturn(veiculo);
		
		service.registrar(compra, idVeiculo);
	}
}
