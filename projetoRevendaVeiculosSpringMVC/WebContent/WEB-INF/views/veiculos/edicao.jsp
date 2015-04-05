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
      <div class="form-group">
        <label for="selectModelo">MODELO</label>
        <form:select path="modelo.id" 
                     id="selectModelo" 
                     class="form-control">
         <c:forEach var="m" items="${modelos}">
           <form:option value="${m.id}" 
             label="${m.descricao} (${m.tipo.descricao}) - ${m.fabricante.descricao}"/>
         </c:forEach>
       </form:select>
        <form:errors path="modelo.id" cssStyle="color: red"/>
      </div>
      
      <div class="form-group">
        <label for="anoFabricacao">ANO</label>
        <form:input path="anoFabricacao" class="form-control"/>
        <form:errors path="anoFabricacao" cssStyle="color: red"/>
      </div>
      
      <div class="form-group">
        <label for="placa">PLACA</label>
        <form:input path="placa" class="form-control"/>
        <form:errors path="placa" cssStyle="color: red"/>
      </div>
      
      <div class="form-group">
        <label for="cilindradas">CILINDRADAS</label>
        <form:input path="cilindradas" class="form-control"/>
        <form:errors path="cilindradas" cssStyle="color: red"/>
      </div>
      
      <div class="form-group">
        <label for="chassi">CHASSI</label>
        <form:input path="chassi" class="form-control"/>
        <form:errors path="chassi" cssStyle="color: red"/>
      </div>
      
      <div class="form-group">
        <label for="arquivoFoto">FOTO</label>
        <input type="file" name="arquivoFoto" accept="image/*" class="form-control"/>
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