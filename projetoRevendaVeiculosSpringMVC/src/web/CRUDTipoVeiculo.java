package web;

import java.util.HashMap;
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

import dominio.RepositorioTipoVeiculo;
import dominio.TipoVeiculo;

@Controller
@RequestMapping("/tipos_veiculo")
public class CRUDTipoVeiculo {
	
	@Autowired
	private RepositorioTipoVeiculo repositorio;
	
	@RequestMapping
	public String inicio(Model model){
		model.addAttribute("tipos", repositorio.todos());
		return "tipos_veiculo/inicio";
	}
	
	@RequestMapping("/novo")
	public String novo(Model model){
		model.addAttribute("tipo", new TipoVeiculo());
		model.addAttribute("titulo", "Novo Tipo de Ve�culo");
		return "tipos_veiculo/edicao";
	}
	
	@RequestMapping(value="/salvar", method=RequestMethod.POST)
	public String salvar(@Valid @ModelAttribute("tipo") TipoVeiculo tipo, 
			BindingResult br, @RequestParam("titulo") String titulo, 
			Model model, final RedirectAttributes rAttrs){
		if(br.hasErrors()){
			model.addAttribute("titulo", titulo);
			return "tipos_veiculo/edicao";
		}
		
		try{
			if(tipo.getId() == null)
				repositorio.inserir(tipo);
			else
				repositorio.atualizar(tipo);
			rAttrs.addFlashAttribute("mensagem", "Tipo de Ve�culo salvo com sucesso.");
		}catch(Exception ex){
			ex.printStackTrace();
			rAttrs.addFlashAttribute("mensagem", "Ocorreu um erro durante a opera��o.");
		}
		return "redirect:/tipos_veiculo";
	}
	
	@RequestMapping("/excluir")
	@ResponseBody
	public Map<String, Object> excluir(@RequestParam("id") Integer idTipo, 
			final RedirectAttributes rAttrs){
		Map<String, Object> json = new HashMap<>();
		try{
			repositorio.excluir(idTipo);
			json.put("mensagem", "Tipo de Ve�culo exclu�do com sucesso.");
		}catch(Exception ex){
			ex.printStackTrace();
			json.put("mensagem", "Ocorreu um erro durante a opera��o.");
			json.put("erro", true);
		}
		return json;
	}
	
	@RequestMapping("/alterar")
	public String novo(@RequestParam("id") Integer idTipoVeiculo, Model model){
		TipoVeiculo tipo = repositorio.getPorId(idTipoVeiculo);
		if(tipo != null){
			model.addAttribute("tipo", tipo);
			model.addAttribute("titulo", "Alterar Tipo de Ve�culo");
			return "tipos_veiculo/edicao";
		}
		model.addAttribute("mensagem", "Tipo de Ve�culo n�o encontrado");
		return "forward:/tipos_veiculo";
	}
}
