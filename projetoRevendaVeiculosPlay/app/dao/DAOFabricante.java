package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import models.Fabricante;
import play.db.DB;

public class DAOFabricante implements DAO<Fabricante> {
	
	@Override
	public Integer inserir(Fabricante f) {
		try{
			Connection con = DB.getConnection();
			PreparedStatement prep = con.prepareStatement("insert into fabricantes (descricao) values (?)", Statement.RETURN_GENERATED_KEYS);
			prep.setString(1, f.descricao);
			prep.executeUpdate();
			ResultSet rs = prep.getGeneratedKeys();
			rs.next();
			Integer id = rs.getInt(1);
			rs.close();
			
			return id;
		}catch(SQLException ex){
			ex.printStackTrace();
			throw new RuntimeException(ex);
		}
	}

	@Override
	public void atualizar(Fabricante fab) {
		try{
			Connection con = DB.getConnection();
			PreparedStatement prep = con.prepareStatement("update fabricantes set descricao=? where id=?");
			prep.setString(1, fab.descricao);
			prep.setInt(2, fab.id);
			prep.executeUpdate();
		}catch(SQLException ex){
			ex.printStackTrace();
			throw new RuntimeException(ex);
		}
	}

	@Override
	public void excluir(Integer idFabricante) {
		try{
			Connection con = DB.getConnection();
			PreparedStatement prep = con.prepareStatement("delete from fabricantes where id=?");
			prep.setInt(1, idFabricante);
			prep.executeUpdate();
		}catch(SQLException ex){
			ex.printStackTrace();
			throw new RuntimeException(ex);
		}
	}

	@Override
	public List<Fabricante> todos() {
		try{
			Connection con = DB.getConnection();
			PreparedStatement prep = con.prepareStatement("select * from fabricantes");
			ResultSet rs = prep.executeQuery();
			ArrayList<Fabricante> fabricantes = new ArrayList<Fabricante>();
			while(rs.next()){
				Fabricante f = montarFabricante(rs);
				fabricantes.add(f);
			}
			
			return fabricantes;
		}catch(SQLException ex){
			ex.printStackTrace();
			throw new RuntimeException(ex);
		}
	}
	
	@Override
	public Fabricante getPorId(Integer id){
		try{
			Connection con = DB.getConnection();
			PreparedStatement prep = con.prepareStatement("select * from fabricantes where id=?");
			prep.setInt(1, id);
			ResultSet rs = prep.executeQuery();
			if(rs.next()){
				return montarFabricante(rs);
			}
			else{ //não há fabricante com o id informado
				return null;
			}
		}catch(SQLException ex){
			ex.printStackTrace();
			throw new RuntimeException(ex);
		}
	}
	
	/**
	 * Cria uma instância de Fabricante a partir de um registro apontado por um ResultSet.
	 * @param rs ResultSet já apontando para o registro.
	 * @return
	 */
	private Fabricante montarFabricante(ResultSet rs){
		try{
			Fabricante f = new Fabricante();
			f.id = rs.getInt("id");
			f.descricao = rs.getString("descricao");
			
			return f;
		}catch(SQLException ex){
			ex.printStackTrace();
			throw new RuntimeException(ex);
		}
	}
}
