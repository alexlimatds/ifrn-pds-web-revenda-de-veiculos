package dominio;

import java.util.Date;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

/**
 * Representa um veículo comercializado pela loja.
 */
public class Veiculo extends Entidade {
	
	@Min(1900)
	@NotNull
	private Integer anoFabricacao;
	@Pattern(regexp="[A-Z]{3}\\d{4}")
	@NotNull
	private String placa;
	private String chassi;
	private Foto foto;
	@Min(50)
	private Integer cilindradas;
	@NotNull
	private Modelo modelo;
	
	private RepositorioVeiculo repositorio;
	
	public Veiculo() {
		super();
	}
	
	public Veiculo(Integer id, String placa){
		super(id);
		this.placa = placa;
	}
	
	public void setRepositorio(RepositorioVeiculo repositorio) {
		this.repositorio = repositorio;
	}

	public Integer getAnoFabricacao() {
		return anoFabricacao;
	}

	public void setAnoFabricacao(Integer anoFabricacao) {
		this.anoFabricacao = anoFabricacao;
	}

	public String getPlaca() {
		return placa;
	}

	public void setPlaca(String placa) {
		this.placa = placa;
	}

	public String getChassi() {
		return chassi;
	}

	public void setChassi(String chassi) {
		this.chassi = chassi;
	}

	public Foto getFoto() {
		return foto;
	}

	public void setFoto(Foto foto) {
		this.foto = foto;
	}

	public String getMimeTypeFoto() {
		if(foto != null)
			return foto.getMimeType();
		return null;
	}

	public Integer getCilindradas() {
		return cilindradas;
	}

	public void setCilindradas(Integer cilindradas) {
		this.cilindradas = cilindradas;
	}

	public Modelo getModelo() {
		return modelo;
	}

	public void setModelo(Modelo modelo) {
		this.modelo = modelo;
	}

	/**
	 * Retorna true caso o veículo esteja em posse da loja e false em caso contrário.
	 * @return
	 */
	public boolean isEmPosseDaLoja() {
		final Date[] datas = repositorio.getDatasUltimasTransacoes(getId());
		final Date dataCompra = datas[0];
		final Date dataVenda = datas[1];
		if(dataCompra == null){
			//veículo nunca foi comprado pela loja, portanto não está em posse da loja
			return false;
		}
		else if(dataVenda == null){
			//veículo foi comprado pela loja mas não foi vendido
			return true;
		}
		else{
			//se dataCompra for posterior a dataVenda, veículo está em posse da loja
			return dataCompra.getTime() > dataVenda.getTime();
		}
	}
}