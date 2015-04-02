package web;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import dominio.Modelo;
import dominio.RepositorioModelo;
import dominio.RepositorioVeiculo;
import dominio.Veiculo;

@Controller
@RequestMapping("/veiculos")
public class CRUDVeiculo {
	
	@Autowired
	private RepositorioVeiculo repositorioVeiculo;
	@Autowired
	private RepositorioModelo repositorioModelo;
	
	//Disponibiliza a lista de modelos para a view
	@ModelAttribute("modelos")
	public List<Modelo> gerarListaModelos(){
		return repositorioModelo.todos();
	}
	
	@RequestMapping
	public String inicio(Model model){
		//model.addAttribute("veiculos", repositorioVeiculo.todos());
		return "veiculos/inicio";
	}
	
	//Para a requisição Ajax de atualização de tabela
	@RequestMapping("/ajax_veiculos")
	public String veiculos(@RequestParam("campo") String campo, 
			@RequestParam("valor") String valor, Model model){
		List<Veiculo> veiculos = repositorioVeiculo.getPor(campo, valor);
		model.addAttribute("veiculos", veiculos);
		return "veiculos/veiculos";
	}
	
	@RequestMapping("/novo")
	public String novo(Model model){
		model.addAttribute("veiculo", new Veiculo());
		model.addAttribute("titulo", "Novo Veículo");
		return "veiculos/edicao";
	}
	
	@RequestMapping(value="/salvar", method=RequestMethod.POST)
	public String salvar(@Valid @ModelAttribute("veiculo") Veiculo veiculo, 
			BindingResult br, @RequestParam("titulo") String titulo, 
			@RequestParam("arquivoFoto") MultipartFile arquivoFoto,
			Model model, final RedirectAttributes rAttrs){
		if(br.hasErrors()){
			model.addAttribute("titulo", titulo);
			return "veiculos/edicao";
		}
		
		try{
			if(arquivoFoto != null){
				veiculo.setFoto(arquivoFoto.getBytes());
				veiculo.setMimeTypeFoto(arquivoFoto.getContentType());
			}
			if(veiculo.getId() == null)
				repositorioVeiculo.inserir(veiculo);
			else{
				if(arquivoFoto == null){
					Veiculo doBanco = repositorioVeiculo.getPorId(veiculo.getId());
					veiculo.setFoto(doBanco.getFoto());
					veiculo.setMimeTypeFoto(doBanco.getMimeTypeFoto());
				}
				repositorioVeiculo.atualizar(veiculo);
			}
			rAttrs.addFlashAttribute("mensagem", "Veículo salvo com sucesso.");
		}catch(Exception ex){
			ex.printStackTrace();
			rAttrs.addFlashAttribute("erro", "erro");
			rAttrs.addFlashAttribute("mensagem", "Ocorreu um erro durante a operação.");
		}
		return "redirect:/veiculos";
	}
	
	@RequestMapping("/excluir")
	@ResponseBody
	public Map<String, Object> excluir(@RequestParam("id") Integer idVeiculo){
		Map<String, Object> json = new HashMap<>();
		try{
			repositorioVeiculo.excluir(idVeiculo);
			json.put("mensagem", "Veículo excluído com sucesso.");
		}catch(Exception ex){
			ex.printStackTrace();
			json.put("mensagem", "Ocorreu um erro durante a operação.");
			json.put("erro", true);
		}
		return json;
	}
	
	@RequestMapping("/alterar")
	public String alterar(@RequestParam("id") Integer idVeiculo, Model model){
		Veiculo veiculo = repositorioVeiculo.getPorId(idVeiculo);
		if(veiculo != null){
			model.addAttribute("veiculo", veiculo);
			model.addAttribute("titulo", "Alterar Veículo");
			return "veiculos/edicao";
		}
		model.addAttribute("mensagem", "Veículo não encontrado");
		return "forward:/veiculos";
	}
	
	@RequestMapping("/ajax_foto")
	public ResponseEntity<byte[]> foto(@RequestParam("id") Integer idVeiculo){
		Veiculo veiculo = repositorioVeiculo.getPorId(idVeiculo);
		HttpHeaders headers = new HttpHeaders();
		String[] tokens = veiculo.getMimeTypeFoto().split("/");
		MediaType mimeType = new MediaType(tokens[0], tokens[1]);
		headers.setContentType(mimeType);
		return new ResponseEntity<>(veiculo.getFoto(), headers, HttpStatus.OK);
	}
}
