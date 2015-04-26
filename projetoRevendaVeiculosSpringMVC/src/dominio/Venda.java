package dominio;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.NumberFormat;

/**
 * Representa uma venda de veículo realizada pela loja.
 *
 */
public class Venda extends Entidade{
	
	@NotNull
	@DateTimeFormat(pattern="dd/MM/yyyy")
	private Date data;
	@NotNull
	@DecimalMin("0.00")
	@NumberFormat(pattern="###,##0.00")
	private BigDecimal desconto;
	@DecimalMin("0.00")
	@NumberFormat(pattern="###,##0.00")
	private BigDecimal comissao; //em percentual
	private String obs;
	private StatusVenda status;
	private Veiculo veiculo;
	private Usuario vendedor;
	private Usuario autorizador;
	private List<PartePagamento> partesPagamento = new ArrayList<PartePagamento>();
	
	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}

	public BigDecimal getDesconto() {
		return desconto;
	}

	public void setDesconto(BigDecimal desconto) {
		this.desconto = desconto;
	}

	public BigDecimal getComissao() {
		return comissao;
	}

	public void setComissao(BigDecimal comissao) {
		this.comissao = comissao;
	}

	public String getObs() {
		return obs;
	}

	public void setObs(String obs) {
		this.obs = obs;
	}

	public StatusVenda getStatus() {
		return status;
	}

	public void setStatus(StatusVenda status) {
		this.status = status;
	}

	public Veiculo getVeiculo() {
		return veiculo;
	}

	public void setVeiculo(Veiculo veiculo) {
		this.veiculo = veiculo;
	}

	public Usuario getVendedor() {
		return vendedor;
	}

	public void setVendedor(Usuario vendedor) {
		this.vendedor = vendedor;
	}

	public Usuario getAutorizador() {
		return autorizador;
	}

	public void setAutorizador(Usuario autorizador) {
		this.autorizador = autorizador;
	}

	public List<PartePagamento> getPartesPagamento() {
		return partesPagamento;
	}

	public void setPartesPagamento(List<PartePagamento> partesPagamento) {
		this.partesPagamento = partesPagamento;
	}

	public BigDecimal getValorTotal(){
		BigDecimal total = new BigDecimal("0.00");
		for(PartePagamento parte : partesPagamento){
			total = total.add(parte.getQuantia());
		}
		return total;
	}
	
	public List<Compra> getComprasEnvolvidas(){
		ArrayList<Compra> compras = new ArrayList<>();
		for(PartePagamento p : partesPagamento){
			if(p.getCompraRelacionada() != null)
				compras.add(p.getCompraRelacionada());
		}
		return compras;
	}
}
