<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="pt">
<head>
  <meta charset="iso-8859-1">
  <title>Fabricantes</title>
  <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.4/css/bootstrap.min.css">
  <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.4/css/bootstrap-theme.min.css">
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.1/jquery.min.js"></script>
  <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.4/js/bootstrap.min.js"></script>
</head>
<body>
  <c:import url="/WEB-INF/views/navegacao.jsp" />
  <div class="container">
    <h2>Fabricantes</h2>
    <c:set var="alertClass" value="${(!erro?'alert alert-success':'alert alert-danger')}"></c:set>
    <div class="${(not empty mensagem?alertClass:'hidden')}">
      <button type="button" class="close" data-dismiss="alert">&times;</button>
      <p>${mensagem}</p>
    </div>
    <c:url var="actionUrl" value="/fabricantes"/>
    <a class="btn btn-default" href="${actionUrl}/novo">
      <span class="glyphicon glyphicon-plus"></span> Novo Fabricante
    </a>
    <table class="table table-hover">
      <tr>
        <th>Fabricante</th>
        <th colspan="2" class="text-center">Opções</th>
      </tr>
      <c:forEach var="fabricante" items="${fabricantes}">
        <tr>
          <td>${fabricante.descricao}</td>
          <td class="text-center">
            <a class="btn btn-danger" href="${actionUrl}/excluir?id=${fabricante.id}">
              <span class="glyphicon glyphicon-trash"></span> Excluir
            </a>
          </td>
          <td class="text-center">
            <a class="btn btn-danger" href="${actionUrl}/alterar?id=${fabricante.id}">
              <span class="glyphicon glyphicon-edit"></span> Alterar
            </a>
          </td>
        </tr>
      </c:forEach>
    </table>
  </div>
</body>
</html>