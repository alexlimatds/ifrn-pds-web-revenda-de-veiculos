package dominio;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

/**
 * Representa um ve�culo comercializado pela loja.
 */
public class Veiculo extends Entidade {
	
	@Min(1900)
	@NotNull
	private Integer anoFabricacao;
	@Pattern(regexp="[A-Z]{3}\\d{4}")
	@NotNull
	private String placa;
	private String chassi;
	private byte[] foto;
	private String mimeTypeFoto;
	@Min(50)
	private Integer cilindradas;
	@NotNull
	private Modelo modelo;

	public Veiculo() {
		super();
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

	public byte[] getFoto() {
		return foto;
	}

	public void setFoto(byte[] foto) {
		this.foto = foto;
	}

	public String getMimeTypeFoto() {
		return mimeTypeFoto;
	}

	public void setMimeTypeFoto(String mimeTypeFoto) {
		this.mimeTypeFoto = mimeTypeFoto;
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
	 * Retorna true caso o ve�culo esteja em posse da loja e false em caso contr�rio.
	 * @return
	 */
	public boolean isEmPosseDaLoja() {
		//TODO remover para algum service
		/*final RepositorioVeiculo repositorio = new RepositorioVeiculo();
		final Date[] datas = repositorio.getDatasUltimasTransacoes(id);
		final Date dataCompra = datas[0];
		final Date dataVenda = datas[1];
		if(dataCompra == null){
			//ve�culo nunca foi comprado pela loja, portanto n�o est� em posse da loja
			return false;
		}
		else if(dataVenda == null){
			//ve�culo foi comprado pela loja mas n�o foi vendido
			return true;
		}
		else{
			//se dataCompra for posterior a dataVenda, ve�culo est� em posse da loja
			return dataCompra.getTime() > dataVenda.getTime();
		}*/
		return false;
	}
}