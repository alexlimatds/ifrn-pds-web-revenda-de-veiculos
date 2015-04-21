<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>
<c:set var="context" value="${pageContext.request.contextPath}"/>
<nav class="navbar navbar-default">
  <div class="container-fluid">
    <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
      <ul class="nav navbar-nav">
        <li class="dropdown">
          <a href="#" 
             class="dropdown-toggle" 
             data-toggle="dropdown" 
             role="button" 
             aria-expanded="false">
            CADASTROS <span class="caret"></span>
          </a>
          <ul class="dropdown-menu" role="menu">
            <li><a href="${context}/veiculos">Veículos</a></li>
            <sec:authorize ifAnyGranted="ROLE_ADMIN">
              <li><a href="${context}/modelos">Modelos</a></li>
              <li><a href="${context}/tipos_veiculo">Tipos de veículo</a></li>
              <li><a href="${context}/fabricantes">Fabricantes</a></li>
            </sec:authorize>
          </ul>
        </li>
        
        <li class="dropdown">
          <a href="#" 
             class="dropdown-toggle" 
             data-toggle="dropdown" 
             role="button" 
             aria-expanded="false">
            TRANSAÇÕES <span class="caret"></span>
          </a>
          <ul class="dropdown-menu" role="menu">
            <li><a href="${context}/compras/iniciar">Iniciar compra</a></li>
            <li><a href="${context}/vendas/iniciar">Iniciar venda</a></li>
          </ul>
        </li>
        
        <li>
          <a href="${context}/j_spring_security_logout">SAIR</a>
        </li>        
      </ul>
      <p class="navbar-text navbar-right">
        <strong><sec:authentication property="details.nome"/></strong>
      </p>
    </div>
  </div>
</nav>