package web;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import dominio.RepositorioModelo;
import dominio.RepositorioRelatorios;
import dominio.relatorios.ModeloMaisVendido;

@Controller
@RequestMapping("/relatorios/mais_vendidos")
public class RelatorioMaisVendidos {
	
	@Autowired
	private RepositorioModelo repositorioModelo;
	@Autowired
	private RepositorioRelatorios repositorioRelatorios;
	
	@RequestMapping
	public String inicio(Model model){
		model.addAttribute("periodoForm", new PeriodoForm());
		return "relatorios/mais_vendidos/inicio";
	}
	
	@RequestMapping("/ajax_validar_form")
	@ResponseBody
	public ValidationResponse validarFormulario(@Valid @ModelAttribute("periodoForm") PeriodoForm periodo, 
			BindingResult br){
		ValidationResponse vResponse;
		if(br.hasErrors()){
			vResponse = new ValidationResponse(br.getFieldErrors());
		}
		else{
			vResponse = new ValidationResponse();
			vResponse.setStatus(ValidationResponse.OK);
		}
		
		return vResponse;
	}
	
	@RequestMapping("/ajax_get_relatorio")
	public String getRelatorio(@ModelAttribute("periodoForm") PeriodoForm periodo, 
			Model model, BindingResult br){
		if(br.hasErrors())
			return null;
		
		List<ModeloMaisVendido> registros = repositorioRelatorios.
				modelosMaisVendidos(periodo.getInicio(), periodo.getFim());
		model.addAttribute("registros", registros);
		
		return "relatorios/mais_vendidos/relatorio";
	}
}
