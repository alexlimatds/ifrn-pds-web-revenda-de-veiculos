package dominio;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Representa uma venda de veículo realizada pela loja.
 *
 */
public class Venda extends Entidade{
	
	private Date data;
	private BigDecimal desconto;
	private double comissao; //em percentual
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

	public double getComissao() {
		return comissao;
	}

	public void setComissao(double comissao) {
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

	public BigDecimal valorTotal(){
		//TODO
		return null;
	}
}
