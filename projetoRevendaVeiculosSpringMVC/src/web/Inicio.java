package web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class Inicio {
	
	@RequestMapping("/")
	public String inicio(){
		return "inicio";
	}
}
