package controllers;

import java.util.List;

import models.*;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;
import dao.DAOModelo;

import views.html.Crud.*;

public class Veiculos extends Controller {

	private final static Form<Veiculo> formVeiculo = new Form<Veiculo>(
			Veiculo.class);
	private final static RepositorioVeiculo repVeiculo = new RepositorioVeiculo();
	private final static Repositorio<Modelo> repModelo = new Repositorio<Modelo>(
			new DAOModelo());

	private static List<Modelo> modelos() {
		return repModelo.todos();
	}

	private static List<Veiculo> todos() {
		return repVeiculo.todos();
	}

	private final static Result INICIO = redirect(routes.Veiculos.listar());

	public static Result salvar() {
		final Form<Veiculo> novoVeiculo = formVeiculo.bindFromRequest();
		if (novoVeiculo.hasErrors()) {
			return ok(veiculos.render(todos(), novoVeiculo, true, modelos()));
		}
		final Veiculo veiculo = novoVeiculo.get();
		if (veiculo.id == null) {
			repVeiculo.inserir(veiculo);
		} else {
			repVeiculo.atualizar(veiculo);
		}
		return INICIO;
	}

	public static Result listar() {
		return ok(veiculos.render(todos(), formVeiculo, false, modelos()));
	}

	public static Result editar(Integer id) {
		final Form<Veiculo> emEdicao = formVeiculo
				.fill(repVeiculo.getPorId(id));
		return ok(veiculos.render(todos(), emEdicao, true, modelos()));
	}

	public static Result remover(Integer id) {
		repVeiculo.excluir(id);
		return INICIO;
	}
}
