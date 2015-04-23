<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="pt">
<head>
  <title>Iniciar compra</title>
</head>
<body>
  <div class="container">
    <h2>Iniciar compra</h2>
    <c:set var="alertClass" value="${(!erro?'alert alert-success':'alert alert-danger')}"></c:set>
    <div class="${(not empty mensagem?alertClass:'hidden')}">
      <button type="button" class="close" data-dismiss="alert">&times;</button>
      <p>${mensagem}</p>
    </div>
    
    <c:url var="actionUrl" value="/compras/checar_placa"/>
    <form action="${actionUrl}" method="get" role="form">
      <div class="form-group">
        <label for="placa">PLACA DO VEÍCULO</label>
        <input type="text" id="placa" name="placa" class="form-control">
      </div>
      
      <div class="form-group">
        <button type="submit" class="btn btn-default">
          Continuar
          <span class="glyphicon glyphicon-step-forward" aria-hidden="true"></span>
        </button>
      </div>
    </form>
  </div>
</body>
</html>