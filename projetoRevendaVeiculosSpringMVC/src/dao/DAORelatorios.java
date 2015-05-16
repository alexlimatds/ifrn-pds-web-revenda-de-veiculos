package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.stereotype.Repository;

import dominio.RepositorioRelatorios;
import dominio.relatorios.ModeloMaisVendido;

@Repository
public class DAORelatorios implements RepositorioRelatorios{
	
	@Autowired
	private DataSource dataSource;
	
	private Connection getConnection(){
		return DataSourceUtils.getConnection(dataSource);
	}
	
	@Override
	public List<ModeloMaisVendido> modelosMaisVendidos(Date inicio, Date fim) {
		try{
			Connection con = getConnection();
			String select = "select m.id, m.descricao as modelo, f.descricao as fabricante, "
					+ "count(ven.id) as quantidade "+
					"from modelos m inner join fabricantes f on m.id_fabricante=f.id "+
					"inner join veiculos vei on m.id=vei.id_modelo "+
					"inner join vendas ven on ven.id_veiculo=vei.id "+
					"where ven.data between ? and ? and ven.status=3 "+
					"group by m.id, m.descricao, f.descricao "+
					"order by count(ven.id) desc";
			PreparedStatement prep = con.prepareStatement(select);
			prep.setDate(1, new java.sql.Date(inicio.getTime()));
			prep.setDate(2, new java.sql.Date(fim.getTime()));
			ResultSet rs = prep.executeQuery();
			ArrayList<ModeloMaisVendido> registros = new ArrayList<>();
			int ordem = 1;
			while(rs.next()){
				ModeloMaisVendido modelo = new ModeloMaisVendido(ordem, 
						rs.getString("modelo"), rs.getString("fabricante"), 
						rs.getInt("quantidade"));
				registros.add(modelo);
				ordem++;
			}
			rs.close();
			prep.close();
			
			return registros;
		}catch(SQLException ex){
			ex.printStackTrace();
			throw new RuntimeException(ex);
		}
	}
}
