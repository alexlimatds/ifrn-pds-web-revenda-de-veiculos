<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<!DOCTYPE html>
<html lang="pt">
<head>
  <title>Registrar Venda: resumo</title>
  <c:url var="resources" value="/resources"/>
  </script>
</head>
<body>
  <div class="container">
    <h2>Registro de Venda</h2>
    <h3>Resumo</h3>
    
    <div class="alert alert-dismissible" role="alert" id="divMsg" ${(not empty erro?'erro':'')}>
      <button type="button" class="close" data-hide="alert">
        <span aria-hidden="true">&times;</span>
      </button>
      <p id="pMsg">${mensagem}</p>
    </div>
    
    <div class="panel panel-default">
      <div class="panel-heading">Veículo a ser vendido</div>
      <div class="panel-body container-fluid">
        <div class="row">
          <div class="col-md-2">PLACA</div>
          <div class="col-md-4"><strong>${venda.veiculo.placa}</strong></div>
        </div>
        <div class="row">
          <div class="col-md-2">MODELO</div>
          <div class="col-md-4">
            <strong>
            ${venda.veiculo.modelo.descricao} (${venda.veiculo.modelo.tipo.descricao}) - ${venda.veiculo.modelo.fabricante.descricao}
            </strong>
          </div>
        </div>
        <div class="row">
          <div class="col-md-2">ANO</div>
          <div class="col-md-4"><strong>${venda.veiculo.anoFabricacao}</strong></div>
        </div>
        <div class="row">
          <div class="col-md-2">CILINDRADAS</div>
          <div class="col-md-4"><strong>${venda.veiculo.cilindradas}</strong></div>
        </div>
        <div class="row">
          <div class="col-md-2">CHASSI</div>
          <div class="col-md-4"><strong>${venda.veiculo.chassi}</strong></div>
        </div>
        <div class="row">
          <div class="col-md-2">FOTO</div>
          <div class="col-md-4">
            <c:choose>
              <c:when test="${not empty venda.veiculo.foto}">
                <a class="btn btn-default" title="Ver foto" onclick="$('#imageModal').modal({show:true})">
                  <span class="glyphicon glyphicon-eye-open" ></span>
                </a>
              </c:when>
              <c:otherwise>
                <strong>SEM FOTO</strong>
              </c:otherwise>
            </c:choose>
          </div>
        </div>
      </div>
    </div><!-- veículo -->
    
    <c:if test="${not empty venda.veiculo.foto}">
      <div id="imageModal" class="modal fade" tabindex="-1" role="dialog" aria-hidden="true">
        <div class="modal-dialog">
          <div class="modal-content">
            <div class="modal-body">
              <c:url var="urlFoto" value="/veiculos/ajax_foto"/>
              <img class="img-responsive" src="${urlFoto}?id=${venda.veiculo.id}">
            </div>
          </div>
        </div>
      </div>
    </c:if>
    
    <div class="panel panel-default">
      <div class="panel-heading">Dados da venda</div>
      <div class="panel-body">
          <div class="row">
            <div class="col-md-2">DATA</div>
            <div class="col-md-4">
              <strong>
                <fmt:formatDate value="${venda.data}" pattern="dd/MM/yyyy" />
              </strong>
            </div>
          </div>
          <div class="row">
            <div class="col-md-2">DESCONTO</div>
            <div class="col-md-4">
              <strong>
                <fmt:formatNumber value="${venda.desconto}" type="currency" />
              </strong>
            </div>
          </div>
          <div class="row">
            <div class="col-md-2">OBSERVAÇÕES</div>
            <div class="col-md-4"><strong>${venda.obs}</strong></div>
          </div>
          
          <table class="table">
            <thead>
              <tr>
                <th>FORMA DE PAGAMENTO</th>
                <th>QUANTIA</th>
                <th>VEÍCULO</th>
              </tr>
            </thead>
            <tbody>
            <c:forEach var="pgt" items="${venda.partesPagamento}">
              <tr>
                <td>${pgt.formaPagamento.descricao}</td>
                <td><fmt:formatNumber value="${pgt.quantia}" type="currency" /></td>
                <td>
                <c:if test="${pgt.compraRelacionada != null}">
                  <c:set var="veiculo" value="${pgt.compraRelacionada.veiculo}" />
                  ${veiculo.placa} / ${veiculo.modelo.descricao} / ${veiculo.anoFabricacao}
                </c:if>
                </td>
              </tr>
            </c:forEach>
            </tbody>
          </table>
          
          <div class="row">
            <div class="col-md-2">VALOR TOTAL</div>
            <div class="col-md-4">
              <strong><fmt:formatNumber value="${venda.valorTotal}" type="currency" /></strong>
            </div>
          </div>
      </div>
    </div><!-- venda -->
    
    <div class="row">
      <div class="col-md-6">
        <a class="btn btn-default" 
           href="<c:url value="/vendas/passo_3" />">
          <span class="glyphicon glyphicon-step-backward" aria-hidden="true"></span>
          Voltar
        </a>
      </div>
      <div class="col-md-6 text-right">
        <c:url var="saveUrl" value="/vendas/salvar_venda" />
        <a class="btn btn-default" href="${saveUrl}" role="button">
          <span class="glyphicon glyphicon-floppy-disk" aria-hidden="true"></span>
          Salvar venda
        </a>
      </div>
    </div>
    
  </div><!-- container -->
</body>
</html>