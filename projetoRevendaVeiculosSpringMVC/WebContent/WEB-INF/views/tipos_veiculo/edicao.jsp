<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<!DOCTYPE html>
<html lang="pt">
<head>
  <meta charset="iso-8859-1">
  <title>${titulo}</title>
  <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.4/css/bootstrap.min.css">
  <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.4/css/bootstrap-theme.min.css">
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.1/jquery.min.js"></script>
  <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.4/js/bootstrap.min.js"></script>
</head>
<body>
  <div class="container">
    <h2>${titulo}</h2>
    <c:url var="actionUrl" value="/tipos_veiculo/salvar"/>
    <form:form action="${actionUrl}" method="post" role="form" modelAttribute="tipo">
      <div class="form-group">
        <label for="descricao">DESCRIÇÃO</label>
        <form:input path="descricao" class="form-control"/>
        <form:errors path="descricao" cssStyle="color: red"/>
      </div>
      <form:hidden path="id"/>
      <input type="hidden" name="titulo" value="${titulo}">
      <div class="form-group">
        <input type="submit" value="Salvar" class="btn btn-default">
      </div>
    </form:form>
  </div>
</body>
</html>