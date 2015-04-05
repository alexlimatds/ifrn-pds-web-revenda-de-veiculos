<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:url var="context" value="."/>
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
            <li><a href="${context}/modelos">Modelos</a></li>
            <li><a href="${context}/tipos_veiculo">Tipos de veículo</a></li>
            <li><a href="${context}/fabricantes">Fabricantes</a></li>
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
          </ul>
        </li>
      </ul>
    </div>
  </div>
</nav>