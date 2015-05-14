<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<!DOCTYPE html>
<html lang="pt">
<head>
  <title>Relatório de modelos mais vendidos</title>
  <c:url var="resources" value="/resources"/>
  <link href="${resources}/js/bootstrap-datepicker.css" rel="stylesheet" />
  <script src="${resources}/js/bootstrap-datepicker.js"></script>
  <script src="${resources}/js/bootstrap-datepicker.pt-BR.min.js" charset="UTF-8"></script>
  <script>
    $(document).ready(function(){
	  $(document).ajaxError(function(event, jqxhr, settings){
  		if(jqxhr.status === 422){ //erro de validação nos campos
  		  $('#meuForm').replaceWith(jqxhr.responseText);
  		  prepararCampos();
  		}
  		else{
		  atualizarAlert('Aconteceu um erro ao realizar a requisição.', true);
  		}
  	  });
  	  
  	  prepararCampos();
    });
    
    function submeter(){
      var parametros = $('#meuForm').serialize();
  	  $.get($('#meuForm').attr('action'), parametros, function(data){
  		$('#divRelatorio').html('<div class="panel-body">' + data + '</div>');
  	  });
    }
    
    function prepararCampos(){
	  $('.campoData').datepicker({
        format: "dd/mm/yyyy", 
        language: "pt-BR"
      });
	  $('#meuForm').submit(function(event){
  	    event.preventDefault(); //evita submissão do formulário
  	    submeter();
  	  });
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
      
    <c:import url="campos.jsp" />
    
    <div id="divRelatorio" class="panel panel-default">
    </div>
  </div>
</body>
</html>