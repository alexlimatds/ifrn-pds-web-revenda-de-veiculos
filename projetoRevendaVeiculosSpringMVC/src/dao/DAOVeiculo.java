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
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import dominio.Foto;
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
			"ANO, CHASSI, PLACA, CILINDRADAS, " +
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
				prep.setBinaryStream(5, new ByteArrayInputStream(v.getFoto().getBytes()));
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
	public void atualizar(Veiculo v, Foto novaFoto) {
		NamedParameterJdbcTemplate jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
		MapSqlParameterSource paramSource = new MapSqlParameterSource();
		paramSource.addValue("ano", v.getAnoFabricacao());
		paramSource.addValue("placa", v.getPlaca());
		paramSource.addValue("chassi", v.getChassi());
		paramSource.addValue("cilindradas", v.getCilindradas());
		paramSource.addValue("idModelo", v.getModelo().getId());
		paramSource.addValue("id", v.getId());
		String update = null;
		if(novaFoto != null){
			update = "update VEICULOS set ANO=:ano, PLACA=:placa, CHASSI=:chassi, "
					+ "CILINDRADAS=:cilindradas, FOTO=:foto, MIME_TYPE_FOTO=:mime, "
					+ "ID_MODELO=:idModelo where ID=:id";
			paramSource.addValue("foto", novaFoto.getBytes());
			paramSource.addValue("mime", novaFoto.getMimeType());
		}
		else{
			update = "update VEICULOS set ANO=:ano, PLACA=:placa, CHASSI=:chassi, "
					+ "CILINDRADAS=:cilindradas, ID_MODELO=:idModelo where ID=:id";
		}
		jdbcTemplate.update(update, paramSource);
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
			PreparedStatement prep = con.prepareStatement("select PLACA as placa, " +
					"max(c.DATA) as dcompra, max(ven.DATA) as dvenda " +
					"from VEICULOS vei left join COMPRAS c on vei.ID = c.ID_VEICULO " +
					"left join VENDAS ven on vei.ID = ven.ID_VEICULO " +
					"where vei.ID=?");
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
	
	public Veiculo getPorPlaca(String placa){
		List<Veiculo> lista = getPor(PLACA, placa);
		if(lista.isEmpty())
			return null;
		return lista.get(0);
	}
	
	/**
	 * Retorna a foto, como mime type e bytes, cadastrada para um 
	 * determinado veículo.
	 */
	public Foto getFoto(Integer idVeiculo){
		try{
			Connection con = dataSource.getConnection();
			String select = "select FOTO, MIME_TYPE_FOTO from VEICULOS where ID=?";
			PreparedStatement prep = con.prepareStatement(select);
			prep.setObject(1, idVeiculo);
			ResultSet rs = prep.executeQuery();
			Foto foto = null;
			if(rs.next()){
				byte[] bytes = rs.getBytes("FOTO");
				String mimeType = rs.getString("MIME_TYPE_FOTO");
				foto = new Foto(bytes, mimeType);
			}
			return foto;
		}catch(SQLException ex){
			ex.printStackTrace();
			throw new RuntimeException(ex);
		}
	}
	
	/**
	 * Cria uma instância de Veiculo a partir de um registro apontado por um ResultSet.
	 * Note que os bytes da foto nunca são recuperados através deste método.
	 * @param rs ResultSet já apontando para o registro.
	 * @return
	 */
	private Veiculo montarVeiculo(ResultSet rs){
		try{
			Veiculo v = new Veiculo();
			v.setId( rs.getInt("ID") );
			v.setAnoFabricacao( rs.getInt("ANO") );
			v.setChassi( rs.getString("CHASSI") );
			v.setCilindradas( (Integer) rs.getObject("CILINDRADAS") );
			String mimeType = rs.getString("MIME_TYPE_FOTO");
			if(mimeType != null){
				Foto foto = new Foto(null, mimeType);
				v.setFoto(foto);
			}
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
