package dominio;

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
	
	private RepositorioCompra repositorioCompra;
	private RepositorioVenda repositorioVenda;
	
	public Veiculo() {
		super();
	}
	
	public Veiculo(Integer id, String placa){
		super(id);
		this.placa = placa;
	}
	
	public void setRepositorioCompra(RepositorioCompra repositorioCompra) {
		this.repositorioCompra = repositorioCompra;
	}

	public void setRepositorioVenda(RepositorioVenda repositorioVenda) {
		this.repositorioVenda = repositorioVenda;
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
	 * Indica o status do veículo. Este status é determinado pelas transações 
	 * realizadas com o veículo.
	 * 
	 * @return
	 */
	public StatusVeiculo getStatus(){
		Compra ultimaCompra = repositorioCompra.getUltimaCompraDoVeiculo(getId());
		Venda ultimaVenda = repositorioVenda.getUltimaVendaDoVeiculo(getId());
		if(ultimaCompra == null && ultimaVenda == null)
			return StatusVeiculo.NAO_PERTENCE_A_LOJA;
		if(ultimaCompra != null && ultimaVenda == null)
			return StatusVeiculo.DISPONIVEL_PARA_VENDA;
		if(ultimaCompra != null && ultimaVenda != null){
			if(ultimaVenda.getStatus() == StatusVenda.FINALIZADA){
				if(ultimaVenda.getData().after(ultimaCompra.getData()))
					return StatusVeiculo.NAO_PERTENCE_A_LOJA;
				else
					return StatusVeiculo.DISPONIVEL_PARA_VENDA;
			}
			else{
				return StatusVeiculo.EM_PROCESSO_DE_VENDA;
			}
		}
		
		throw new IllegalStateException("Situação não prevista");
	}
}