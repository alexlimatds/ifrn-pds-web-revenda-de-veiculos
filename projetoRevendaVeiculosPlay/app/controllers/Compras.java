package controllers;

import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.text.DateFormat;
import java.util.Date;

import models.*;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;

import views.html.*;

public class Compras extends Controller {

	private static Form<Veiculo> formPlaca = new Form<Veiculo>(Veiculo.class);
	private static Form<Compra> formCompra = new Form<Compra>(Compra.class);
	private static RepositorioVeiculo repositorioVeiculo = new RepositorioVeiculo();
	private static RepositorioCompra repositorioCompra = new RepositorioCompra();

	public static Result iniciar() {
		return ok(inicio_compra.render(formPlaca));
	}

	public static Result selecionarVeiculo() {
		final Form<Veiculo> form = formPlaca.bindFromRequest();
		final String placa = form.get().placa;
		final Veiculo veiculo = repositorioVeiculo.getPorPlaca(placa);
		if (veiculo == null) {
			// veículo não cadastrado
			return ok(mensagem.render("Veículo não cadastrado"));
		} else if (veiculo.isEmPosseDaLoja()) {
			// em posse da loja, não pode ser comprado
			return ok(mensagem.render("Veículo já pertence à loja"));
		} else {
			// não está em posse da loja e pode ser comprado
			return ok(compra.render(veiculo, formCompra));
		}
	}

	public static Result salvar() {
		final Form<Compra> form = formCompra.bindFromRequest();
		final Compra compra = form.get();
		final Integer idVeiculo = Integer.parseInt(form.field("idVeiculo")
				.value());
		final Veiculo v = repositorioVeiculo.getPorId(idVeiculo);
		final DateFormat fmt = new SimpleDateFormat("dd/MM/yyyy HH:mm");
		final Date dataCompra = fmt.parse(form.field("data").value(),
				new ParsePosition(0));
		compra.data = dataCompra;
		String msg;
		try {
			compra.veiculo = v;
			repositorioCompra.inserir(compra);
			msg = "Compra registrada com sucesso";
		} catch (ModeloException ex) {
			msg = "Veículo em posse da loja";
		}
		return ok(mensagem.render(msg));
	}
}