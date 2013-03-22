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
import dao.DAOVeiculo;

public class Veiculos extends Controller {
	
	private static Form<Veiculo> formVeiculo = new Form<Veiculo>(Veiculo.class);
	private static RepositorioVeiculo repVeiculo = new RepositorioVeiculo(new DAOVeiculo());
	private static Repositorio<Modelo> repModelo = new Repositorio<Modelo>(new DAOModelo());
	private static List<Modelo> modelos;
	
	public static Result salvar() {
		Form<Veiculo> novoVeiculo = formVeiculo.bindFromRequest();
		Integer idModelo = Integer.parseInt(novoVeiculo.field("modelo.id").value());
		Veiculo veiculo = novoVeiculo.get();
		Modelo modelo = new Modelo(idModelo, null, null, null);
		veiculo.modelo = modelo;
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
		modelos = repModelo.todos();
		
		return ok(views.html.Crud.veiculos.render(lista, formVeiculo, null, modelos));
	}
	
	public static Result editar(Integer id) {
		List<Veiculo> lista = repVeiculo.todos();
		Veiculo emEdicao = repVeiculo.getPorId(id);
		
		return ok(views.html.Crud.veiculos.render(lista, formVeiculo.fill(emEdicao), emEdicao,  modelos));
	}
	
	public static Result remover(Integer id) {
		repVeiculo.excluir(id);
		
		return redirect(routes.Veiculos.listar());
	}
}
