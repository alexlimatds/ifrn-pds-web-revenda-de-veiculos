package web;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import dominio.Compra;
import dominio.RepositorioVeiculo;
import dominio.ServiceCompra;
import dominio.Veiculo;

@Controller
@RequestMapping("/compras")
public class RegistrarCompra {
	
	@Autowired
	private RepositorioVeiculo repositorioVeiculo;
	@Autowired
	private ServiceCompra service;
	
	@RequestMapping("/iniciar")
	public String inicio(){
		return "compras/iniciar";
	}
	
	@RequestMapping("/checar_placa")
	public String checarPlaca(@RequestParam String placa, 
			Model model){
		Veiculo veiculo = repositorioVeiculo.getPorPlaca(placa);
		if(veiculo == null){
			model.addAttribute("erro", true);
			model.addAttribute("mensagem", "Veículo não cadastrado.");
			return inicio();
		}
		else{
			if(veiculo.isEmPosseDaLoja(repositorioVeiculo)){
				model.addAttribute("erro", true);
				model.addAttribute("mensagem", "O veículo já pertence à loja.");
				return inicio();
			}
			else{
				model.addAttribute("veiculo", veiculo);
				model.addAttribute("compra", new Compra());
				return "compras/registro";
			}
		}
	}
	
	@RequestMapping(value="/salvar", method=RequestMethod.POST)
	public String salvar(@Valid @ModelAttribute("compra") Compra compra, 
			BindingResult br, @RequestParam("id_veiculo") Integer idVeiculo, 
			final RedirectAttributes rAttrs, Model model){
		if(br.hasErrors()){
			model.addAttribute("veiculo", repositorioVeiculo.getPorId(idVeiculo));
			return "compras/registro";
		}
		
		try{
			service.registrar(compra, idVeiculo);
			rAttrs.addFlashAttribute("mensagem", "Compra registrada com sucesso.");
			return "redirect:/";
		}catch(Exception ex){
			ex.printStackTrace();
			model.addAttribute("veiculo", repositorioVeiculo.getPorId(idVeiculo));
			model.addAttribute("erro", true);
			model.addAttribute("mensagem", "Ocorreu um erro durante a operação.");
			return "compras/registro";
		}
	}
}
