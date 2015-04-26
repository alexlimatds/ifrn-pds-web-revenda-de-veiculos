package dominio;

import java.util.List;

public interface RepositorioFormasDePagamento {
	public List<FormaDePagamento> todas();

	public FormaDePagamento getPagamentoComVeiculo();
}
