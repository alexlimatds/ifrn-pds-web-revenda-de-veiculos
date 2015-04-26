<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<!DOCTYPE html>
<html lang="pt">
<head>
  <title>Registrar Venda: dados da venda</title>
  <c:url var="resources" value="/resources"/>
  <link href="${resources}/js/bootstrap-datepicker.css" rel="stylesheet" />
  <script src="${resources}/js/bootstrap-datepicker.js"></script>
  <script src="${resources}/js/bootstrap-datepicker.pt-BR.min.js" charset="UTF-8"></script>
  <script>
    $(document).ready(function(){
  	  $('#txtData').datepicker({
  		format: "dd/mm/yyyy", 
  		language: "pt-BR"
  	  });
  	  $(document).ajaxError(function(event, jqxhr, settings){
  	    atualizarAlert('Aconteceu um erro ao realizar a requisição.', true);
  	  });
  	  configurarFormularioPgt();
    });
    
    <c:url var="addPgtUrl" value="/vendas/ajax_add_pagamento"/>
    function addPagamento(){
      var campos = $('#formPgt').serialize();
  	  $.get('${addPgtUrl}', campos, function(data){
  		$('#divPgtos').empty();
  		$('#divPgtos').append(data);
  		configurarFormularioPgt();
  		$('#formaPgt').focus();
  	  });
    }
    
    function configurarFormularioPgt(){
  	  $('#formPgt').submit(function(event){
    	event.preventDefault();
      });
  	  $('#btnAddPgt').click(addPagamento);
    }
    
    <c:url var="delPgtUrl" value="/vendas/ajax_del_pagamento"/>
    function confirmarExclusao(eventSource){
  	  var msg = 'Confirmar exclusão de parte de pagamento?';
  	  bootbox.confirm(msg, function(result){
  		if(result){
  		  var indiceLinha = $(eventSource).parent().parent().index();
  		  var param = {index : indiceLinha};
  		  $.getJSON('${delPgtUrl}', param, function(data){
  			var status = data['status'];
  			if(status === 'ok')
  			  $(eventSource).closest('tr').remove();
  			else
  		      atualizarAlert('Ocorreu um erro.', 'erro');
  		  });
  		}
  	  });
  	}
  </script>
</head>
<body>
  <div class="container">
    <h2>Registro de Venda</h2>
    <h3>Dados da venda</h3>
    
    <div class="alert alert-dismissible" role="alert" id="divMsg" ${(not empty erro?'erro':'')}>
      <button type="button" class="close" data-hide="alert">
        <span aria-hidden="true">&times;</span>
      </button>
      <p id="pMsg">${mensagem}</p>
    </div>
    
    <div class="panel panel-default">
      <div class="panel-heading">Veículo a ser vendido</div>
      <div class="panel-body container-fluid">
        <div class="row">
          <div class="col-md-2">PLACA</div>
          <div class="col-md-4"><strong>${venda.veiculo.placa}</strong></div>
        </div>
        <div class="row">
          <div class="col-md-2">MODELO</div>
          <div class="col-md-4">
            <strong>
            ${venda.veiculo.modelo.descricao} (${venda.veiculo.modelo.tipo.descricao}) - ${venda.veiculo.modelo.fabricante.descricao}
            </strong>
          </div>
        </div>
        <div class="row">
          <div class="col-md-2">ANO</div>
          <div class="col-md-4"><strong>${venda.veiculo.anoFabricacao}</strong></div>
        </div>
        <div class="row">
          <div class="col-md-2">CILINDRADAS</div>
          <div class="col-md-4"><strong>${venda.veiculo.cilindradas}</strong></div>
        </div>
        <div class="row">
          <div class="col-md-2">CHASSI</div>
          <div class="col-md-4"><strong>${venda.veiculo.chassi}</strong></div>
        </div>
        <div class="row">
          <div class="col-md-2">FOTO</div>
          <div class="col-md-4">
            <c:choose>
              <c:when test="${not empty venda.veiculo.foto}">
                <a class="btn btn-default" title="Ver foto" onclick="$('#imageModal').modal({show:true})">
                  <span class="glyphicon glyphicon-eye-open" ></span>
                </a>
              </c:when>
              <c:otherwise>
                <strong>SEM FOTO</strong>
              </c:otherwise>
            </c:choose>
          </div>
        </div>
      </div>
    </div>
    
    <c:if test="${not empty venda.veiculo.foto}">
      <div id="imageModal" class="modal fade" tabindex="-1" role="dialog" aria-hidden="true">
        <div class="modal-dialog">
          <div class="modal-content">
            <div class="modal-body">
              <c:url var="urlFoto" value="/veiculos/ajax_foto"/>
              <img class="img-responsive" src="${urlFoto}?id=${venda.veiculo.id}">
            </div>
          </div>
        </div>
      </div>
    </c:if>
    
    <div class="panel panel-default">
      <div class="panel-heading">Dados da venda</div>
      <div class="panel-body">
        <c:url var="actionUrl" value="/vendas/submeter_venda"/>
        <form:form action="${actionUrl}" method="post" role="form" 
                   modelAttribute="venda" id="formVenda">
          <div class="form-group">
            <label for="txtData">DATA</label>
            <form:input path="data" id="txtData" class="form-control"/>
            <form:errors path="data" class="text-danger"/>
          </div>
          <div class="form-group">
            <label for="txtDesconto">DESCONTO</label>
            <form:input path="desconto" id="txtDesconto" class="form-control"/>
            <form:errors path="desconto" class="text-danger"/>
          </div>
          <div class="form-group">
            <label for="txtObs">OBSERVAÇÕES</label>
            <form:textarea path="obs" id="txtObs" rows="3" class="form-control"/>
            <form:errors path="obs" class="text-danger"/>
          </div>
          <form:errors path="partesPagamento" class="text-danger" />
        </form:form>
        
        <div class="panel panel-default" id="divPgtos">
          <c:import url="/WEB-INF/views/vendas/pagamentos.jsp"/>
        </div>
        
        <button type="button" class="btn btn-default" 
          onclick="$('#formVenda').submit()">
          Continuar
          <span class="glyphicon glyphicon-step-forward" aria-hidden="true"></span>
        </button>
      </div>
    </div>
  </div>
</body>
</html>