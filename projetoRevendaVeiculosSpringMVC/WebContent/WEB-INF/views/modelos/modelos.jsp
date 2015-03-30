<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:forEach var="modelo" items="${modelos}">
  <tr>
    <td>${modelo.descricao}</td>
    <td>${modelo.fabricante.descricao}</td>
    <td>${modelo.tipo.descricao}</td>
    <td class="text-center">
      <a class="btn btn-danger" href="#" 
        onclick="confirmarExclusao('${modelo.id}', '${modelo.descricao}', this);">
        <span class="glyphicon glyphicon-trash"></span> Excluir
      </a>
    </td>
    <td class="text-center">
      <c:url var="actionUrl" value="/modelos"/>
      <a class="btn btn-danger" href="${actionUrl}/alterar?id=${modelo.id}">
        <span class="glyphicon glyphicon-edit"></span> Alterar
      </a>
    </td>
  </tr>
</c:forEach>