package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.stereotype.Repository;

import dominio.FormaDePagamento;
import dominio.RepositorioFormasDePagamento;

@Repository
public class DAOFormaDePagamento implements RepositorioFormasDePagamento {

	@Autowired
	private DataSource dataSource;
	
	private Connection getConnection(){
		return DataSourceUtils.getConnection(dataSource);
	}
	
	@Override
	public List<FormaDePagamento> todas() {
		try{
			Connection con = getConnection();
			PreparedStatement prep = con.prepareStatement("select * from FORMAS_PAGAMENTO");
			ResultSet rs = prep.executeQuery();
			List<FormaDePagamento> formas = new ArrayList<FormaDePagamento>();
			while(rs.next()){
				FormaDePagamento f = montarForma(rs);
				formas.add(f);
			}
			
			return formas;
		}catch(SQLException ex){
			ex.printStackTrace();
			throw new RuntimeException(ex);
		}
	}
	
	@Override
	public FormaDePagamento getPagamentoComVeiculo() {
		try{
			Connection con = getConnection();
			PreparedStatement prep = con.prepareStatement("select * from FORMAS_PAGAMENTO where ID = ?");
			prep.setInt(1, FormaDePagamento.ID_COM_VEICULO);
			ResultSet rs = prep.executeQuery();
			FormaDePagamento forma = null;
			while(rs.next()){
				forma = montarForma(rs);
			}
			
			return forma;
		}catch(SQLException ex){
			ex.printStackTrace();
			throw new RuntimeException(ex);
		}
	}
	
	private FormaDePagamento montarForma(ResultSet rs){
		try{
			FormaDePagamento f = new FormaDePagamento();
			f.setId( rs.getInt("id") );
			f.setDescricao( rs.getString("descricao") );
			
			return f;
		}catch(SQLException ex){
			ex.printStackTrace();
			throw new RuntimeException(ex);
		}
	}
}
