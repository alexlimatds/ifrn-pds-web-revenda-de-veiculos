<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="pt">
<head>
  <title>Veículos</title>
  <c:url var="actionUrl" value="/veiculos"/>
  <script>
  	$(document).ready(function(){
  	  $(document).ajaxError(function(event, jqxhr, settings){
  		atualizarAlert('Aconteceu um erro ao realizar a requisição.', true);
  	  });
  	  $('#formPesquisa').submit(function(event){
  	    event.preventDefault();
  	    buscar();
  	  });
  	  $('#btnBuscar').click(buscar);
  	  $('#selectCampo').change(atualizarCampos);
  	  atualizarCampos();
  	});
  	function atualizarCampos(){
  	  var modelo = ($('#selectCampo').val() === 'ID_MODELO');
  	  $('#inputValor').prop('disabled', (modelo?'disabled':false));
	  $('#selectModelo').prop('disabled', (modelo?false:'disabled'));
  	}
  	function buscar(){
  	  var parametros = $('#formPesquisa').serialize();
  	  $.get('${actionUrl}/ajax_veiculos', parametros, function(data){
  		$('#tbodyRegistros').empty();
  		$('#tbodyRegistros').append(data);
  		$('#divMsg').hide();
  	  });
  	}
  	function confirmarExclusao(idEntidade, descricao, eventSource){
  	  var msg = 'Confirmar exclusão do veículo com placa <strong>' + descricao + '</strong>?';
  	  bootbox.confirm(msg, function(result){
  		if(result){
  		  var param = {id : idEntidade};
  		  $.getJSON('${actionUrl}/excluir', param, function(data){
  			var msg = data['mensagem'];
  		    var erro = data['erro'];
  		    atualizarAlert(msg, erro);
  		    //Remove a linha da tabela
  		    $(eventSource).closest('tr').remove();
  		  });
  		}
  	  });
  	}
  	function verFoto(idVeiculo){
  	  var imgUrl = '${actionUrl}/ajax_foto?id='+idVeiculo;
  	  $('#imgVeiculo').attr('src', imgUrl);
  	  $('#imageModal').modal({show:true});
  	}
  </script>
</head>
<body>
  <div class="container">
    <h2>Veículos</h2>
    
    <div class="alert alert-dismissible" role="alert" id="divMsg" ${(not empty erro?'erro':'')}>
      <button type="button" class="close" data-hide="alert">
        <span aria-hidden="true">&times;</span>
      </button>
      <p id="pMsg">${mensagem}</p>
    </div>
    
    <a class="btn btn-default" href="${actionUrl}/novo">
      <span class="glyphicon glyphicon-plus"></span> Novo Veículo
    </a>
    
    <div class="panel panel-default">
      <div class="panel-heading">Pesquisa</div>
      <div class="panel-body">
        <form role="form" id="formPesquisa">
          <div class="form-group">
            <label for="campo">CAMPO</label>
            <select name="campo" class="form-control" id="selectCampo">
              <option value="ANO">Ano</option>
              <option value="PLACA">Placa</option>
              <option value="ID_MODELO">Modelo</option>
            </select>
          </div>
          <div class="form-group">
            <label for="valor">VALOR</label>
            <input type="text" name="valor" id="inputValor" class="form-control"/>
          </div>
          <div class="form-group">
            <label for="modelo">MODELO</label>
            <select name="valor" id="selectModelo" class="form-control">
              <c:forEach var="m" items="${modelos}">
                <option value="${m.id}">${m.descricao}</option>
              </c:forEach>
            </select>
          </div>
          <div class="form-group">
            <a class="btn btn-default" id="btnBuscar">
              <span class="glyphicon glyphicon-search"></span> Buscar
            </a>
          </div>
        </form>
        
        <table class="table table-hover" id="tblRegistros">
          <thead>
            <tr>
              <th>Placa</th>
              <th>Fabricante</th>
              <th>Modelo</th>
              <th>Ano</th>
              <th>Cilindradas</th>
              <th colspan="3" class="text-center">Opções</th>
            </tr>
          </thead>
          <tbody id="tbodyRegistros">
            <c:import url="/WEB-INF/views/veiculos/veiculos.jsp" />
          </tbody>
        </table>
      </div>
    </div>
    
    <div id="imageModal" class="modal fade" tabindex="-1" role="dialog" aria-hidden="true">
      <div class="modal-dialog">
        <div class="modal-content">
          <div class="modal-body">
            <img id="imgVeiculo" class="img-responsive">
          </div>
        </div>
      </div>
    </div>
  </div>
</body>
</html>