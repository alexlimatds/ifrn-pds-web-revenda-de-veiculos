package dominio;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import dominio.excecoes.VeiculoNaoPertenceALoja;
import dominio.excecoes.VendaException;

@Service
public class ServiceVenda {
	
	@Autowired
	private RepositorioVenda repositorioVenda;
	@Autowired
	private RepositorioVeiculo repositorioVeiculo;
	@Autowired
	private ServiceCompra serviceCompra;
	
	public void setRepositorioVeiculo(RepositorioVeiculo repositorioVeiculo) {
		this.repositorioVeiculo = repositorioVeiculo;
	}

	public void setServiceCompra(ServiceCompra serviceCompra) {
		this.serviceCompra = serviceCompra;
	}

	public void setRepositorioVenda(RepositorioVenda repositorioVenda) {
		this.repositorioVenda = repositorioVenda;
	}
	
	@Transactional(propagation=Propagation.REQUIRED)
	public Integer registrar(Venda venda){
		//Verifica as pré-condições
		if(venda.getPartesPagamento().size() == 0)
			throw new VendaException("Venda sem pagamentos");
		for(PartePagamento pgt : venda.getPartesPagamento()){
			if(pgt.getFormaPagamento().isComVeiculo() && 
			   pgt.getCompraRelacionada() == null){
				throw new VendaException("Pagamento com veículo sem compra relacionada");
			}
		}
		if(venda.getVeiculo().getStatus() == StatusVeiculo.NAO_PERTENCE_A_LOJA)
			throw new VeiculoNaoPertenceALoja();
		
		//preparo
		venda.setComissao(new BigDecimal("3.00")); //comissão padrão
		for(PartePagamento pgt : venda.getPartesPagamento()){
			if(pgt.getFormaPagamento().isComVeiculo()){
				Compra c = pgt.getCompraRelacionada();
				c.setData(venda.getData());
				c.setPreco(pgt.getQuantia());
			}
		}
		venda.setStatus(StatusVenda.AGUARDANDO_AUTORIZACAO);
		
		//persistência
		for(Compra c : venda.getComprasEnvolvidas()){
			Integer idVeiculo = c.getVeiculo().getId();
			if(idVeiculo == null){
				idVeiculo = repositorioVeiculo.inserir(c.getVeiculo());
				c.getVeiculo().setId(idVeiculo);
			}
			Integer idCompra = serviceCompra.registrar(c, idVeiculo);
			c.setId(idCompra);
		}
		Integer idGeradoVenda = repositorioVenda.inserir(venda);
		
		return idGeradoVenda;
	}
}
