package dominio.relatorios;

/**
 * Representa um registro do relatório de modelos mais vendidos.
 * 
 * @author Alexandre
 */
public class ModeloMaisVendido {
	private Integer ordem;
	private String modelo;
	private String fabricante;
	private Integer unidadesComercializadas;

	public ModeloMaisVendido(Integer ordem, String modelo, String fabricante,
			Integer unidadesComercializadas) {
		this.ordem = ordem;
		this.modelo = modelo;
		this.fabricante = fabricante;
		this.unidadesComercializadas = unidadesComercializadas;
	}

	public Integer getOrdem() {
		return ordem;
	}

	public String getModelo() {
		return modelo;
	}

	public String getFabricante() {
		return fabricante;
	}

	public Integer getUnidadesComercializadas() {
		return unidadesComercializadas;
	}
}
