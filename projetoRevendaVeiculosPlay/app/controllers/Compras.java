package controllers;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import models.Compra;
import models.ModeloException;
import models.RepositorioCompra;
import models.RepositorioVeiculo;
import models.Veiculo;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;

public class Compras extends Controller {
	
	private static Form<Veiculo> formPlaca = new Form<Veiculo>(Veiculo.class);
	private static Form<Compra> formCompra = new Form<Compra>(Compra.class);
	private static RepositorioVeiculo repositorioVeiculo = new RepositorioVeiculo();
	private static RepositorioCompra repositorioCompra = new RepositorioCompra();
	
	public static Result iniciar(){
		return ok(views.html.inicio_compra.render(formPlaca));
	}
	
	public static Result selecionarVeiculo(){
		Form<Veiculo> form = formPlaca.bindFromRequest();
		String placa = form.get().placa;
		Veiculo veiculo = repositorioVeiculo.getPorPlaca(placa);
		if(veiculo == null){
			//veículo não cadastrado
			return ok(views.html.mensagem.render("Veículo não cadastrado"));
		}
		else if(!veiculo.isEmPosseDaLoja()){
			//não está em posse da loja e pode ser comprado
			return ok(views.html.compra.render(veiculo, formCompra));
		}
		else{
			//em posse da loja, não pode ser comprado
			return ok(views.html.mensagem.render("Veículo já pertence à loja"));
		}
	}
	
	public static Result salvar() throws ParseException{
		Form<Compra> form = formCompra.bindFromRequest();
		Compra compra = form.get();
		Integer idVeiculo = Integer.parseInt(form.field("idVeiculo").value());
		Veiculo v = repositorioVeiculo.getPorId(idVeiculo);
		SimpleDateFormat fmt = new SimpleDateFormat("dd/MM/yyyy HH:mm");
		Date dataCompra = fmt.parse(form.field("data").value());
		compra.data = dataCompra;
		String msg = null;
		try{
			compra.veiculo = v;
			repositorioCompra.inserir(compra);
			msg = "Compra registrada com sucesso";
		}
		catch(ModeloException ex){
			ex.printStackTrace();
			msg = "Veículo em posse da loja";
		}
		return ok(views.html.mensagem.render(msg));
	}
}