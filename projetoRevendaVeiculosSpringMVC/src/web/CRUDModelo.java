package web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import dominio.Fabricante;
import dominio.Modelo;
import dominio.RepositorioFabricante;
import dominio.RepositorioModelo;
import dominio.RepositorioTipoVeiculo;
import dominio.TipoVeiculo;

@Controller
@RequestMapping("/modelos")
public class CRUDModelo {
	
	@Autowired
	private RepositorioModelo repositorioModelo;
	@Autowired
	private RepositorioFabricante repositorioFabricante;
	@Autowired
	private RepositorioTipoVeiculo repositorioTipo;
	
	//Disponibiliza a lista de fabricantes para a view
	@ModelAttribute("fabricantes")
	public List<Fabricante> gerarListaFabricantes(){
		return repositorioFabricante.todos();
	}
	
	//Disponibiliza a lista de tipos de veículo para a view
	@ModelAttribute("tipos")
	public List<TipoVeiculo> gerarListaTiposVeiculo(){
		return repositorioTipo.todos();
	}
	
	@RequestMapping
	public String inicio(Model model){
		model.addAttribute("modelos", repositorioModelo.todos());
		return "modelos/inicio";
	}
	
	@RequestMapping("/novo")
	public String novo(Model model){
		model.addAttribute("modelo", new Modelo());
		model.addAttribute("titulo", "Novo Modelo");
		return "modelos/edicao";
	}
	
	@RequestMapping(value="/salvar", method=RequestMethod.POST)
	public String salvar(@Valid @ModelAttribute("modelo") Modelo modelo, 
			BindingResult br, @RequestParam("titulo") String titulo, 
			Model model, final RedirectAttributes rAttrs){
		if(br.hasErrors()){
			model.addAttribute("titulo", titulo);
			return "modelos/edicao";
		}
		
		try{
			if(modelo.getId() == null)
				repositorioModelo.inserir(modelo);
			else
				repositorioModelo.atualizar(modelo);
			rAttrs.addFlashAttribute("mensagem", "Modelo salvo com sucesso.");
		}catch(Exception ex){
			ex.printStackTrace();
			rAttrs.addFlashAttribute("mensagem", "Ocorreu um erro durante a operação.");
		}
		return "redirect:/modelos";
	}
	
	@RequestMapping("/excluir")
	@ResponseBody
	public Map<String, Object> excluir(@RequestParam("id") Integer idModelo, 
			final RedirectAttributes rAttrs){
		Map<String, Object> json = new HashMap<>();
		try{
			repositorioModelo.excluir(idModelo);
			json.put("mensagem", "Modelo excluído com sucesso.");
		}catch(Exception ex){
			ex.printStackTrace();
			json.put("mensagem", "Ocorreu um erro durante a operação.");
			json.put("erro", true);
		}
		return json;
	}
	
	@RequestMapping("/modelos")
	public String modelos(Model model){
		List<Modelo> modelos = repositorioModelo.todos();
		model.addAttribute("modelos", modelos);
		return "modelos/modelos";
	}
	
	@RequestMapping("/alterar")
	public String alterar(@RequestParam("id") Integer idModelo, Model model){
		Modelo modelos = repositorioModelo.getPorId(idModelo);
		if(modelos != null){
			model.addAttribute("modelo", modelos);
			model.addAttribute("titulo", "Alterar Modelo");
			return "modelos/edicao";
		}
		model.addAttribute("mensagem", "Modelo não encontrado");
		return "forward:/modelos";
	}
}
