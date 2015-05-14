package web;

import java.util.Date;

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
}
