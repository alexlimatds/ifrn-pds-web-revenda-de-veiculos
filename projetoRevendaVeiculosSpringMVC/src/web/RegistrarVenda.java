package web;

import java.security.Principal;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import dominio.Compra;
import dominio.FormaDePagamento;
import dominio.PartePagamento;
import dominio.RepositorioFormasDePagamento;
import dominio.RepositorioModelo;
import dominio.RepositorioVeiculo;
import dominio.ServiceVenda;
import dominio.StatusVeiculo;
import dominio.Usuario;
import dominio.Veiculo;
import dominio.Venda;

@Controller
@RequestMapping("/vendas")
@SessionAttributes({"venda"})
public class RegistrarVenda {
	
	@Autowired
	private RepositorioVeiculo repositorioVeiculo;
	@Autowired
	private RepositorioModelo repositorioModelo;
	@Autowired
	private RepositorioFormasDePagamento repositorioFormasDePagamento;
	@Autowired
	private ServiceVenda service;
	private Map<Integer, FormaDePagamento> formasDePagamento;
	private FormaDePagamento pagamentoComVeiculo;
	
	@PostConstruct
	public void init(){
		formasDePagamento = new HashMap<Integer, FormaDePagamento>();
		List<FormaDePagamento> lista = repositorioFormasDePagamento.todas();
		for(FormaDePagamento f : lista){
			if(f.getId() != FormaDePagamento.ID_COM_VEICULO)
				formasDePagamento.put(f.getId(), f);
			else
				pagamentoComVeiculo = f;
		}
	}
	
	@ModelAttribute("formasPagamento")
	public Collection<FormaDePagamento> gerarListaFormasDePagamento(){
		return formasDePagamento.values();
	}
	
	//**** Passos ****
	
	@RequestMapping("/iniciar")
	public String passo_1(Model model, 
			@RequestParam(required=false) String placa){
		PlacaForm form = new PlacaForm();
		if(placa != null)
			form.setPlaca(placa);
		model.addAttribute("placaForm", form);
		return "vendas/iniciar";
	}
	
	@RequestMapping("/passo_2")
	public String passo_2(Model model){
		model.addAttribute("partePagamento", new PartePagamento());
		return "vendas/dados_venda";
	}
	
	@RequestMapping("/passo_3")
	public String passo_3(Model model){
		model.addAttribute("placaForm", new PlacaForm());
		return "vendas/compras_envolvidas";
	}
	
	@RequestMapping(value="/resumo")
	public String resumo(){
		return "vendas/registro";
	}
	
	//**** Operações ****
	
	@RequestMapping("/checar_placa")
	public String checarPlaca(@Valid @ModelAttribute("placaForm") PlacaForm placaForm, 
			BindingResult br,
			Model model){
		if(br.hasErrors()){
			return "vendas/iniciar";
		}
		
		Veiculo veiculo = repositorioVeiculo.getPorPlaca(placaForm.getPlaca());
		if(veiculo == null){
			model.addAttribute("erro", true);
			model.addAttribute("mensagem", "Veículo não cadastrado.");
			return "forward:/vendas/iniciar";
		}
		else{
			StatusVeiculo status = veiculo.getStatus();
			if(status == StatusVeiculo.DISPONIVEL_PARA_VENDA){
				Venda venda = new Venda();
				venda.setVeiculo(veiculo);
				model.addAttribute("venda", venda);
				return "forward:/vendas/passo_2";
			}
			else if(status == StatusVeiculo.EM_PROCESSO_DE_VENDA){
				model.addAttribute("erro", true);
				model.addAttribute("mensagem", "O veículo está sendo negociado em outra transação.");
				return "forward:/vendas/iniciar";
			}
			model.addAttribute("erro", true);
			model.addAttribute("mensagem", "O veículo não pertence à loja.");
			return "forward:/vendas/iniciar";
		}
	}
	
	@RequestMapping("/ajax_add_pagamento")
	public String addPagamento(@Valid @ModelAttribute("partePagamento") PartePagamento pagamento, 
			BindingResult br, ModelMap model){
		if(br.hasErrors()){
			for(ObjectError oe : br.getAllErrors()){
				System.out.println("-->ADD PGT Binding error: " + oe.getDefaultMessage());
			}
		}
		else{
			FormaDePagamento formaPgt = formasDePagamento.get(pagamento.getFormaPagamento().getId());
			pagamento.setFormaPagamento(formaPgt);
			Venda venda = (Venda)model.get("venda");
			venda.getPartesPagamento().add(pagamento);
			model.addAttribute("partePagamento", new PartePagamento());
		}
		
		return "vendas/pagamentos";
	}
	
	@RequestMapping("/ajax_del_pagamento")
	@ResponseBody
	public Map<String, Object> delPagamento(@RequestParam("index") int index, 
			ModelMap model){
		Map<String, Object> json = new HashMap<>();
		
		try{
			Venda venda = (Venda)model.get("venda");
			venda.getPartesPagamento().remove(index);
			json.put("status", "ok");
		}
		catch(RuntimeException ex){
			ex.printStackTrace();
			json.put("status", "falha");
		}
		
		return json;
	}
	
	@RequestMapping(value="/submeter_venda", method=RequestMethod.POST)
	public String submeter_venda(@Valid @ModelAttribute("venda") Venda venda, 
			BindingResult br, Model model){
		if(br.hasErrors()){
			for(ObjectError oe : br.getAllErrors()){
				System.out.println("-->Binding error: " + oe.getDefaultMessage());
			}
			model.addAttribute("partePagamento", new PartePagamento());
			return "vendas/dados_venda";
		}
		
		return "forward:/vendas/passo_3";
	}
	
	@RequestMapping(value="/checar_placa_compra", method=RequestMethod.POST)
	public String checarPlacaCompra(@Valid @ModelAttribute("placaForm") PlacaForm placa, 
			BindingResult br, Model model){
		if(br.hasErrors()){
			return "vendas/compras_envolvidas";
		}
		
		Veiculo veiculo = repositorioVeiculo.getPorPlaca(placa.getPlaca());
		if(veiculo != null && veiculo.getStatus() != StatusVeiculo.NAO_PERTENCE_A_LOJA){
			model.addAttribute("erro", true);
			model.addAttribute("mensagem", "O veículo já pertence à loja.");
			model.addAttribute("placaForm", placa);
			return "vendas/compras_envolvidas";
		}
		if(veiculo == null){
			veiculo = new Veiculo(null, placa.getPlaca());
			model.addAttribute("mensagem", "Veículo não cadastrado.");
		}
		Compra c = new Compra();
		c.setData(new Date()); //somente para passar na validação
		c.setVeiculo(veiculo);
		model.addAttribute("novaCompra", c);
		model.addAttribute("modelos", repositorioModelo.todos());
		return "vendas/nova_compra";
	}
	
	@RequestMapping(value="/add_compra", method=RequestMethod.POST)
	public String addCompra(@Valid @ModelAttribute("novaCompra") Compra novaCompra, 
			BindingResult br, ModelMap model){
		if(br.hasErrors()){
			return "vendas/nova_compra";
		}
		
		Integer idVeiculo = novaCompra.getVeiculo().getId();
		Veiculo veiculo = novaCompra.getVeiculo();
		if(idVeiculo != null)
			veiculo = repositorioVeiculo.getPorId(idVeiculo);
		else{
			veiculo.setModelo( repositorioModelo.getPorId(veiculo.getModelo().getId()) );
		}
		novaCompra.setVeiculo(veiculo);
		Venda venda = (Venda)model.get("venda");
		PartePagamento partePgt = new PartePagamento();
		partePgt.setCompraRelacionada(novaCompra);
		partePgt.setFormaPagamento(pagamentoComVeiculo);
		partePgt.setQuantia(novaCompra.getPreco());
		partePgt.setVenda(venda);
		venda.getPartesPagamento().add(partePgt);
		model.addAttribute("mensagem", "Veículo adicionado com sucesso.");
		model.addAttribute("placaForm", new PlacaForm());
		return "vendas/compras_envolvidas";
	}
	
	@RequestMapping(value="/salvar_venda")
	public String salvarVenda(RedirectAttributes rAttrs, 
			ModelMap model, 
			Principal principal, 
			SessionStatus sessionStatus){
		Usuario usuarioLogado = (Usuario)((Authentication)principal).getDetails();
		Venda venda = (Venda)model.get("venda");
		venda.setVendedor(usuarioLogado);
		service.registrar(venda);
		sessionStatus.setComplete();
		rAttrs.addFlashAttribute("mensagem", "Venda registrada com sucesso.");
		return "redirect:/";
	}
}
