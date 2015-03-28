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

import dominio.RepositorioTipoVeiculo;
import dominio.TipoVeiculo;

@Repository
public class DAOTipoVeiculo implements RepositorioTipoVeiculo{
	
	@Autowired
	private DataSource dataSource;
	
	@Override
	public Integer inserir(TipoVeiculo tipo) {
		try{
			Connection con = dataSource.getConnection();
			PreparedStatement prep = con.prepareStatement("insert into TIPOS_VEICULO (DESCRICAO) "
					+ "values (?)", Statement.RETURN_GENERATED_KEYS);
			prep.setString(1, tipo.getDescricao());
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
	public void atualizar(TipoVeiculo tipo) {
		try{
			Connection con = dataSource.getConnection();
			PreparedStatement prep = con.prepareStatement("update TIPOS_VEICULO set DESCRICAO=? where ID=?");
			prep.setString(1, tipo.getDescricao());
			prep.setInt(2, tipo.getId());
			prep.executeUpdate();
		}catch(SQLException ex){
			ex.printStackTrace();
			throw new RuntimeException(ex);
		}
	}

	@Override
	public void excluir(Integer idTipoVeiculo) {
		try{
			Connection con = dataSource.getConnection();
			PreparedStatement prep = con.prepareStatement("delete from TIPOS_VEICULO where ID=?");
			prep.setInt(1, idTipoVeiculo);
			prep.executeUpdate();
		}catch(SQLException ex){
			ex.printStackTrace();
			throw new RuntimeException(ex);
		}
	}

	@Override
	public List<TipoVeiculo> todos() {
		try{
			Connection con = dataSource.getConnection();
			PreparedStatement prep = con.prepareStatement("select * from TIPOS_VEICULO");
			ResultSet rs = prep.executeQuery();
			List<TipoVeiculo> tipos = new ArrayList<TipoVeiculo>();
			while(rs.next()){
				TipoVeiculo t = montarTipo(rs);
				tipos.add(t);
			}
			
			return tipos;
		}catch(SQLException ex){
			ex.printStackTrace();
			throw new RuntimeException(ex);
		}
	}
	
	@Override
	public TipoVeiculo getPorId(Integer id){
		try{
			Connection con = dataSource.getConnection();
			PreparedStatement prep = con.prepareStatement("select * from TIPOS_VEICULO where ID=?");
			prep.setInt(1, id);
			ResultSet rs = prep.executeQuery();
			if(rs.next()){
				return montarTipo(rs);
			}
			else{ //não há tipo com o id informado
				return null;
			}
		}catch(SQLException ex){
			ex.printStackTrace();
			throw new RuntimeException(ex);
		}
	}
	
	/**
	 * Cria uma instância de TipoVeiculo a partir de um registro apontado por um ResultSet.
	 * @param rs ResultSet já apontando para o registro.
	 * @return
	 */
	private TipoVeiculo montarTipo(ResultSet rs){
		try{
			TipoVeiculo t = new TipoVeiculo();
			t.setId( rs.getInt("id") );
			t.setDescricao( rs.getString("descricao") );
			
			return t;
		}catch(SQLException ex){
			ex.printStackTrace();
			throw new RuntimeException(ex);
		}
	}
}
