package dao;

import java.io.ByteArrayInputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import dominio.Modelo;
import dominio.RepositorioVeiculo;
import dominio.Veiculo;

@Repository
public class DAOVeiculo implements RepositorioVeiculo{
	
	@Autowired
	private DataSource dataSource;
	@Autowired
	private DAOModelo daoModelo;
	private final String selectQuery = "select ID, " +
			"ANO, CHASSI, PLACA, FOTO, CILINDRADAS, " +
			"ID_MODELO, MIME_TYPE_FOTO " +
			"from VEICULOS"; 
	
	@Override
	public Integer inserir(Veiculo v) {
		try{
			Connection con = dataSource.getConnection();
			PreparedStatement prep = con.prepareStatement("insert into VEICULOS " +
					"(ANO, PLACA, CILINDRADAS, CHASSI, FOTO, MIME_TYPE_FOTO, ID_MODELO) " +
					"values (?, ?, ?, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
			prep.setInt(1, v.getAnoFabricacao());
			prep.setString(2, v.getPlaca());
			prep.setObject(3, v.getCilindradas());
			prep.setString(4, v.getChassi());
			if(v.getFoto() != null){
				prep.setBinaryStream(5, new ByteArrayInputStream(v.getFoto()));
				prep.setString(6, v.getMimeTypeFoto());
			}
			else{
				prep.setObject(5, null);
				prep.setString(6, null);
			}
			prep.setInt(7, v.getModelo().getId());
			prep.executeUpdate();
			
			//obtém id gerado pelo banco
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
	public void atualizar(Veiculo v) {
		try{
			Connection con = dataSource.getConnection();
			PreparedStatement prep = con.prepareStatement("update VEICULOS " +
					"set ANO=?, PLACA=?, CHASSI=?, CILINDRADAS=?, FOTO=?, MIME_TYPE_FOTO=?, "
					+ "ID_MODELO=? where ID=?");
			prep.setInt(1, v.getAnoFabricacao());
			prep.setString(2, v.getPlaca());
			prep.setString(3, v.getChassi());
			prep.setObject(4, v.getCilindradas());
			if(v.getFoto() != null){
				prep.setBinaryStream(5, new ByteArrayInputStream(v.getFoto()));
				prep.setString(6, v.getMimeTypeFoto());
			}
			else{
				prep.setObject(5, null);
				prep.setString(6, null);
			}
			prep.setInt(7, v.getModelo().getId());
			prep.setInt(8, v.getId());
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
			PreparedStatement prep = con.prepareStatement("delete from VEICULOS where ID=?");
			prep.setInt(1, id);
			prep.executeUpdate();
		}catch(SQLException ex){
			ex.printStackTrace();
			throw new RuntimeException(ex);
		}
	}

	@Override
	public List<Veiculo> todos() {
		ArrayList<Veiculo> veiculos = new ArrayList<Veiculo>();
		try{
			Connection con = dataSource.getConnection();
			PreparedStatement prep = con.prepareStatement(selectQuery);
			ResultSet rs = prep.executeQuery();
			while(rs.next()){
				Veiculo v = montarVeiculo(rs);
				veiculos.add(v);
			}
			return veiculos;
		}catch(SQLException ex){
			ex.printStackTrace();
			throw new RuntimeException(ex);
		}
	}

	@Override
	public Veiculo getPorId(Integer id) {
		try{
			Connection con = dataSource.getConnection();
			PreparedStatement prep = con.prepareStatement(selectQuery + " where ID=?");
			prep.setInt(1, id);
			ResultSet rs = prep.executeQuery();
			if(rs.next()){
				return montarVeiculo(rs);
			}
			else{ //não há veículo com o id informado
				return null;
			}
		}catch(SQLException ex){
			ex.printStackTrace();
			throw new RuntimeException(ex);
		}
	}
	
	public Date[] getDatasUltimasTransacoes(Integer idVeiculo){
		try{
			Connection con = dataSource.getConnection();
			PreparedStatement prep = con.prepareStatement("select placa as placa, " +
					"max(c.data) as dcompra, max(ven.data) as dvenda " +
					"from veiculos vei left join compras c on " +
					"vei.id = c.idveiculo left join vendas ven on vei.id = ven.idveiculo " +
					"where vei.id=?");
			prep.setInt(1, idVeiculo);
			ResultSet rs = prep.executeQuery();
			if(rs.next()){
				Date[] datas = new Date[2];
				datas[0] = rs.getDate("dcompra");
				datas[1] = rs.getDate("dvenda");
				
				return datas;
			}
			else{ //não há veículo com o id informado
				return null;
			}
		}catch(SQLException ex){
			ex.printStackTrace();
			throw new RuntimeException(ex);
		}
	}
	
	public List<Veiculo> getPor(String campo, Object valor){
		try{
			Connection con = dataSource.getConnection();
			PreparedStatement prep = con.prepareStatement(selectQuery + " where "+ campo +"=?");
			prep.setObject(1, valor);
			ResultSet rs = prep.executeQuery();
			ArrayList<Veiculo> lista = new ArrayList<>();
			while(rs.next()){
				lista.add( montarVeiculo(rs) );
			}
			return lista;
		}catch(SQLException ex){
			ex.printStackTrace();
			throw new RuntimeException(ex);
		}
	}
	
	/**
	 * Cria uma instância de Veiculo a partir de um registro apontado por um ResultSet.
	 * @param rs ResultSet já apontando para o registro.
	 * @return
	 */
	private Veiculo montarVeiculo(ResultSet rs){
		try{
			Veiculo v = new Veiculo();
			v.setId( rs.getInt("ID") );
			v.setAnoFabricacao( rs.getInt("ANO") );
			v.setChassi( rs.getString("CHASSI") );
			v.setCilindradas( rs.getInt("CILINDRADAS") );
			v.setFoto( rs.getBytes("FOTO") );
			v.setMimeTypeFoto( rs.getString("MIME_TYPE_FOTO") );
			v.setPlaca( rs.getString("PLACA") );
			
			Modelo modelo = daoModelo.getPorId( rs.getInt("ID_MODELO") );
			v.setModelo( modelo );
			
			return v;
		}catch(SQLException ex){
			ex.printStackTrace();
			throw new RuntimeException(ex);
		}
	}
}
