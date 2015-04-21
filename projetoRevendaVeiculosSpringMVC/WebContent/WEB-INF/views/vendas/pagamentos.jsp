<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring" %>

<div class="panel-heading">Pagamentos</div>
<div class="panel-body">
  <form:form role="form" 
             modelAttribute="partePagamento" 
             id="formPgt">
    <div class="row">
      <div class="form-group col-md-5">
        <label for="formaPgt">FORMA DE PAGAMENTO</label>
        <form:select path="formaPagamento.id" id="formaPgt" 
                     class="form-control" 
                     items="${formasPagamento}" 
                     itemValue="id"
                     itemLabel="descricao"/>
      </div>
  
      <spring:bind path="quantia">
        <c:if test="${status.error}">
          <c:set var="erroQuantia" value="${true}"/>
        </c:if>
      </spring:bind>
      <div class="form-group ${erroQuantia?'has-error has-feedback':''} col-md-5">
        <label for="quantia">QUANTIA (R$)</label>
        <form:input path="quantia" id="quantia" class="form-control"/>
        <c:if test="${erroQuantia}">
        <span class="glyphicon glyphicon-remove form-control-feedback" aria-hidden="true"></span>
        </c:if>
        <form:errors path="quantia" class="text-danger"/>
      </div>
    </div>
    
    <div class="form-group">
      <button type="button" class="btn btn-default" id="btnAddPgt">
        <span class="glyphicon glyphicon-plus-sign" aria-hidden="true"></span> Adicionar
      </button>
    </div>
  </form:form>
</div>

<table class="table">
  <thead>
    <tr>
      <th>FORMA DE PAGAMENTO</th>
      <th>QUANTIA</th>
      <th></th>
    </tr>
  </thead>
  <tbody>
  <c:forEach var="pgt" items="${venda.partesPagamento}">
    <tr>
      <td>${pgt.formaPagamento.descricao}</td>
      <td><fmt:formatNumber value="${pgt.quantia}" type="currency" /></td>
      <td class="text-center">
        <button type="button" class="btn btn-danger" 
          onclick="confirmarExclusao(this)">
          <span class="glyphicon glyphicon-trash"></span> 
          Excluir
        </button>
      </td>
    </tr>
  </c:forEach>
  </tbody>
</table>
