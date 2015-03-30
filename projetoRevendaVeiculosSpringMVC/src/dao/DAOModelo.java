package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import dominio.Fabricante;
import dominio.Modelo;
import dominio.RepositorioModelo;
import dominio.TipoVeiculo;

@Repository
public class DAOModelo implements RepositorioModelo {
	
	@Autowired
	private DataSource dataSource;
	private final String selectQuery = "select m.id as idmodelo, " +
			"m.descricao as descmodelo, f.id as idfabricante, " +
			"f.descricao as descfabricante, t.id as idtipo, " +
			"t.descricao as desctipo from modelos m " +
			"inner join fabricantes f on m.id_fabricante = f.id " +
			"inner join tipos_veiculo t on m.id_tipo_veiculo = t.id";
	
	@Override
	public Integer inserir(Modelo m) {
		try{
			Connection con = dataSource.getConnection();
			PreparedStatement prep = con.prepareStatement("insert into MODELOS (DESCRICAO, "
					+ "ID_FABRICANTE, ID_TIPO_VEICULO) values (?, ?, ?)", 
					Statement.RETURN_GENERATED_KEYS);
			prep.setString(1, m.getDescricao());
			prep.setInt(2, m.getFabricante().getId());
			prep.setInt(3, m.getTipo().getId());
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
	public void atualizar(Modelo m) {
		try{
			Connection con = dataSource.getConnection();
			PreparedStatement prep = con.prepareStatement("update MODELOS set DESCRICAO=?, "
					+ "ID_FABRICANTE=?, ID_TIPO_VEICULO=? where ID=?");
			prep.setString(1, m.getDescricao());
			prep.setInt(2, m.getFabricante().getId());
			prep.setInt(3, m.getTipo().getId());
			prep.setInt(4, m.getId());
			prep.executeUpdate();
		}catch(SQLException ex){
			ex.printStackTrace();
			throw new RuntimeException(ex);
		}
	}

	@Override
	public void excluir(Integer id) {
		try{
			Connection con = dataSource.getConnection();
			PreparedStatement prep = con.prepareStatement("delete from MODELOS where ID=?");
			prep.setInt(1, id);
			prep.executeUpdate();
		}catch(SQLException ex){
			ex.printStackTrace();
			throw new RuntimeException(ex);
		}
	}

	@Override
	public List<Modelo> todos() {
		ArrayList<Modelo> modelos = new ArrayList<Modelo>();
		try{
			Connection con = dataSource.getConnection();
			PreparedStatement prep = con.prepareStatement(selectQuery);
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
		try{
			Connection con = dataSource.getConnection();
			PreparedStatement prep = con.prepareStatement(selectQuery + " where m.id=?");
			prep.setInt(1, id);
			ResultSet rs = prep.executeQuery();
			if(rs.next()){
				return montarModelo(rs);
			}
			else{ //não há modelo com o id informado
				return null;
			}
		}catch(SQLException ex){
			ex.printStackTrace();
			throw new RuntimeException(ex);
		}
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
