<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="pt">
<head>
  <!-- Página que utiliza Ajax para realizar a exclusão de registros. 
       Após a exclusão do registro no lado servidor, faz uma nova requisição 
       solicitando a tabela com os registros. -->
  <meta charset="iso-8859-1">
  <title>Modelos</title>
  <c:url var="resources" value="/resources"/>
  <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.4/css/bootstrap.min.css">
  <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.4/css/bootstrap-theme.min.css">
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.1/jquery.min.js"></script>
  <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.4/js/bootstrap.min.js"></script>
  <script src="${resources}/js/bootbox.min.js"></script>
  <script src="${resources}/js/funcoes.js"></script>
  <script>
  	function confirmarExclusao(idEntidade, descricao, eventSource){
  	  var msg = 'Confirmar exclusão de <strong>' + descricao + '</strong>?';
  	  bootbox.confirm(msg, function(result){
  		if(result){
  		  var param = {id : idEntidade};
  		  $.getJSON('modelos/excluir', param, function(data){
  		    var msg = data['mensagem'];
  		    var erro = data['erro'];
  		    atualizarAlert(msg, erro);
  		    $.get('modelos/modelos', function(data){
  		    	var $tbody = $('#tblRegistros').children('tbody');
  		    	$tbody.empty();
  		    	$tbody.append(data);
  		    });
  		  });
  		}
  	  });
  	}
  </script>
</head>
<body>
  <div class="container">
    <h2>Modelos</h2>
    
    <div class="alert alert-dismissible" role="alert" id="divMsg" ${(not empty erro?'erro':'')}>
      <button type="button" class="close" data-hide="alert">
        <span aria-hidden="true">&times;</span>
      </button>
      <p id="pMsg">${mensagem}</p>
    </div>
    
    <c:url var="actionUrl" value="/modelos"/>
    <a class="btn btn-default" href="${actionUrl}/novo">
      <span class="glyphicon glyphicon-plus"></span> Novo Modelo
    </a>
    <table class="table table-hover" id="tblRegistros">
      <thead>
        <tr>
          <th>Descrição</th>
          <th>Fabricante</th>
          <th>Tipo de Veículo</th>
          <th colspan="2" class="text-center">Opções</th>
        </tr>
      </thead>
      <tbody>
        <c:import url="/WEB-INF/views/modelos/modelos.jsp" />
      </tbody>
    </table>
  </div>
</body>
</html>