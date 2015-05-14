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
      <div class="panel-heading">Dados do veículo</div>
      <div class="panel-body container-fluid">
        <div class="row">
          <div class="col-md-2">PLACA</div>
          <div class="col-md-4"><strong>${compra.veiculo.placa}</strong></div>
        </div>
        <div class="row">
          <div class="col-md-2">MODELO</div>
          <div class="col-md-4">
            <strong>
            ${compra.veiculo.modelo.descricao} (${compra.veiculo.modelo.tipo.descricao}) - ${compra.veiculo.modelo.fabricante.descricao}
            </strong>
          </div>
        </div>
        <div class="row">
          <div class="col-md-2">ANO</div>
          <div class="col-md-4"><strong>${compra.veiculo.anoFabricacao}</strong></div>
        </div>
        <div class="row">
          <div class="col-md-2">CILINDRADAS</div>
          <div class="col-md-4"><strong>${compra.veiculo.cilindradas}</strong></div>
        </div>
        <div class="row">
          <div class="col-md-2">CHASSI</div>
          <div class="col-md-4"><strong>${compra.veiculo.chassi}</strong></div>
        </div>
        <div class="row">
          <div class="col-md-2">FOTO</div>
          <div class="col-md-4">
            <c:choose>
              <c:when test="${not empty veiculo.foto}">
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
    
    <c:if test="${not empty veiculo.foto}">
      <div id="imageModal" class="modal fade" tabindex="-1" role="dialog" aria-hidden="true">
        <div class="modal-dialog">
          <div class="modal-content">
            <div class="modal-body">
              <c:url var="urlFoto" value="/veiculos/ajax_foto"/>
              <img class="img-responsive" src="${urlFoto}?id=${compra.veiculo.id}">
            </div>
          </div>
        </div>
      </div>
    </c:if>
    
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
            <label for="txtPreco">PREÇO</label>
            <form:input path="preco" id="txtPreco" class="form-control"/>
            <form:errors path="preco" cssStyle="color: red"/>
          </div>
          
          <div class="form-group">
            <label for="txtObs">OBSERVAÇÕES</label>
            <form:textarea path="obs" id="txtObs" rows="3" class="form-control"/>
            <form:errors path="obs" cssStyle="color: red"/>
          </div>
          
          <form:hidden path="veiculo.id"  />
          
          <div class="form-group">
            <button type="submit" class="btn btn-default">
              <span class="glyphicon glyphicon-floppy-disk" aria-hidden="true"></span>
              Salvar
            </button>
          </div>
        </form:form>
      </div>
    </div>
  </div>
</body>
</html>