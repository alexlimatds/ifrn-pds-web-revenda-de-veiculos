package controllers;

import java.util.List;

import models.Modelo;
import models.Repositorio;
import models.RepositorioVeiculo;
import models.Veiculo;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;
import dao.DAOModelo;

public class Veiculos extends Controller {
	
	private static Form<Veiculo> formVeiculo = new Form<Veiculo>(Veiculo.class);
	private static RepositorioVeiculo repVeiculo = new RepositorioVeiculo();
	private static Repositorio<Modelo> repModelo = new Repositorio<Modelo>(new DAOModelo());
	private static List<Modelo> modelos() {
		return repModelo.todos();
	}
	
	public static Result salvar() {
		Form<Veiculo> novoVeiculo = formVeiculo.bindFromRequest();
		if (novoVeiculo.hasErrors()){
			List<Veiculo> lista = repVeiculo.todos();
			return ok(views.html.Crud.veiculos.render(lista, novoVeiculo, true, modelos()));
		}
		Veiculo veiculo = novoVeiculo.get();
		if(veiculo.id == null){
			repVeiculo.inserir(veiculo);
		}
		else{
			repVeiculo.atualizar(veiculo);
		}
		return redirect(routes.Veiculos.listar());
	}
	
	public static Result listar() {
		List<Veiculo> lista = repVeiculo.todos();
		return ok(views.html.Crud.veiculos.render(lista, formVeiculo, false, modelos()));
	}
	
	public static Result editar(Integer id) {
		List<Veiculo> lista = repVeiculo.todos();
		Veiculo emEdicao = repVeiculo.getPorId(id);
		
		return ok(views.html.Crud.veiculos.render(lista, formVeiculo.fill(emEdicao), true,  modelos()));
	}
	
	public static Result remover(Integer id) {
		repVeiculo.excluir(id);
		
		return redirect(routes.Veiculos.listar());
	}
}
