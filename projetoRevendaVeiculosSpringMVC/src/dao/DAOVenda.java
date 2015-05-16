package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import dominio.PartePagamento;
import dominio.RepositorioVenda;
import dominio.StatusVenda;
import dominio.Venda;

@Repository
@Transactional(propagation=Propagation.REQUIRED)
public class DAOVenda implements RepositorioVenda{
	
	@Autowired
	private DataSource dataSource;
	
	private Map<Integer, StatusVenda> mapaStatus;
	
	public DAOVenda() {
		mapaStatus = new HashMap<Integer, StatusVenda>();
		for(StatusVenda s : StatusVenda.values())
			mapaStatus.put(s.getId(), s);
	}
	
	private Connection getConnection(){
		return DataSourceUtils.getConnection(dataSource);
	}
	
	@Override
	public Venda getUltimaVendaDoVeiculo(Integer idVeiculo) {
		try{
			Connection con = getConnection();
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
			con = getConnection();
			Integer idVendaGerado = persistirVenda(con, v);
			persistirPartesPagamento(con, idVendaGerado, v.getPartesPagamento());
			
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
	
	private Integer persistirVenda(Connection con, Venda v) throws SQLException{
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
		
		return idVendaGerado;
	}
	
	private void persistirPartesPagamento(Connection con, 
			Integer idVendaGerado, List<PartePagamento> lista) 
					throws SQLException{
		PreparedStatement prep = con.prepareStatement("insert into PARTES_PAGAMENTO "
				+ "(QUANTIA, ID_FORMA_PAGAMENTO, ID_VENDA, ID_COMPRA) values (?, ?, ?, ?)");
		for(PartePagamento pgt : lista){
			prep.setBigDecimal(1, pgt.getQuantia());
			prep.setInt(2, pgt.getFormaPagamento().getId());
			prep.setInt(3, idVendaGerado);
			if(pgt.getCompraRelacionada() != null)
				prep.setInt(4, pgt.getCompraRelacionada().getId());
			else
				prep.setObject(4, null);
			prep.executeUpdate();
		}
		prep.close();
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
