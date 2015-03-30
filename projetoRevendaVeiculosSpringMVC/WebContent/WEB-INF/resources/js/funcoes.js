$(document).ready(function(){
  //Comportamento do botão de fechamento do alert
  $("[data-hide]").on("click", function(){
    $(this).closest("." + $(this).attr("data-hide")).hide();
  });
  //Configura alert
  var $alert = $('.alert');
  if(!$alert.children('p:first-of-type').is(':empty')){
	var erro = $alert.attr('erro');
	if(erro === undefined)
	  $alert.addClass('alert-success');
	else
	  $alert.addClass('alert-danger');
  }else{
	$alert.hide();
  }
});
//Exibe e atualiza o alert de mensagens.
//mensagem	Mensagem a ser exibida.
//erro		Indica se a mensagem é de erro (true) ou de sucesso (false).
function atualizarAlert(mensagem, erro){
  if(erro === undefined)
	erro = false;
  $('#pMsg').text(mensagem);
  $('#divMsg').show();
  $('#divMsg').toggleClass('alert-danger', erro);
  $('#divMsg').toggleClass('alert-success', !erro);
}