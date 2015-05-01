<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>

      <div class="form-group">
        <label for="selectModelo">MODELO</label>
        <form:select path="modelo" 
                     id="selectModelo" 
                     class="form-control">
         <c:forEach var="m" items="${modelos}">
           <form:option value="${m.id}" 
             label="${m.descricao} (${m.tipo.descricao}) - ${m.fabricante.descricao}"/>
         </c:forEach>
       </form:select>
        <form:errors path="modelo" cssStyle="color: red"/>
      </div>
      
      <div class="form-group">
        <label for="anoFabricacao">ANO</label>
        <form:input path="anoFabricacao" class="form-control"/>
        <form:errors path="anoFabricacao" cssStyle="color: red"/>
      </div>
      
      <div class="form-group">
        <label for="placa">PLACA</label>
        <form:input path="placa" class="form-control" readonly="${not empty placaReadonly}" />
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
      
      <div class="form-group">
        <button type="submit" class="btn btn-default">
          <span class="glyphicon glyphicon-floppy-disk" aria-hidden="true"></span>
          Salvar
        </button>
      </div>