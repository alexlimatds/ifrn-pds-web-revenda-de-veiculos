package models;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Representa uma venda de veículo realizada pela loja.
 *
 */
public class Venda extends Model {
	
	public Date data;
	public BigDecimal desconto;
	public double comissao; //em percentual
	public String obs;
	public StatusVenda status;
	public Veiculo veiculo;
	public Usuario vendedor;
	public Usuario autorizador;
	public List<PartePagamento> partesPagamento = new ArrayList<PartePagamento>();
	
	public Venda() {
		super();
	}
	
	public BigDecimal valorTotal(){
		//TODO
		return null;
	}
}
