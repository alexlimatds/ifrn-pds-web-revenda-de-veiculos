package web;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import dominio.Compra;
import dominio.Foto;
import dominio.Modelo;
import dominio.RepositorioModelo;
import dominio.RepositorioVeiculo;
import dominio.ServiceCompra;
import dominio.StatusVeiculo;
import dominio.Veiculo;

@Controller
@RequestMapping("/compras")
public class RegistrarCompra {
	
	@Autowired
	private RepositorioVeiculo repositorioVeiculo;
	@Autowired
	private RepositorioModelo repositorioModelo;
	@Autowired
	private ServiceCompra service;
	
	@ModelAttribute("modelos")
	public List<Modelo> gerarListaModelos(){
		return repositorioModelo.todos();
	}
	
	@RequestMapping("/iniciar")
	public String inicio(Model model){
		model.addAttribute("placaForm", new PlacaForm());
		return "compras/iniciar";
	}
	
	@RequestMapping("/checar_placa")
	public String checarPlaca(@Valid @ModelAttribute("placaForm") PlacaForm placaForm, 
			BindingResult br,
			Model model){
		if(br.hasErrors()){
			return "compras/iniciar";
		}
			
		Veiculo veiculo = repositorioVeiculo.getPorPlaca(placaForm.getPlaca());
		if(veiculo == null){
			model.addAttribute("mensagem", "Veículo não cadastrado.");
			model.addAttribute("veiculo", new Veiculo(null, placaForm.getPlaca()));
			model.addAttribute("placaReadonly", true);
			return "compras/novo_veiculo";
		}
		else{
			if(veiculo.getStatus() != StatusVeiculo.NAO_PERTENCE_A_LOJA){
				model.addAttribute("erro", true);
				model.addAttribute("mensagem", "O veículo já pertence à loja.");
				return "compras/iniciar";
			}
			else{
				Compra compra = new Compra();
				compra.setVeiculo(veiculo);
				model.addAttribute("compra", compra);
				return "compras/registro";
			}
		}
	}
	
	@RequestMapping(value="/salvar_veiculo", method=RequestMethod.POST)
	public String salvarVeiculo(@Valid @ModelAttribute("veiculo") Veiculo veiculo, 
			BindingResult br, @RequestParam("arquivoFoto") MultipartFile arquivoFoto,
			Model model){
		if(br.hasErrors()){
			return "compras/novo_veiculo";
		}
		
		try{
			Foto foto = Util.multipartToFoto(arquivoFoto);
			veiculo.setFoto(foto);
			Integer idVeiculo = repositorioVeiculo.inserir(veiculo);
			veiculo = repositorioVeiculo.getPorId(idVeiculo);
			model.addAttribute("mensagem", "Veículo cadastrado com sucesso.");
			Compra c = new Compra();
			c.setVeiculo(veiculo);
			model.addAttribute("compra", c);
			return "compras/registro";
		}catch(Exception ex){
			ex.printStackTrace();
			model.addAttribute("mensagem", "Ocorreu um erro.");
			return "compras/novo_veiculo";
		}
	}
	
	@RequestMapping(value="/salvar", method=RequestMethod.POST)
	public String salvarCompra(@Valid @ModelAttribute("compra") Compra compra, 
			BindingResult br, final RedirectAttributes rAttrs, Model model){
		Integer idVeiculo = compra.getVeiculo().getId();
		if(br.hasErrors()){
			compra.setVeiculo(repositorioVeiculo.getPorId(idVeiculo));
			return "compras/registro";
		}
		
		try{
			service.registrar(compra, idVeiculo);
			rAttrs.addFlashAttribute("mensagem", "Compra registrada com sucesso.");
			return "redirect:/";
		}catch(Exception ex){
			ex.printStackTrace();
			model.addAttribute("erro", true);
			model.addAttribute("mensagem", "Ocorreu um erro durante a operação.");
			return "compras/registro";
		}
	}
}
