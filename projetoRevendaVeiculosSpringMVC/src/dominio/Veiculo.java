package dominio;

/**
 * Representa um veículo comercializado pela loja.
 */
public class Veiculo extends Entidade {

	private int anoFabricacao;
	private String placa;
	private String chassi;
	private byte[] foto;
	private int cilindradas;
	private Modelo modelo;

	public Veiculo() {
		super();
	}
	
	public int getAnoFabricacao() {
		return anoFabricacao;
	}

	public void setAnoFabricacao(int anoFabricacao) {
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

	public int getCilindradas() {
		return cilindradas;
	}

	public void setCilindradas(int cilindradas) {
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
		//TODO remover para algum service
		/*final RepositorioVeiculo repositorio = new RepositorioVeiculo();
		final Date[] datas = repositorio.getDatasUltimasTransacoes(id);
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
		}*/
		return false;
	}
}