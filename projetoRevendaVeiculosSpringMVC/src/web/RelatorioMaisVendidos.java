package web;

import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

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
	
	@RequestMapping("/ajax_relatorio")
	public String gerarRelatorio(@Valid @ModelAttribute("periodoForm") PeriodoForm periodo, 
			BindingResult br, HttpServletResponse response, Model model){
		String viewName;
		HttpStatus httpStatus;
		if(br.hasErrors()){
			viewName = "relatorios/mais_vendidos/campos";
			httpStatus = HttpStatus.UNPROCESSABLE_ENTITY;
		}
		else{
			viewName = "relatorios/mais_vendidos/relatorio";
			httpStatus = HttpStatus.OK;
			List<ModeloMaisVendido> registros = repositorioRelatorios.
					modelosMaisVendidos(periodo.getInicio(), periodo.getFim());
			model.addAttribute("registros", registros);
		}
		
		response.setStatus(httpStatus.value());
		return viewName;
	}
}
