package models;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Representa uma compra de veículo realizada pela loja.
 *
 */
public class Compra extends Model {
	public Date data;
	public BigDecimal preco;
	public String obs;
	public Veiculo veiculo;
	
	public Compra() {
		super();
	}
	
}
