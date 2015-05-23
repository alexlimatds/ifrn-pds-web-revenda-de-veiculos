package web;

import java.util.ArrayList;
import java.util.List;

import org.springframework.validation.FieldError;

/**
 * Define um tipo de objeto usado para armazenar informações a respeito da 
 * validação de um POJO. Basicamente, será utilizada para realizar a validação 
 * de formulários web através de Ajax e da API Bean Validation.
 * Consulte: https://spring.io/blog/2012/08/29/integrating-spring-mvc-with-jquery-for-validation-rules
 * 
 * @author Alexandre
 */
public class ValidationResponse {
	
	public static final String OK = "OK";
	public static final String FAIL = "FAIL";
	private String status;
	private List<ErrorMessage> errorMessageList;
	
	public ValidationResponse() {
	}
	
	public ValidationResponse(List<FieldError> errorList) {
		if(errorList != null && !errorList.isEmpty()){
			setStatus(FAIL);
			List<ErrorMessage> errorMessages = new ArrayList<>();
			for(FieldError fe : errorList){
				ErrorMessage eMsg = new ErrorMessage(fe.getField(), fe.getDefaultMessage());
				errorMessages.add(eMsg);
			}
			setErrorMessageList(errorMessages);
		}
	}
	
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}

	public List<ErrorMessage> getErrorMessageList() {
		return errorMessageList;
	}
	public void setErrorMessageList(List<ErrorMessage> errorMessageList) {
		this.errorMessageList = errorMessageList;
	}
}
