package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import dominio.FormaDePagamento;
import dominio.RepositorioFormasDePagamento;

@Repository
public class DAOFormaDePagamento implements RepositorioFormasDePagamento {

	@Autowired
	private DataSource dataSource;
	
	@Override
	public List<FormaDePagamento> todas() {
		try{
			Connection con = dataSource.getConnection();
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
