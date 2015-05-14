package dominio;

import java.util.Date;
import java.util.List;

import dominio.relatorios.ModeloMaisVendido;

public interface RepositorioRelatorios {
	
	List<ModeloMaisVendido> modelosMaisVendidos(Date inicio, Date fim);
	
}
