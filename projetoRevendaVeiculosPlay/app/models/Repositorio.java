package models;

import java.util.List;

import dao.DAO;


/**
 * Classe genérica de Repositório com métodos para obtenção, inserção, exclusão e atualização.
 * Uma classe de repositório tem como propósitos isolar a camada web e os objetos de domínio (que 
 * não sejam repositórios) da camada de acesso a dados, bem como   
 * concentrar as regras de negócio relativas à inclusão, alteração, exclusão e busca de instâncias.
 * Esta classe atende as entidades cujas operações de crud não necessitem de requisitos adicionais, 
 * como por exemplo, a classe Fabricante (note que não há uma classe RepositorioFabricante).
 * Para entidades que necessitam de repositório específicos, sugere-se que este repositório genérico 
 * sirva como superclasse de tais repositórios.
 */
public class Repositorio<T>{
	
	protected final DAO<T> dao;
	
	public Repositorio(DAO<T> dao){
		this.dao = dao;
	}
	
	public Integer inserir(T obj){
		return dao.inserir(obj);
	}
	
	public void atualizar(T obj){
		dao.atualizar(obj);
	}
	
	public void excluir(Integer id){
		dao.excluir(id);
	}
	
	public List<T> todos(){
		return dao.todos();
	}
	
	public T getPorId(Integer id){
		return dao.getPorId(id);
	}
}
