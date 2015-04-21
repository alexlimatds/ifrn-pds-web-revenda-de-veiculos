package web;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import dominio.FormaDePagamento;
import dominio.PartePagamento;
import dominio.RepositorioFormasDePagamento;
import dominio.RepositorioVeiculo;
import dominio.StatusVeiculo;
import dominio.Veiculo;
import dominio.Venda;

@Controller
@RequestMapping("/vendas")
@SessionAttributes({"venda", "partePagamento"})
public class RegistrarVenda {
	
	@Autowired
	private RepositorioVeiculo repositorioVeiculo;
	@Autowired
	private RepositorioFormasDePagamento repositorioFormasDePagamento;
	private Map<Integer, FormaDePagamento> formasDePagamento;
	
	@PostConstruct
	public void init(){
		formasDePagamento = new HashMap<Integer, FormaDePagamento>();
		List<FormaDePagamento> lista = repositorioFormasDePagamento.todas();
		for(FormaDePagamento f : lista){
			formasDePagamento.put(f.getId(), f);
		}
	}
	
	@ModelAttribute("formasPagamento")
	public Collection<FormaDePagamento> gerarListaFormasDePagamento(){
		return formasDePagamento.values();
	}
	
	@RequestMapping("/iniciar")
	public String inicio(){
		return "vendas/iniciar";
	}
	
	@RequestMapping("/checar_placa")
	public String checarPlaca(@RequestParam String placa, Model model){
		Veiculo veiculo = repositorioVeiculo.getPorPlaca(placa);
		if(veiculo == null){
			model.addAttribute("erro", true);
			model.addAttribute("mensagem", "Veículo não cadastrado.");
			return inicio();
		}
		else{
			StatusVeiculo status = veiculo.getStatus();
			if(status == StatusVeiculo.DISPONIVEL_PARA_VENDA){
				Venda venda = new Venda();
				venda.setVeiculo(veiculo);
				model.addAttribute("venda", venda);
				model.addAttribute("partePagamento", new PartePagamento());
				return "vendas/registro";
			}
			else if(status == StatusVeiculo.EM_PROCESSO_DE_VENDA){
				model.addAttribute("erro", true);
				model.addAttribute("mensagem", "O veículo está sendo negociado em outra transação.");
				return inicio();
			}
			model.addAttribute("erro", true);
			model.addAttribute("mensagem", "O veículo não pertence à loja.");
			return inicio();
		}
	}
	
	@RequestMapping("/ajax_add_pagamento")
	public String addPagamento(@Valid @ModelAttribute("partePagamento") PartePagamento pagamento, 
			BindingResult br, @ModelAttribute("venda") Venda venda, Model model){
		if(br.hasErrors()){
			for(ObjectError oe : br.getAllErrors()){
				System.out.println("-->ADD PGT Binding error: " + oe.getDefaultMessage());
			}
		}
		else{
			FormaDePagamento formaPgt = formasDePagamento.get(pagamento.getFormaPagamento().getId());
			pagamento.setFormaPagamento(formaPgt);
			venda.getPartesPagamento().add(pagamento);
			model.addAttribute("partePagamento", new PartePagamento());
		}
		
		return "vendas/pagamentos";
	}
	
	@RequestMapping("/ajax_del_pagamento")
	@ResponseBody
	public Map<String, Object> delPagamento(@RequestParam("index") int index, 
			@ModelAttribute("venda") Venda venda){
		Map<String, Object> json = new HashMap<>();
		
		try{
			venda.getPartesPagamento().remove(index);
			json.put("status", "ok");
		}
		catch(RuntimeException ex){
			ex.printStackTrace();
			json.put("status", "falha");
		}
		
		return json;
	}
	
	@RequestMapping(value="/salvar", method=RequestMethod.POST)
	public String salvar(@Valid @ModelAttribute("venda") Venda venda, 
			BindingResult br, final RedirectAttributes rAttrs, Model model){
		if(br.hasErrors()){
			for(ObjectError oe : br.getAllErrors()){
				System.out.println("-->SALVAR Binding error: " + oe.getDefaultMessage());
			}
			return "vendas/registro";
		}
		
		try{
			//TODO
			//service.registrar(venda, idVeiculo);
			rAttrs.addFlashAttribute("mensagem", "Venda registrada com sucesso.");
			return "redirect:/";
		}catch(Exception ex){
			ex.printStackTrace();
			model.addAttribute("erro", true);
			model.addAttribute("mensagem", "Ocorreu um erro durante a operação.");
			return "vendas/registro";
		}
	}
}
