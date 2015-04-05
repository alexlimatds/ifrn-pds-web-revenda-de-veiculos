<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<!DOCTYPE html>
<html lang="pt">
<head>
  <title>Registrar compra</title>
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
    });
  </script>
  <c:url var="resources" value="/resources"/>
</head>
<body>
  <div class="container">
    <h2>Registro de compra</h2>
    
    <div class="alert alert-dismissible" role="alert" id="divMsg" ${(not empty erro?'erro':'')}>
      <button type="button" class="close" data-hide="alert">
        <span aria-hidden="true">&times;</span>
      </button>
      <p id="pMsg">${mensagem}</p>
    </div>
    
    <div class="panel panel-default">
      <div class="panel-heading">Dados do ve�culo</div>
      <div class="panel-body container-fluid">
        <div class="row">
          <div class="col-md-2">PLACA</div>
          <div class="col-md-4"><strong>${veiculo.placa}</strong></div>
        </div>
        <div class="row">
          <div class="col-md-2">MODELO</div>
          <div class="col-md-4">
            <strong>
            ${veiculo.modelo.descricao} (${veiculo.modelo.tipo.descricao}) - ${veiculo.modelo.fabricante.descricao}
            </strong>
          </div>
        </div>
        <div class="row">
          <div class="col-md-2">ANO</div>
          <div class="col-md-4"><strong>${veiculo.anoFabricacao}</strong></div>
        </div>
        <div class="row">
          <div class="col-md-2">CILINDRADAS</div>
          <div class="col-md-4"><strong>${veiculo.cilindradas}</strong></div>
        </div>
        <div class="row">
          <div class="col-md-2">CHASSI</div>
          <div class="col-md-4"><strong>${veiculo.chassi}</strong></div>
        </div>
        <div class="row">
          <div class="col-md-2">FOTO</div>
        </div>
      </div>
    </div>
    
    <div class="panel panel-default">
      <div class="panel-heading">Dados da compra</div>
      <div class="panel-body">
        <c:url var="actionUrl" value="/compras/salvar"/>
        <form:form action="${actionUrl}" method="post" role="form" 
                   modelAttribute="compra">
          <div class="form-group">
            <label for="txtData">DATA</label>
            <form:input path="data" id="txtData" class="form-control"/>
            <form:errors path="data" cssStyle="color: red"/>
          </div>
          <div class="form-group">
            <label for="txtPreco">PRE�O</label>
            <form:input path="preco" id="txtPreco" class="form-control"/>
            <form:errors path="preco" cssStyle="color: red"/>
          </div>
          <div class="form-group">
            <label for="txtObs">OBSERVA��ES</label>
            <form:textarea path="obs" id="txtObs" rows="3" class="form-control"/>
            <form:errors path="obs" cssStyle="color: red"/>
          </div>
          <input type="hidden" name="id_veiculo" value="${veiculo.id}">
          <div class="form-group">
            <input type="submit" value="Salvar" class="btn btn-default">
          </div>
        </form:form>
      </div>
    </div>
  </div>
</body>
</html>