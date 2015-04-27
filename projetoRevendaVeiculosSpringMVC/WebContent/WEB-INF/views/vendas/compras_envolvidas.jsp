<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<!DOCTYPE html>
<html lang="pt">
<head>
  <title>Registrar Venda: compras envolvidas</title>
</head>
<body>
  <div class="container">
    <h2>Registro de Venda</h2>
    <h3>Compras envolvidas</h3>
    
    <div class="alert alert-dismissible" role="alert" id="divMsg" ${(not empty erro?'erro':'')}>
      <button type="button" class="close" data-hide="alert">
        <span aria-hidden="true">&times;</span>
      </button>
      <p id="pMsg">${mensagem}</p>
    </div>
    
    <div class="panel panel-default">
      <div class="panel-body">
        <c:choose>
          <c:when test="${not empty venda.comprasEnvolvidas}">
            <table class="table">
              <thead>
                <tr>
                  <th>PLACA</th>
                  <th>MODELO</th>
                  <th>ANO</th>
                  <th>VALOR DE COMPRA</th>
                </tr>
              </thead>
              <tbody>
              <c:forEach var="compra" items="${venda.comprasEnvolvidas}">
                <tr>
                  <td>${compra.veiculo.placa}</td>
                  <td>${compra.veiculo.modelo.descricao} / ${compra.veiculo.modelo.fabricante.descricao}</td>
                  <td>${compra.veiculo.anoFabricacao}</td>
                  <td>
                    <fmt:formatNumber value="${compra.preco}" type="currency" />
                  </td>
                </tr>
              </c:forEach>
              </tbody>
            </table>
          </c:when>
          <c:otherwise>
            <div class="panel panel-default" role="alert">
              <div class="panel-body bg-info">
                Sem veículos utilizados como parte do pagamento.
              </div>
            </div>
          </c:otherwise>
        </c:choose>
        
        <c:url var="continuarUrl" value="/vendas/checar_placa_compra"/>
        <form:form action="${continuarUrl}" 
                   method="post" 
                   role="form" 
                   modelAttribute="placaForm" 
                   cssClass="form-inline">
          <div class="form-group">
            <label for="placa">PLACA DO VEÍCULO</label>
            <form:input path="placa" id="placa" class="form-control"/>
            <form:errors path="placa" class="text-danger"/>
          </div>
          
          <button type="submit" class="btn btn-default">
            <span class="glyphicon glyphicon-plus-sign" aria-hidden="true"></span>
            Adicionar veículo
          </button>
        </form:form>
      </div>
    </div>
    
    <c:url var="continuarUrl" value="/vendas/resumo"/>
    <a class="btn btn-default" href="${continuarUrl}" role="button">
      Continuar
      <span class="glyphicon glyphicon-step-forward" aria-hidden="true"></span>
    </a>
  </div> <!-- container -->
</body>
</html>