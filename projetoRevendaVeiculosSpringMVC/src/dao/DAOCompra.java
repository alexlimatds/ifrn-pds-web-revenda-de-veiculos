package dao;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import dominio.Compra;
import dominio.RepositorioCompra;

@Repository
public class DAOCompra implements RepositorioCompra, Serializable{
	
	@Autowired
	private DataSource dataSource;

	@Override
	public Integer inserir(Compra c) {
		try{
			Connection con = dataSource.getConnection();
			PreparedStatement prep = con.prepareStatement("insert into COMPRAS (DATA, PRECO, OBS, ID_VEICULO) " +
					"values (?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
			prep.setTimestamp(1, new Timestamp(c.getData().getTime()));
			prep.setBigDecimal(2, c.getPreco());
			prep.setString(3, c.getObs());
			prep.setInt(4, c.getVeiculo().getId());
			prep.executeUpdate();
			ResultSet rs = prep.getGeneratedKeys();
			rs.next();
			Integer id = rs.getInt(1);
			rs.close();
			prep.close();
			
			return id;
		}catch(SQLException ex){
			ex.printStackTrace();
			throw new RuntimeException(ex);
		}
	}
	
	@Override
	public void excluir(Integer id) {
		try{
			Connection con = dataSource.getConnection();
			PreparedStatement prep = con.prepareStatement("delete from COMPRAS where ID=?");
			prep.setInt(1, id);
			prep.executeUpdate();
			prep.close();
		}catch(SQLException ex){
			ex.printStackTrace();
			throw new RuntimeException(ex);
		}
	}

	@Override
	public Compra getUltimaCompraDoVeiculo(Integer idVeiculo) {
		try{
			Connection con = dataSource.getConnection();
			String query = "select * from COMPRAS where ID_VEICULO=? and "
					+ "DATA=(select max(DATA) from COMPRAS where ID_VEICULO=?)";
			PreparedStatement prep = con.prepareStatement(query);
			prep.setInt(1, idVeiculo);
			prep.setInt(2, idVeiculo);
			ResultSet rs = prep.executeQuery();
			Compra ultimaCompra = null;
			if(rs.next())
				ultimaCompra = montarCompra(rs);
			rs.close();
			prep.close();
			return ultimaCompra;
		}catch(SQLException ex){
			ex.printStackTrace();
			throw new RuntimeException(ex);
		}
	}
	
	private Compra montarCompra(ResultSet rs) throws SQLException{
		Compra c = new Compra();
		c.setData(rs.getDate("DATA"));
		c.setId(rs.getInt("ID"));
		c.setObs(rs.getString("OBS"));
		c.setPreco(rs.getBigDecimal("PRECO"));
		return c;
	}
}
