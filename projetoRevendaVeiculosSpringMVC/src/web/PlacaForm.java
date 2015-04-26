package web;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

public class PlacaForm {
	
	@Pattern(regexp="[A-Z]{3}\\d{4}")
	@NotNull
	private String placa;

	public String getPlaca() {
		return placa;
	}

	public void setPlaca(String placa) {
		this.placa = placa;
	}
}
