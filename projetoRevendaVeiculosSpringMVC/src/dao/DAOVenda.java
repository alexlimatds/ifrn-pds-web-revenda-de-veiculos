package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import dominio.PartePagamento;
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
	
	@Override
	public Integer inserir(Venda v) {
		Connection con = null;
		try{
			con = dataSource.getConnection();
			con.setAutoCommit(false);
			PreparedStatement prep = con.prepareStatement("insert into VENDAS (DATA, " +
					"DESCONTO, COMISSAO, STATUS, ID_VEICULO, ID_VENDEDOR) " +
					"values (?, ?, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
			prep.setTimestamp(1, new Timestamp(v.getData().getTime()));
			prep.setBigDecimal(2, v.getDesconto());
			prep.setBigDecimal(3, v.getComissao());
			prep.setInt(4, v.getStatus().getId());
			prep.setInt(5, v.getVeiculo().getId());
			prep.setInt(6, v.getVendedor().getId());
			prep.executeUpdate();
			ResultSet rs = prep.getGeneratedKeys();
			rs.next();
			Integer idVendaGerado = rs.getInt(1);
			rs.close();
			prep.close();
			
			//partes pagamento
			PreparedStatement prep2 = con.prepareStatement("insert into PARTES_PAGAMENTO "
					+ "(QUANTIA, ID_FORMA_PAGAMENTO, ID_VENDA) values (?, ?, ?)");
			for(PartePagamento pgt : v.getPartesPagamento()){
				prep2.setBigDecimal(1, pgt.getQuantia());
				prep2.setInt(2, pgt.getFormaPagamento().getId());
				prep2.setInt(3, idVendaGerado);
				prep2.executeUpdate();
			}
			prep2.close();
			con.commit();
			
			return idVendaGerado;
		}catch(SQLException ex){
			if(con != null){
				try {
					con.rollback();
				} catch (SQLException e) {
					e.printStackTrace();
					throw new RuntimeException(e);
				}
			}
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
