package dao;

import java.util.List;

import play.libs.F;
import play.libs.F.None;

import models.Usuario;

public class DAOUsuario implements DAO<Usuario> {

	@Override
	public Integer inserir(Usuario f) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void atualizar(Usuario f) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void excluir(Integer id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<Usuario> todos() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Usuario getPorId(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

	public F.Option<Usuario> getPorLogin(String login){
		return new F.None<Usuario>(); 
	}
}
