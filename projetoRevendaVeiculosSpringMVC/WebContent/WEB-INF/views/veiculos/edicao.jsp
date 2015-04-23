<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<!DOCTYPE html>
<html lang="pt">
<head>
  <title>${titulo}</title>
  <c:url var="resources" value="/resources"/>
  <link href="//cdnjs.cloudflare.com/ajax/libs/select2/4.0.0-rc.2/css/select2.min.css" rel="stylesheet" />
  <script src="//cdnjs.cloudflare.com/ajax/libs/select2/4.0.0-rc.2/js/select2.min.js"></script>
  <script>
  $(document).ready(function() {
    $('#selectModelo').select2();
  });
  </script>
</head>
<body>
  <div class="container">
    <h2>${titulo}</h2>
    <c:url var="actionUrl" value="/veiculos/salvar"/>
    <form:form action="${actionUrl}" method="post" role="form" 
               modelAttribute="veiculo" enctype="multipart/form-data">
      <input type="hidden" name="titulo" value="${titulo}">
      <c:import url="campos_edicao.jsp"/>
    </form:form>
  </div>
</body>
</html>