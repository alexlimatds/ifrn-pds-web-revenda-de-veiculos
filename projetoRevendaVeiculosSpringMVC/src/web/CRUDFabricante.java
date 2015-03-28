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

import dominio.Fabricante;
import dominio.RepositorioFabricante;

@Controller
@RequestMapping("/fabricantes")
public class CRUDFabricante {
	
	@Autowired
	private RepositorioFabricante repositorio;
	
	@RequestMapping
	public String inicio(Model model){
		model.addAttribute("fabricantes", repositorio.todos());
		return "fabricantes/inicio";
	}
	
	@RequestMapping("/novo")
	public String novo(Model model){
		model.addAttribute("fabricante", new Fabricante());
		model.addAttribute("titulo", "Novo Fabricante");
		return "fabricantes/edicao";
	}
	
	@RequestMapping(value="/salvar", method=RequestMethod.POST)
	public String salvar(@Valid @ModelAttribute("fabricante") Fabricante fabricante, 
			BindingResult br, @RequestParam("titulo") String titulo, 
			Model model, final RedirectAttributes rAttrs){
		if(br.hasErrors()){
			model.addAttribute("titulo", titulo);
			return "fabricantes/edicao";
		}
		
		try{
			if(fabricante.getId() == null)
				repositorio.inserir(fabricante);
			else
				repositorio.atualizar(fabricante);
			rAttrs.addFlashAttribute("mensagem", "Fabricante salvo com sucesso.");
		}catch(Exception ex){
			ex.printStackTrace();
			rAttrs.addFlashAttribute("erro", true);
			rAttrs.addFlashAttribute("mensagem", "Ocorreu um erro durante a operação.");
		}
		return "redirect:/fabricantes";
	}
	
	@RequestMapping("/excluir")
	public String excluir(@RequestParam("id") Integer idFabricante, 
			final RedirectAttributes rAttrs){
		try{
			repositorio.excluir(idFabricante);
			rAttrs.addFlashAttribute("mensagem", "Fabricante excluído com sucesso.");
		}catch(Exception ex){
			ex.printStackTrace();
			rAttrs.addFlashAttribute("erro", true);
			rAttrs.addFlashAttribute("mensagem", "Ocorreu um erro durante a operação.");
		}
		return "redirect:/fabricantes";
	}
	
	@RequestMapping("/alterar")
	public String novo(@RequestParam("id") Integer idFabricante, Model model, 
			final RedirectAttributes rAttrs){
		Fabricante f = repositorio.getPorId(idFabricante);
		if(f != null){
			model.addAttribute("fabricante", f);
			model.addAttribute("titulo", "Alterar Fabricante");
			return "fabricantes/edicao";
		}
		rAttrs.addFlashAttribute("erro", true);
		rAttrs.addFlashAttribute("mensagem", "Fabricante não encontrado");
		return "redirect:/fabricantes";
	}
}
