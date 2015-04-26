<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<!DOCTYPE html>
<html lang="pt">
<head>
  <title>Registrar venda: cadastro de veículo utilizado como pagamento</title>
  <c:url var="resources" value="/resources"/>
  <link href="//cdnjs.cloudflare.com/ajax/libs/select2/4.0.0-rc.2/css/select2.min.css" rel="stylesheet" />
  <script src="//cdnjs.cloudflare.com/ajax/libs/select2/4.0.0-rc.2/js/select2.min.js"></script>
  <script>
    $(document).ready(function(){
      $('#selectModelo').select2();
    });
  </script>
</head>
<body>
  <div class="container">
    <h2>Registro de venda</h2>
    <h3>Cadastro de veículo utilizado como pagamento</h3>
    
    <div class="alert alert-dismissible" role="alert" id="divMsg" ${(not empty erro?'erro':'')}>
      <button type="button" class="close" data-hide="alert">
        <span aria-hidden="true">&times;</span>
      </button>
      <p id="pMsg">${mensagem}</p>
    </div>
    
    <c:url var="actionUrl" value="/vendas/add_compra" />
    <form:form action="${actionUrl}" 
               method="post" 
               role="form" 
               modelAttribute="novaCompra" 
               enctype="multipart/form-data">
      
      <div class="panel panel-default">
        <div class="panel-heading">
          Dados do veículo
        </div>
        <div class="panel-body">
          <c:set var="editarVeiculo" value="${novaCompra.veiculo.id == null}"/>
          
          <form:hidden path="veiculo.id" />
          
          <div class="form-group">
            <label for="selectModelo">MODELO</label>
            <form:select path="veiculo.modelo.id" 
                         id="selectModelo" 
                         class="form-control" 
                         disabled="${not editarVeiculo}">
             <c:forEach var="m" items="${modelos}">
               <form:option value="${m.id}" 
                 label="${m.descricao} (${m.tipo.descricao}) - ${m.fabricante.descricao}"/>
             </c:forEach>
           </form:select>
            <form:errors path="veiculo.modelo.id" cssStyle="color: red"/>
          </div>
          
          <div class="form-group">
            <label for="anoFabricacao">ANO</label>
            <form:input path="veiculo.anoFabricacao" class="form-control" readonly="${not editarVeiculo}" />
            <form:errors path="veiculo.anoFabricacao" cssStyle="color: red"/>
          </div>
          
          <div class="form-group">
            <label for="placa">PLACA</label>
            <form:input path="veiculo.placa" class="form-control" readonly="${true}" />
            <form:errors path="veiculo.placa" cssStyle="color: red"/>
          </div>
          
          <div class="form-group">
            <label for="cilindradas">CILINDRADAS</label>
            <form:input path="veiculo.cilindradas" class="form-control" readonly="${not editarVeiculo}" />
            <form:errors path="veiculo.cilindradas" cssStyle="color: red"/>
          </div>
          
          <div class="form-group">
            <label for="chassi">CHASSI</label>
            <form:input path="veiculo.chassi" class="form-control" readonly="${not editarVeiculo}" />
            <form:errors path="veiculo.chassi" cssStyle="color: red"/>
          </div>
          
          <div class="form-group">
            <label for="arquivoFoto">FOTO</label>
            <input type="file" name="arquivoFoto" accept="image/*" class="form-control" ${editarVeiculo?'':'disabled=\"disabled\"'} />
          </div>
        </div>
      </div> <!-- veículo -->
      
      <div class="panel panel-default">
        <div class="panel-heading">
          Dados da compra
        </div>
        <div class="panel-body">
          
          <form:hidden path="data" /><!-- somente para passar na validação -->
        
          <div class="form-group">
            <label for="txtPreco">PREÇO (R$)</label>
            <form:input path="preco" id="txtPreco" class="form-control"/>
            <form:errors path="preco" cssStyle="color: red"/>
          </div>
          
          <div class="form-group">
            <label for="txtObs">OBSERVAÇÕES</label>
            <form:textarea path="obs" id="txtObs" rows="3" class="form-control"/>
            <form:errors path="obs" cssStyle="color: red"/>
          </div>
        </div>
      </div> <!-- compra -->
      
      <button type="submit" class="btn btn-default">
        <span class="glyphicon glyphicon-plus-sign" aria-hidden="true"></span>
        Adicionar veículo
      </button>
    </form:form>
    
  </div><!-- container -->
</body>
</html>