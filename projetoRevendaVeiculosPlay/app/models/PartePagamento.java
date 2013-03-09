package models;

import java.math.BigDecimal;

public class PartePagamento extends Model {
	
	public BigDecimal quantia;
	public FormaDePagamento formaPagamento;
	public Venda venda;
	public Compra compraRelacionada;
	
	public PartePagamento() {
		super();
	}
}
