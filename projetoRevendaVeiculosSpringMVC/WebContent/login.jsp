<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
  <title>Revenda de Veículos</title>
  <c:import url="/WEB-INF/views/estilos_e_scripts.jsp" />
  <style>
    div.container{
      margin-top: 30px;
    }
  </style>
</head>
<body>
  <div class="container">
    <c:if test="${param.error != null}">
      <div class="alert alert-danger">
        <button type="button" class="close" data-dismiss="alert">&times;</button>
        <p>Login falhou.</p>
      </div>
    </c:if>
    
	<c:url var="url" value="/j_spring_security_check" />
	<form action="${url}" method="post" role="form">
      <div class="form-group">
        <label for="login">LOGIN</label>
		<input type="text" name="j_username" id="login" class="form-control">
      </div>
      
      <div class="form-group">
        <label for="senha">SENHA</label>
		<input type="password" name="j_password" id="senha" class="form-control">
      </div>
      
      <div class="form-group">
        <input type="submit" value="Entrar" class="btn btn-default"/>
      </div>
	</form>
  </div>
</body>
</html>