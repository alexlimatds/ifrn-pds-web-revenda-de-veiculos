package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.List;

import models.Compra;
import play.db.DB;

public class DAOCompra implements DAO<Compra> {

	@Override
	public Integer inserir(Compra c) {
		try{
			Connection con = DB.getConnection();
			PreparedStatement prep = con.prepareStatement("insert into compras (data, preco, obs, idveiculo) " +
					"values (?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
			prep.setTimestamp(1, new Timestamp(c.data.getTime()));
			prep.setBigDecimal(2, c.preco);
			prep.setString(3, c.obs);
			prep.setInt(4, c.veiculo.id);
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
	public void atualizar(Compra c) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void excluir(Integer id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<Compra> todos() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Compra getPorId(Integer id) {
		// TODO Auto-generated method stub
		return null;
	}

}
