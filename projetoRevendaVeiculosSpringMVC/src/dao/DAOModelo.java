package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;

import dominio.Fabricante;
import dominio.Modelo;
import dominio.RepositorioModelo;
import dominio.TipoVeiculo;

public class DAOModelo implements RepositorioModelo {
	
	@Autowired
	private DataSource dataSource;
	
	@Override
	public Integer inserir(Modelo m) {
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
			Connection con = dataSource.getConnection();
			PreparedStatement prep = con.prepareStatement("select m.id as idmodelo, " +
					"m.descricao as descmodelo, f.id as idfabricante, " +
					"f.descricao as descfabricante, t.id as idtipo, " +
					"t.descricao as desctipo from modelos m " +
					"inner join fabricantes f on m.idfabricante = f.id " +
					"inner join tipos_veiculo t on m.idtipoveiculo = t.id");
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
			m.setId( rs.getInt("idmodelo") );
			m.setDescricao( rs.getString("descmodelo") );
			
			Fabricante f = new Fabricante();
			f.setDescricao( rs.getString("descfabricante") );
			f.setId( rs.getInt("idfabricante") );
			m.setFabricante( f );
			
			TipoVeiculo tipo = new TipoVeiculo();
			tipo.setId( rs.getInt("idtipo") );
			tipo.setDescricao( rs.getString("desctipo") );
			m.setTipo( tipo );
			
			return m;
		}catch(SQLException ex){
			ex.printStackTrace();
			throw new RuntimeException(ex);
		}
	}
}
