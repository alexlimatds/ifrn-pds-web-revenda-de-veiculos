<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="pt">
<head>
  <meta charset="iso-8859-1">
  <title>Tipos de Veículo</title>
  <c:url var="resources" value="/resources"/>
  <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.4/css/bootstrap.min.css">
  <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.4/css/bootstrap-theme.min.css">
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.1/jquery.min.js"></script>
  <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.4/js/bootstrap.min.js"></script>
  <script src="${resources}/js/bootbox.min.js"></script>
  <script>
  	$(document).ready(function(){
  	  if(${not empty mensagem}){
  		if(${not empty erro})
  		  $('#divMsg').addClass('alert-danger');
  		else
  		  $('#divMsg').addClass('alert-success');
  	  }else{
  		$('#divMsg').hide();
  	  }
  	});
  	$(document).ready(function(){
  	  $("[data-hide]").on("click", function(){
  	    $(this).closest("." + $(this).attr("data-hide")).hide();
  	  });
  	});
  	function confirmarExclusao(idEntidade, descricao, eventSource){
  		var msg = 'Confirmar exclusão de <strong>' + descricao + '</strong>?';
  		bootbox.confirm(msg, function(result){
  		  if(result){
  		    var param = {id : idEntidade};
  		    $.getJSON('tipos_veiculo/excluir', param, function(data){
  		      var msg = data['mensagem'];
  		      var erro = data['erro'];
  		      atualizarMsg(msg, erro);
  		      removerRegistro(eventSource);
  		    });
  		  }
  		});
  	}
  	//Exibe e atualiza o alert de mensagens.
  	//mensagem	Mensagem a ser exibida.
  	//erro		Indica se a mensagem é de erro (true) ou de sucesso (false).
  	function atualizarMsg(mensagem, erro){
  	  if(erro === undefined)
  		erro = false;
  	  $('#pMsg').text(mensagem);
      //$('#divMsg').toggleClass('hidden', false); //Exibe alert
      $('#divMsg').show();
      $('#divMsg').toggleClass('alert-danger', erro);
      $('#divMsg').toggleClass('alert-success', !erro);
  	}
  	//Remove o registro disparador do evento
  	//eventSource	elemento HTML disparador do evento. Geralmente um link ou botão.
  	function removerRegistro(eventSource){
  	  $(eventSource).closest('tr').remove();
  	}
  </script>
</head>
<body>
  <div class="container">
    <h2>Tipos de Veículo</h2>
    
    <div class="alert alert-dismissible" role="alert" id="divMsg">
      <button type="button" class="close" data-hide="alert">
        <span aria-hidden="true">&times;</span>
      </button>
      <p id="pMsg">${mensagem}</p>
    </div>
    
    <c:url var="actionUrl" value="/tipos_veiculo"/>
    <a class="btn btn-default" href="${actionUrl}/novo">
      <span class="glyphicon glyphicon-plus"></span> Novo Tipo de Veículo
    </a>
    <table class="table table-hover" id="tblRegistros">
      <thead>
        <tr>
          <th>Tipo de Veículo</th>
          <th colspan="2" class="text-center">Opções</th>
        </tr>
      </thead>
      <tbody>
        <c:forEach var="tipo" items="${tipos}">
          <tr>
            <td>${tipo.descricao}</td>
            <td class="text-center">
              <a class="btn btn-danger" href="#" 
                onclick="confirmarExclusao('${tipo.id}', '${tipo.descricao}', this);">
                <span class="glyphicon glyphicon-trash"></span> Excluir
              </a>
            </td>
            <td class="text-center">
              <a class="btn btn-danger" href="${actionUrl}/alterar?id=${tipo.id}">
                <span class="glyphicon glyphicon-edit"></span> Alterar
              </a>
            </td>
          </tr>
        </c:forEach>
      </tbody>
    </table>
  </div>
</body>
</html>