<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://www.opensymphony.com/sitemesh/decorator" prefix="decorator" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="pt">
<head>
  <meta charset="iso-8859-1">
  <title><decorator:title /></title>
  <c:import url="/WEB-INF/views/estilos_e_scripts.jsp" />
  <decorator:head />
</head>
<body>
  <c:import url="/WEB-INF/views/navegacao.jsp" />
  <decorator:body />
</body>
</html>