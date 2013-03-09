package controllers;

import java.util.List;

import models.Fabricante;
import models.Repositorio;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;
import dao.DAOFabricante;

public class Fabricantes extends Controller {
	
	private static Form<Fabricante> formFabricante = new Form<Fabricante>(Fabricante.class);
	private static Repositorio<Fabricante> repFabricante = new Repositorio<Fabricante>(new DAOFabricante());
	
	public static Result salvar() {
		Form<Fabricante> novoFabricante = formFabricante.bindFromRequest();
		Fabricante fabricante = novoFabricante.get();
		if(fabricante.id == null){
			repFabricante.inserir(fabricante);
		}
		else{
			repFabricante.atualizar(fabricante);
		}
		
		return redirect(routes.Fabricantes.listar());
	}
	
	public static Result listar() {
		List<Fabricante> lista = repFabricante.todos();
		
		return ok(views.html.Crud.fabricantes.render(lista, formFabricante, null));
	}
	
	public static Result editar(Integer id) {
		List<Fabricante> lista = repFabricante.todos();
		Fabricante emEdicao = repFabricante.getPorId(id);
		
		return ok(views.html.Crud.fabricantes.render(lista, formFabricante.fill(emEdicao), emEdicao));
	}
	
	public static Result remover(Integer id) {
		repFabricante.excluir(id);
		
		return redirect(routes.Fabricantes.listar());
	}
}
