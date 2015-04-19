package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import dominio.RepositorioVenda;
import dominio.StatusVenda;
import dominio.Venda;

@Repository
public class DAOVenda implements RepositorioVenda{
	
	@Autowired
	private DataSource dataSource;
	private Map<Integer, StatusVenda> mapaStatus;
	
	public DAOVenda() {
		mapaStatus = new HashMap<Integer, StatusVenda>();
		for(StatusVenda s : StatusVenda.values())
			mapaStatus.put(s.getId(), s);
	}
	
	@Override
	public Venda getUltimaVendaDoVeiculo(Integer idVeiculo) {
		try{
			Connection con = dataSource.getConnection();
			String query = "select * from VENDAS where ID_VEICULO=? and "
					+ "DATA=(select max(DATA) from VENDAS where ID_VEICULO=?)";
			PreparedStatement prep = con.prepareStatement(query);
			prep.setInt(1, idVeiculo);
			prep.setInt(2, idVeiculo);
			ResultSet rs = prep.executeQuery();
			Venda ultimaVenda = null;
			if(rs.next())
				ultimaVenda = montarVenda(rs);
			rs.close();
			prep.close();
			return ultimaVenda;
		}catch(SQLException ex){
			ex.printStackTrace();
			throw new RuntimeException(ex);
		}
	}
	
	private Venda montarVenda(ResultSet rs) throws SQLException{
		Venda v = new Venda();
		v.setData(rs.getDate("DATA"));
		v.setId(rs.getInt("ID"));
		v.setObs(rs.getString("OBS"));
		v.setDesconto(rs.getBigDecimal("DESCONTO"));
		v.setComissao(rs.getBigDecimal("COMISSAO"));
		Integer idStatus = rs.getInt("STATUS");
		v.setStatus(mapaStatus.get(idStatus));
		
		return v;
	}
}
