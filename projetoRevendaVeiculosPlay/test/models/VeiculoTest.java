package models;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import dao.DAOVeiculo;

public class VeiculoTest {
	private DAOVeiculo dao;
	@Before
	public void setUp(){
		dao = Mockito.mock(DAOVeiculo.class);
		RepositorioVeiculo.daoVeiculo = dao;
	}
	@Test
	public void testIsEmPosseDaLoja1(){
		//Sem datas de compra e venda. Logo, o veículo não está em posse da loja.
		Integer idVeiculo = 130;
		Date datas[] = new Date[2];
		Mockito.when(dao.getDatasUltimasTransacoes(idVeiculo)).thenReturn(datas);
		
		Veiculo v = new Veiculo();
		v.id = idVeiculo;
		Assert.assertFalse(v.isEmPosseDaLoja());
	}
	@Test
	public void testIsEmPosseDaLoja2(){
		//Apenas data de compra. Logo, o veículo está em posse da loja.
		Integer idVeiculo = 130;
		Date datas[] = new Date[]{new Date(), null};
		Mockito.when(dao.getDatasUltimasTransacoes(idVeiculo)).thenReturn(datas);
		
		Veiculo v = new Veiculo();
		v.id = idVeiculo;
		Assert.assertTrue(v.isEmPosseDaLoja());
	}
	@Test
	public void testIsEmPosseDaLoja3() throws ParseException{
		//Data de compra anterior à data de venda. Logo, o veículo não está em posse da loja.
		Integer idVeiculo = 130;
		SimpleDateFormat fmt = new SimpleDateFormat("dd/MM/yyyy");
		Date datas[] = new Date[]{fmt.parse("01/02/2012"), fmt.parse("13/03/2012")};
		Mockito.when(dao.getDatasUltimasTransacoes(idVeiculo)).thenReturn(datas);
		
		Veiculo v = new Veiculo();
		v.id = idVeiculo;
		Assert.assertFalse(v.isEmPosseDaLoja());
	}
	@Test
	public void testIsEmPosseDaLoja4() throws ParseException{
		//Data de compra posterior à data de venda. Logo, o veículo está em posse da loja.
		Integer idVeiculo = 130;
		SimpleDateFormat fmt = new SimpleDateFormat("dd/MM/yyyy");
		Date datas[] = new Date[]{fmt.parse("13/03/2012"), fmt.parse("01/02/2012")};
		Mockito.when(dao.getDatasUltimasTransacoes(idVeiculo)).thenReturn(datas);
		
		Veiculo v = new Veiculo();
		v.id = idVeiculo;
		Assert.assertTrue(v.isEmPosseDaLoja());
	}
}
