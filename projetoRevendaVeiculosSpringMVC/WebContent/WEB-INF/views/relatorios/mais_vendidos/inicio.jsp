<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<!DOCTYPE html>
<html lang="pt">
<head>
  <title>Relatório de modelos mais vendidos</title>
  <c:url var="baseUrl" value="/relatorios/mais_vendidos"/>
  <c:url var="resources" value="/resources"/>
  <link href="${resources}/js/bootstrap-datepicker.css" rel="stylesheet" />
  <script src="${resources}/js/bootstrap-datepicker.js"></script>
  <script src="${resources}/js/bootstrap-datepicker.pt-BR.min.js" charset="UTF-8"></script>
  <script>
    $(document).ready(function(){
	  $(document).ajaxError(function(event, jqxhr, settings){
		atualizarAlert('Aconteceu um erro ao realizar a requisição.', true);
  	  });
  	  
	  $('.campoData').datepicker({
        format: "dd/mm/yyyy", 
        language: "pt-BR"
      });
	  
	  $('#meuForm').submit(function(event){
		var $meuForm = $('#meuForm');
		var parametros = $meuForm.serialize();
		var url = '${baseUrl}/ajax_validar_form';
	  	$.get(url, parametros, function(response){
	  	  $('#divMsg').hide();
	  	  $meuForm.find('.text-danger').empty();
	  	  
	  	  if(response.status == 'FAIL'){
	  		for (var i = 0; i < response.errorMessageList.length; i++) {
	  		  var item = response.errorMessageList[i];
	  		  if(item.fieldName != 'valid'){
  	  		    var $errorSpan = $('#' + item.fieldName + 'ErrorMsg');
  	  		    $errorSpan.addClass('text-danger');
  	  		    $errorSpan.text(item.message);
	  		  }
	  		  else{
	  		    atualizarAlert(item.message, true);
	  		  }
	  		}
	  	  }
	  	  else{
	  	    var url2 = '${baseUrl}/ajax_get_relatorio';
	  	    $.get(url2, parametros, function(html){
	  	      $('#divRelatorio').html(html);
	  	    });
	  	  }
	  	});
		
		event.preventDefault();
  	    return false;
  	  });
    });
    
    function submeter(){
      
    }
  </script>
</head>
<body>
  <div class="container">
    <h2>Relatório de modelos mais vendidos</h2>
    
    <c:set var="alertClass" value="${(!erro?'alert alert-success':'alert alert-danger')}"></c:set>
    <div class="alert alert-dismissible" role="alert" id="divMsg" ${(not empty erro?'erro':'')}>
      <button type="button" class="close" data-hide="alert">
        <span aria-hidden="true">&times;</span>
      </button>
      <p id="pMsg">${mensagem}</p>
    </div>
      
    <form:form action="${baseUrl}/ajax_validar_form" 
          method="get" 
          role="form" 
          modelAttribute="periodoForm" 
          id="meuForm">
          
      <div class="form-group">
        <label for="inicio">DATA INICIAL</label>
        <form:input id="inicio" path="inicio" class="form-control campoData"/>
        <span id="inicioErrorMsg">
        </span>
      </div>
      
      <div class="form-group">
        <label for="fim">DATA FINAL</label>
        <form:input id="fim" path="fim" class="form-control campoData"/>
        <span id="fimErrorMsg">
        </span>
      </div>
      
      <div class="form-group">
        <button type="submit" class="btn btn-default" id="botaoForm">
          <span class="glyphicon glyphicon-list-alt" aria-hidden="true"></span>
          Gerar relatório
        </button>
      </div>
    </form:form>
    
    <div id="divRelatorio" class="panel panel-default">
    </div>
  </div>
</body>
</html>