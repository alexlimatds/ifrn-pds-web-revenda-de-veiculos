package dominio;

import java.math.BigDecimal;
import java.util.Date;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import dominio.excecoes.VeiculoNaoPertenceALoja;
import dominio.excecoes.VendaException;
import static org.junit.Assert.*;

public class ServiceVendaTest {
	
	private ServiceVenda service;
	
	@Before
	public void setUp(){
		service = new ServiceVenda();
	}
	
	@Test(expected=VendaException.class)
	public void testRegistrar_1(){
		//Testa pré-condição: venda sem pagamentos
		Venda venda = new Venda();
		
		service.registrar(venda);
	}
	
	@Test(expected=VendaException.class)
	public void testRegistrar_2(){
		//Testa pré-condição: pagamento com veículo sem compra relacionada
		Venda venda = new Venda();
		FormaDePagamento formaPgt = new FormaDePagamento(FormaDePagamento.ID_COM_VEICULO, "");
		PartePagamento pagamento = new PartePagamento();
		pagamento.setFormaPagamento(formaPgt);
		venda.getPartesPagamento().add(pagamento);
		pagamento.setVenda(venda);
		
		service.registrar(venda);
	}
	
	@Test(expected=VeiculoNaoPertenceALoja.class)
	public void testRegistrar_3(){
		//Testa pré-condição: veículo vendido não está em posse da loja
		Venda venda = new Venda();
		PartePagamento pagamento = criarPagamentoComVeiculo("12000.00");
		venda.getPartesPagamento().add(pagamento);
		Veiculo veiculo = Mockito.mock(Veiculo.class);
		venda.setVeiculo(veiculo);
		
		StatusVeiculo status = StatusVeiculo.NAO_PERTENCE_A_LOJA;
		Mockito.when(veiculo.getStatus()).thenReturn(status);
		
		service.registrar(venda);
	}
	
	@Test
	public void testRegistrar_4(){
		//Testa pós-condições
		Venda venda = new Venda();
		venda.setData(new Date());
		Integer idVendaGerado = 98;
		
		PartePagamento pagamento = criarPagamentoComVeiculo("8000.00");
		venda.getPartesPagamento().add(pagamento);
		Veiculo veiculo = Mockito.mock(Veiculo.class);
		venda.setVeiculo(veiculo);
		
		StatusVeiculo status = StatusVeiculo.DISPONIVEL_PARA_VENDA;
		Mockito.when(veiculo.getStatus()).thenReturn(status);
		
		RepositorioVenda repoVenda = Mockito.mock(RepositorioVenda.class);
		Mockito.when(repoVenda.inserir(venda)).thenReturn(idVendaGerado);
		service.setRepositorioVenda(repoVenda);
		
		Integer idVeiculoGerado = 102;
		RepositorioVeiculo repoVeiculo = Mockito.mock(RepositorioVeiculo.class);
		Mockito.when(repoVeiculo.inserir(veiculo)).thenReturn(idVeiculoGerado);
		service.setRepositorioVeiculo(repoVeiculo);
		
		ServiceCompra serviceCompra = Mockito.mock(ServiceCompra.class);
		Mockito.when(serviceCompra.registrar(pagamento.getCompraRelacionada(), idVeiculoGerado))
			.thenReturn(345);
		service.setServiceCompra(serviceCompra);
		
		Integer idCompraRetornado = service.registrar(venda);
		
		assertNotNull(venda.getComissao());
		assertEquals(venda.getData(), pagamento.getCompraRelacionada().getData());
		assertEquals(StatusVenda.AGUARDANDO_AUTORIZACAO, venda.getStatus());
		assertEquals(pagamento.getQuantia(), pagamento.getCompraRelacionada().getPreco());
		assertEquals(idVendaGerado, idCompraRetornado); 
	}
	
	private PartePagamento criarPagamentoComVeiculo(String quantia){
		FormaDePagamento formaPgt = new FormaDePagamento(FormaDePagamento.ID_COM_VEICULO, "");
		PartePagamento pagamento = new PartePagamento();
		pagamento.setQuantia(new BigDecimal(quantia));
		pagamento.setFormaPagamento(formaPgt);
		pagamento.setCompraRelacionada(new Compra());
		return pagamento;
	}
}
