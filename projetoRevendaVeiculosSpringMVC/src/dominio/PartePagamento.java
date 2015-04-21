package dominio;

import java.math.BigDecimal;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.NumberFormat;

public class PartePagamento extends Entidade {
	
	@NotNull
	@DecimalMin("0.00")
	@NumberFormat(pattern="###,###.00")
	private BigDecimal quantia;
	@NotNull
	private FormaDePagamento formaPagamento;
	private Venda venda;
	private Compra compraRelacionada;
	
	public PartePagamento() {
		super();
	}

	public BigDecimal getQuantia() {
		return quantia;
	}

	public void setQuantia(BigDecimal quantia) {
		this.quantia = quantia;
	}

	public FormaDePagamento getFormaPagamento() {
		return formaPagamento;
	}

	public void setFormaPagamento(FormaDePagamento formaPagamento) {
		this.formaPagamento = formaPagamento;
	}

	public Venda getVenda() {
		return venda;
	}

	public void setVenda(Venda venda) {
		this.venda = venda;
	}

	public Compra getCompraRelacionada() {
		return compraRelacionada;
	}

	public void setCompraRelacionada(Compra compraRelacionada) {
		this.compraRelacionada = compraRelacionada;
	}
}
