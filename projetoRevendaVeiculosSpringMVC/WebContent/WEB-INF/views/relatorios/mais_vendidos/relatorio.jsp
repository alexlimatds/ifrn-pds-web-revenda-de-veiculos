<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

    <h2 class="text-center">RELATÓRIO</h2>
    
    <table class="table .table-striped">
      <tr>
        <th>#</th>
        <th>MODELO</th>
        <th>FABRICANTE</th>
        <th>UNIDADES COMERCIALIZADAS</th>
      </tr>
    <c:forEach items="${registros}" var="r">
      <tr>
        <td>${r.ordem}</td>
        <td>${r.modelo}</td>
        <td>${r.fabricante}</td>
        <td>${r.unidadesComercializadas}</td>
      </tr>  
    </c:forEach>
    </table>
    
