<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<!DOCTYPE html>
<html lang="pt">
<head>
  <title>Registrar compra: cadastro de veículo</title>
  <c:url var="resources" value="/resources"/>
  <link href="//cdnjs.cloudflare.com/ajax/libs/select2/4.0.0-rc.2/css/select2.min.css" rel="stylesheet" />
  <script src="//cdnjs.cloudflare.com/ajax/libs/select2/4.0.0-rc.2/js/select2.min.js"></script>
  <script>
    $(document).ready(function(){
      $('#selectModelo').select2();
    });
  </script>
</head>
<body>
  <div class="container">
    <h2>Registro de compra</h2>
    <h3>Cadastro de veículo</h3>
    
    <div class="alert alert-dismissible" role="alert" id="divMsg" ${(not empty erro?'erro':'')}>
      <button type="button" class="close" data-hide="alert">
        <span aria-hidden="true">&times;</span>
      </button>
      <p id="pMsg">${mensagem}</p>
    </div>
    
    <c:url var="actionUrl" value="/compras/salvar_veiculo" />
    <form:form action="${actionUrl}" 
               method="post" 
               role="form" 
               modelAttribute="veiculo" 
               enctype="multipart/form-data">
      <c:import url="/WEB-INF/views/veiculos/campos_edicao.jsp" />
    </form:form>
  </div>
</body>
</html>