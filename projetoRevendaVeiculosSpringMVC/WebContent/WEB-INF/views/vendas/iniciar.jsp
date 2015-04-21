<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
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
    <form action="${actionUrl}" method="get" role="form">
      <div class="form-group">
        <label for="placa">PLACA DO VEÍCULO</label>
        <input type="text" id="placa" name="placa" class="form-control">
      </div>
      <div class="form-group">
        <input type="submit" value="Continuar" class="btn btn-default">
      </div>
    </form>
  </div>
</body>
</html>