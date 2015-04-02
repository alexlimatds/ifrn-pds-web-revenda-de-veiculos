<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:forEach var="veiculo" items="${veiculos}">
  <tr>
    <td>${veiculo.placa}</td>
    <td>${veiculo.modelo.fabricante.descricao}</td>
    <td>${veiculo.modelo.descricao} - ${veiculo.modelo.tipo.descricao}</td>
    <td>${veiculo.anoFabricacao}</td>
    <td>${veiculo.cilindradas}</td>
    <td class="text-center">
      <c:choose>
        <c:when test="${not empty veiculo.foto}">
          <a class="btn btn-default" title="Ver foto" onclick="verFoto(${veiculo.id})">
            <span class="glyphicon glyphicon-eye-open" ></span>
          </a>
        </c:when>
        <c:otherwise>
          SEM FOTO
        </c:otherwise>
      </c:choose>
    </td>
    <td class="text-center">
      <c:url var="actionUrl" value="/veiculos"/>
      <a class="btn btn-danger" href="${actionUrl}/alterar?id=${veiculo.id}">
        <span class="glyphicon glyphicon-edit"></span> Alterar
      </a>
    </td>
    <td class="text-center">
      <a class="btn btn-danger" href="#" 
        onclick="confirmarExclusao('${veiculo.id}', '${veiculo.placa}', this);">
        <span class="glyphicon glyphicon-trash"></span> Excluir
      </a>
    </td>
  </tr>
</c:forEach>