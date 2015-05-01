<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<!DOCTYPE html>
<html lang="pt">
<head>
  <title>Iniciar Venda</title>
</head>
<body>
  <div class="container">
    <h2>Iniciar Venda</h2>
    <c:set var="alertClass" value="${(!erro?'alert alert-success':'alert alert-danger')}"></c:set>
    <div class="${(not empty mensagem?alertClass:'hidden')}">
      <button type="button" class="close" data-dismiss="alert">&times;</button>
      <p>${mensagem}</p>
    </div>
    
    <c:url var="actionUrl" value="/vendas/checar_placa"/>
    <form:form action="${actionUrl}" 
          method="get" 
          role="form" 
          modelAttribute="placaForm">
      <div class="form-group">
        <label for="placa">PLACA DO VEÍCULO</label>
        <form:input path="placa" id="placa" class="form-control"/>
        <form:errors path="placa" class="text-danger"/>
      </div>
      
      <div class="form-group text-right">
        <button type="submit" class="btn btn-default">
          Continuar
          <span class="glyphicon glyphicon-step-forward" aria-hidden="true"></span>
        </button>
      </div>
    </form:form>
  </div>
</body>
</html>