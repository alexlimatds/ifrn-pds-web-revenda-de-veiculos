package controllers;

import java.util.List;

import models.*;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.Crud.fabricantes;
import dao.DAOFabricante;

public class Fabricantes extends Controller {

	private final static Form<Fabricante> formFabricante = new Form<Fabricante>(
			Fabricante.class);
	private final static Repositorio<Fabricante> repFabricante = new Repositorio<Fabricante>(
			new DAOFabricante());

	private final static List<Fabricante> todos() {
		return repFabricante.todos();
	}

	private final static Result INICIO = redirect(routes.Fabricantes.listar());

	public static Result salvar() {
		final Form<Fabricante> novoFabricante = formFabricante
				.bindFromRequest();
		final Fabricante fabricante = novoFabricante.get();
		if (fabricante.id == null) {
			repFabricante.inserir(fabricante);
			flash("success", "Inserido com sucesso.");
		} else {
			repFabricante.atualizar(fabricante);
			flash("success", "Atualizado com sucesso.");
		}
		return INICIO;
	}

	public static Result listar() {
		return ok(fabricantes.render(todos(), formFabricante, false));
	}

	public static Result editar(Integer id) {
		Form<Fabricante> emEdicao = formFabricante.fill(repFabricante
				.getPorId(id));
		return ok(fabricantes.render(todos(), emEdicao, true));
	}

	public static Result remover(Integer id) {
		repFabricante.excluir(id);
		return INICIO;
	}
}
