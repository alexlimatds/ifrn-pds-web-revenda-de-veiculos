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
    <c:url var="actionUrl" value="/fabricantes/salvar"/>
    <form action="${actionUrl}" method="post" role="form">
      <div class="form-group">
        <label for="descricao">DESCRIÇÃO</label>
        <input type="text" 
          id="descricao" 
          name="descricao" 
          value="${fabricante.descricao}" 
          class="form-control">
        <form:errors path="fabricante.descricao" cssStyle="color: red"/>
      </div>
      <input type="hidden" name="id" value="${fabricante.id}">
      <div class="form-group">
        <input type="submit" value="Salvar">
      </div>
    </form>
  </div>
</body>
</html>