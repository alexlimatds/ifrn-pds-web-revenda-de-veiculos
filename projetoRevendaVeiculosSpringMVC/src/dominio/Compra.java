package dominio;

import java.math.BigDecimal;
import java.util.Date;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.NumberFormat;

/**
 * Representa uma compra de veículo realizada pela loja.
 */
public class Compra extends Entidade {
	@NotNull
	@DateTimeFormat(pattern="ss/MM/yyyy")
	private Date data;
	@DecimalMin("0.00")
	@NotNull
	@NumberFormat(pattern="###,###.00")
	private BigDecimal preco;
	private String obs;
	@NotNull
	private Veiculo veiculo;
	
	public Compra() {
		super();
	}

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}

	public BigDecimal getPreco() {
		return preco;
	}

	public void setPreco(BigDecimal preco) {
		this.preco = preco;
	}

	public String getObs() {
		return obs;
	}

	public void setObs(String obs) {
		this.obs = obs;
	}

	public Veiculo getVeiculo() {
		return veiculo;
	}

	public void setVeiculo(Veiculo veiculo) {
		this.veiculo = veiculo;
	}
}
