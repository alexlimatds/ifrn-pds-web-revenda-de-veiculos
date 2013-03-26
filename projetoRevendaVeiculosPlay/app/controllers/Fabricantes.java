package controllers;

import java.util.List;

import models.Fabricante;
import models.Repositorio;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;
import dao.DAOFabricante;
import views.html.Crud.*;

public class Fabricantes extends Controller {

	private static Form<Fabricante> formFabricante = new Form<Fabricante>(
			Fabricante.class);
	private static Repositorio<Fabricante> repFabricante = new Repositorio<Fabricante>(
			new DAOFabricante());

    public static Result salvar() { 
		Form<Fabricante> novoFabricante = formFabricante.bindFromRequest();
		Fabricante fabricante = novoFabricante.get();
		if (fabricante.id == null) {
			repFabricante.inserir(fabricante);
			flash("sucess", "Inserido com sucesso.");
		} else {
			repFabricante.atualizar(fabricante);
			flash("sucess", "Atualizado com sucesso.");
		}
		return redirect(routes.Fabricantes.listar());
	}

	public static Result listar() {
		List<Fabricante> lista = repFabricante.todos();
		return ok(fabricantes.render(lista, formFabricante, false));
	}

	public static Result editar(Integer id) {
		List<Fabricante> lista = repFabricante.todos();
		Fabricante emEdicao = repFabricante.getPorId(id);
		return ok(fabricantes.render(lista, formFabricante.fill(emEdicao), true));
	}

	public static Result remover(Integer id) {
		repFabricante.excluir(id);
		return redirect(routes.Fabricantes.listar());
	}
}
