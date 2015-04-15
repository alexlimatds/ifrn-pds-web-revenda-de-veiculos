package dominio;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.Test;
import org.mockito.Mockito;

public class VeiculoTest {
	
	private SimpleDateFormat fmt = new SimpleDateFormat("dd/MM/yyyy");
	
	@Test
	public void testIsEmPosseDaLoja_1() throws Exception{
		//Veiculo nunca esteve em posse da loja
		Integer idVeiculo = 185;
		Veiculo veiculo = new Veiculo(idVeiculo, "");
		
		Date dataCompra = null;
		Date dataVenda = null;
		Date[] datas = new Date[]{dataCompra, dataVenda};
		RepositorioVeiculo repositorio = Mockito.mock(RepositorioVeiculo.class);
		Mockito.when(repositorio.getDatasUltimasTransacoes(idVeiculo)).thenReturn(datas);
		veiculo.setRepositorio(repositorio);
		
		assertFalse( veiculo.isEmPosseDaLoja() );
	}
	
	@Test
	public void testIsEmPosseDaLoja_2() throws Exception{
		//Veiculo está em posse da loja e nunca foi vendido
		Integer idVeiculo = 185;
		Veiculo veiculo = new Veiculo(idVeiculo, "");
		
		Date dataCompra = fmt.parse("12/10/2014");
		Date dataVenda = null;
		Date[] datas = new Date[]{dataCompra, dataVenda};
		RepositorioVeiculo repositorio = Mockito.mock(RepositorioVeiculo.class);
		Mockito.when(repositorio.getDatasUltimasTransacoes(idVeiculo)).thenReturn(datas);
		veiculo.setRepositorio(repositorio);
		
		assertTrue( veiculo.isEmPosseDaLoja() );
	}
	
	@Test
	public void testIsEmPosseDaLoja_3() throws Exception{
		//Veiculo não está em posse da loja: foi comprado e depois vendido 
		Integer idVeiculo = 185;
		Veiculo veiculo = new Veiculo(idVeiculo, "");
		
		Date dataCompra = fmt.parse("08/05/2014");
		Date dataVenda = fmt.parse("12/06/2014");
		Date[] datas = new Date[]{dataCompra, dataVenda};
		RepositorioVeiculo repositorio = Mockito.mock(RepositorioVeiculo.class);
		Mockito.when(repositorio.getDatasUltimasTransacoes(idVeiculo)).thenReturn(datas);
		veiculo.setRepositorio(repositorio);
		
		assertFalse( veiculo.isEmPosseDaLoja() );
	}
	
	@Test
	public void testIsEmPosseDaLoja_4() throws Exception{
		//Veiculo está em posse da loja: foi comprado, vendido e comprado novamente 
		Integer idVeiculo = 185;
		Veiculo veiculo = new Veiculo(idVeiculo, "");
		
		Date dataCompra = fmt.parse("05/03/2015");
		Date dataVenda = fmt.parse("12/06/2012");
		Date[] datas = new Date[]{dataCompra, dataVenda};
		RepositorioVeiculo repositorio = Mockito.mock(RepositorioVeiculo.class);
		Mockito.when(repositorio.getDatasUltimasTransacoes(idVeiculo)).thenReturn(datas);
		veiculo.setRepositorio(repositorio);
		
		assertTrue( veiculo.isEmPosseDaLoja() );
	}
}
