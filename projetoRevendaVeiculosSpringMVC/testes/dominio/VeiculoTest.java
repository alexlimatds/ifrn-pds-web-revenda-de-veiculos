package dominio;

import static org.junit.Assert.assertEquals;

import java.text.SimpleDateFormat;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

public class VeiculoTest {
	
	private SimpleDateFormat fmt = new SimpleDateFormat("dd/MM/yyyy");
	private RepositorioCompra repositorioCompra;
	private RepositorioVenda repositorioVenda;
	
	@Before
	public void setUp(){
		repositorioCompra = Mockito.mock(RepositorioCompra.class);
		repositorioVenda = Mockito.mock(RepositorioVenda.class);
	}
	
	private Veiculo criar(Integer idVeiculo){
		Veiculo veiculo = new Veiculo(idVeiculo, "");
		veiculo.setRepositorioCompra(repositorioCompra);
		veiculo.setRepositorioVenda(repositorioVenda);
		return veiculo;
	}
	
	@Test
	public void testGetStatus_1() throws Exception{
		//Status: NÃO PERTENCE À LOJA
		//Não há registro de transações.
		Integer idVeiculo = 185;
		Veiculo veiculo = criar(idVeiculo);
		
		Compra ultimaCompra = null;
		Venda ultimaVenda = null;
		Mockito.when(repositorioCompra.getUltimaCompraDoVeiculo(idVeiculo)).thenReturn(ultimaCompra);
		Mockito.when(repositorioVenda.getUltimaVendaDoVeiculo(idVeiculo)).thenReturn(ultimaVenda);
		
		assertEquals(StatusVeiculo.NAO_PERTENCE_A_LOJA, veiculo.getStatus());
	}
	
	@Test
	public void testGetStatus_2() throws Exception{
		//Status: NÃO PERTENCE À LOJA
		//Foi comprado e depois vendido 
		Integer idVeiculo = 185;
		Veiculo veiculo = criar(idVeiculo);
		
		Compra ultimaCompra = new Compra();
		ultimaCompra.setData(fmt.parse("08/05/2014"));
		Venda ultimaVenda = new Venda();
		ultimaVenda.setData(fmt.parse("12/06/2014"));
		ultimaVenda.setStatus(StatusVenda.FINALIZADA);
		Mockito.when(repositorioCompra.getUltimaCompraDoVeiculo(idVeiculo)).thenReturn(ultimaCompra);
		Mockito.when(repositorioVenda.getUltimaVendaDoVeiculo(idVeiculo)).thenReturn(ultimaVenda);
		
		assertEquals(StatusVeiculo.NAO_PERTENCE_A_LOJA, veiculo.getStatus());
	}
	
	@Test
	public void testGetStatus_3(){
		//Status: DISPONÍVEL PARA A VENDA
		//Veiculo foi comprado e ainda não foi vendido
		Integer idVeiculo = 185;
		Veiculo veiculo = criar(idVeiculo);
		
		Compra ultimaCompra = new Compra();
		Venda ultimaVenda = null;
		Mockito.when(repositorioCompra.getUltimaCompraDoVeiculo(idVeiculo)).thenReturn(ultimaCompra);
		Mockito.when(repositorioVenda.getUltimaVendaDoVeiculo(idVeiculo)).thenReturn(ultimaVenda);
		
		assertEquals(StatusVeiculo.DISPONIVEL_PARA_VENDA, veiculo.getStatus());
	}
	
	@Test
	public void testGetStatus_4() throws Exception{
		//Status: DISPONÍVEL PARA A VENDA
		//Veiculo foi comprado, vendido e comprado novamente 
		Integer idVeiculo = 185;
		Veiculo veiculo = criar(idVeiculo);
		
		Venda ultimaVenda = new Venda();
		ultimaVenda.setData(fmt.parse("12/06/2012"));
		ultimaVenda.setStatus(StatusVenda.FINALIZADA);
		Compra ultimaCompra = new Compra();
		ultimaCompra.setData(fmt.parse("05/03/2015"));
		Mockito.when(repositorioCompra.getUltimaCompraDoVeiculo(idVeiculo)).thenReturn(ultimaCompra);
		Mockito.when(repositorioVenda.getUltimaVendaDoVeiculo(idVeiculo)).thenReturn(ultimaVenda);
		
		assertEquals(StatusVeiculo.DISPONIVEL_PARA_VENDA, veiculo.getStatus());
	}
	
	@Test
	public void testGetStatus_5() throws Exception{
		//Status: EM PROCESSO DE VENDA
		//Foi comprado e está em negociação 
		Integer idVeiculo = 185;
		Veiculo veiculo = criar(idVeiculo);
		
		Compra ultimaCompra = new Compra();
		ultimaCompra.setData(fmt.parse("08/05/2014"));
		Venda ultimaVenda = new Venda();
		ultimaVenda.setData(fmt.parse("12/06/2014"));
		ultimaVenda.setStatus(StatusVenda.AGUARDANDO_AUTORIZACAO);
		Mockito.when(repositorioCompra.getUltimaCompraDoVeiculo(idVeiculo)).thenReturn(ultimaCompra);
		Mockito.when(repositorioVenda.getUltimaVendaDoVeiculo(idVeiculo)).thenReturn(ultimaVenda);
		
		assertEquals(StatusVeiculo.EM_PROCESSO_DE_VENDA, veiculo.getStatus());
	}
	
	@Test
	public void testGetStatus_6() throws Exception{
		//Status: EM PROCESSO DE VENDA
		//Veiculo foi comprado, vendido, comprado e está sendo negociado 
		Integer idVeiculo = 185;
		Veiculo veiculo = criar(idVeiculo);
		
		Venda ultimaVenda = new Venda();
		ultimaVenda.setData(fmt.parse("12/06/2012"));
		ultimaVenda.setStatus(StatusVenda.AUTORIZADA);
		Compra ultimaCompra = new Compra();
		ultimaCompra.setData(fmt.parse("05/03/2015"));
		Mockito.when(repositorioCompra.getUltimaCompraDoVeiculo(idVeiculo)).thenReturn(ultimaCompra);
		Mockito.when(repositorioVenda.getUltimaVendaDoVeiculo(idVeiculo)).thenReturn(ultimaVenda);
		
		assertEquals(StatusVeiculo.EM_PROCESSO_DE_VENDA, veiculo.getStatus());
	}
}
