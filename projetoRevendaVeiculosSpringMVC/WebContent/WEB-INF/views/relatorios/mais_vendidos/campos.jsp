<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>

    <c:url var="actionUrl" value="/relatorios/mais_vendidos/ajax_relatorio"/>
    <form:form action="${actionUrl}" 
          method="get" 
          role="form" 
          modelAttribute="periodoForm" 
          id="meuForm">
          
      <div class="form-group">
        <label for="inicio">DATA INICIAL</label>
        <form:input id="inicio" path="inicio" class="form-control campoData"/>
        <form:errors path="inicio" class="text-danger" />
      </div>
      
      <div class="form-group">
        <label for="fim">DATA FINAL</label>
        <form:input id="fim" path="fim" class="form-control campoData"/>
        <form:errors path="fim" class="text-danger" />
      </div>
      
      <div class="form-group">
        <button type="submit" class="btn btn-default" id="botaoForm">
          <span class="glyphicon glyphicon-list-alt" aria-hidden="true"></span>
          Gerar relatório
        </button>
      </div>
    </form:form>