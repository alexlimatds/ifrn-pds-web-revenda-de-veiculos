package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import models.Modelo;
import models.Veiculo;
import play.db.DB;

public class DAOVeiculo implements DAO<Veiculo> {
	
	@Override
	public Integer inserir(Veiculo v) {
		try{
			Connection con = DB.getConnection();
			PreparedStatement prep = con.prepareStatement("insert into veiculos (anofabricacao, placa, cilindradas, idmodelo, chassi) " +
					"values (?, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
			prep.setInt(1, v.anoFabricacao);
			prep.setString(2, v.placa);
			prep.setInt(3, v.cilindradas);
			prep.setInt(4, v.modelo.id);
			prep.setString(5, v.chassi);
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
			Connection con = DB.getConnection();
			PreparedStatement prep = con.prepareStatement("update veiculos set anofabricacao=?, placa=?, cilindradas=?, idmodelo=?, chassi=? where id=?");
			prep.setInt(1, v.anoFabricacao);
			prep.setString(2, v.placa);
			prep.setInt(3, v.cilindradas);
			prep.setInt(4, v.modelo.id);
			prep.setString(5, v.chassi);
			prep.setInt(6, v.id);
			prep.executeUpdate();
		}catch(SQLException ex){
			ex.printStackTrace();
			throw new RuntimeException(ex);
		}
	}

	@Override
	public void excluir(Integer id) {
		try{
			Connection con = DB.getConnection();
			PreparedStatement prep = con.prepareStatement("delete from veiculos where id=?");
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
			Connection con = DB.getConnection();
			PreparedStatement prep = con.prepareStatement("select v.id as id, anofabricacao, chassi, placa, foto, cilindradas, " +
					"m.descricao as desc, m.id as idmodelo  " +
					"from veiculos v inner join modelos m on v.idmodelo = m.id ");
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
			Connection con = DB.getConnection();
			PreparedStatement prep = con.prepareStatement("select v.id as id, anofabricacao, chassi, placa, foto, cilindradas, " +
					"m.descricao as desc, m.id as idmodelo " +
					"from veiculos v inner join modelos m on v.idmodelo = m.id where v.id=?");
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
	
	/**
	 * Retorna um veículo específico a partir de uma determinada placa. 
	 * @param placa
	 * @return 	uma instância de veículo ou null caso não seja encontrado veículo com a placa 
	 * 			especificada. A instância retornada tem o atributo modelo não preenchido, ou seja, 
	 * 			com null.
	 */
	public Veiculo getPorPlaca(String placa){
		try{
			Connection con = DB.getConnection();
			PreparedStatement prep = con.prepareStatement("select v.id as id, anofabricacao, chassi, placa, foto, cilindradas, " +
					"m.descricao as desc, m.id as idmodelo " +
					"from veiculos v inner join modelos m on v.idmodelo = m.id where v.placa=?");
			prep.setString(1, placa);
			ResultSet rs = prep.executeQuery();
			if(rs.next()){
				return montarVeiculo(rs);
			}
			else{ //não há veículo com a placa informada
				return null;
			}
		}catch(SQLException ex){
			ex.printStackTrace();
			throw new RuntimeException(ex);
		}
	}
	
	public Date[] getDatasUltimasTransacoes(Integer idVeiculo){
		try{
			Connection con = DB.getConnection();
			PreparedStatement prep = con.prepareStatement("select placa as placa, max(c.data) as dcompra, max(ven.data) as dvenda " +
					"from veiculos vei left join compras c on vei.id = c.idveiculo left join vendas ven on vei.id = ven.idveiculo " +
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
	
	/**
	 * Cria uma instância de Veiculo a partir de um registro apontado por um ResultSet.
	 * @param rs ResultSet já apontando para o registro.
	 * @return
	 */
	private Veiculo montarVeiculo(ResultSet rs){
		try{
			Veiculo v = new Veiculo();
			v.id = rs.getInt("id");
			v.anoFabricacao = rs.getInt("anofabricacao");
			v.chassi = rs.getString("chassi");
			v.cilindradas = rs.getInt("cilindradas");
			v.foto = rs.getBytes("foto");
			v.placa = rs.getString("placa");
			
			Modelo modelo = new Modelo();
			modelo.id = rs.getInt("idmodelo");
			modelo.descricao = rs.getString("desc");
			v.modelo = modelo;
			
			return v;
		}catch(SQLException ex){
			ex.printStackTrace();
			throw new RuntimeException(ex);
		}
	}
}
