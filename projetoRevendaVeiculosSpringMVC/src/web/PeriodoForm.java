package web;

import java.util.Date;

import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;

public class PeriodoForm {
	
	@NotNull
	@DateTimeFormat(pattern="ss/MM/yyyy")
	private Date inicio;
	
	@NotNull
	@DateTimeFormat(pattern="ss/MM/yyyy")
	private Date fim;

	public Date getInicio() {
		return inicio;
	}

	public void setInicio(Date inicio) {
		this.inicio = inicio;
	}

	public Date getFim() {
		return fim;
	}

	public void setFim(Date fim) {
		this.fim = fim;
	}
	
	@AssertTrue(message="fim deve ser posterior à início")
	private boolean isValid(){
		//Ignora quando um dos campos é nulo para que a validação seja 
		//baseada nas suas anotações @NotNull
		if(inicio == null || fim == null)
			return true;
		return fim.compareTo(inicio) >= 0;
	}
}
