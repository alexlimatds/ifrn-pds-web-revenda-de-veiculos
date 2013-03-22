package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import models.Fabricante;
import models.Modelo;
import models.TipoVeiculo;
import play.db.DB;

public class DAOModelo implements DAO<Modelo> {
	
	@Override
	public Integer inserir(Modelo f) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void atualizar(Modelo f) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void excluir(Integer id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<Modelo> todos() {
		ArrayList<Modelo> modelos = new ArrayList<Modelo>();
		try{
			Connection con = DB.getConnection();
			PreparedStatement prep = con.prepareStatement("select m.id as idmodelo, m.descricao as descmodelo, f.id as idfabricante, " +
					"f.descricao as descfabricante, t.id as idtipo, t.descricao as desctipo from modelos m " +
					"inner join fabricantes f on m.idfabricante = f.id inner join tipos_veiculo t on m.idtipoveiculo = t.id");
			ResultSet rs = prep.executeQuery();
			while(rs.next()){
				Modelo m = montarModelo(rs);
				modelos.add(m);
			}
			return modelos;
		}catch(SQLException ex){
			ex.printStackTrace();
			throw new RuntimeException(ex);
		}
	}

	@Override
	public Modelo getPorId(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}
	
	/**
	 * Cria uma instância de Modelo a partir de um registro apontado por um ResultSet.
	 * @param rs ResultSet já apontando para o registro.
	 * @return
	 */
	private Modelo montarModelo(ResultSet rs){
		try{
			Modelo m = new Modelo();
			m.id = rs.getInt("idmodelo");
			m.descricao = rs.getString("descmodelo");
			
			Fabricante f = new Fabricante();
			f.descricao = rs.getString("descfabricante");
			f.id = rs.getInt("idfabricante");
			m.fabricante = f;
			
			TipoVeiculo tipo = new TipoVeiculo();
			tipo.id = rs.getInt("idtipo");
			tipo.descricao = rs.getString("desctipo");
			m.tipo = tipo;
			
			return m;
		}catch(SQLException ex){
			ex.printStackTrace();
			throw new RuntimeException(ex);
		}
	}
}
