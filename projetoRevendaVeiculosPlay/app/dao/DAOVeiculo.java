package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import models.Veiculo;
import play.db.DB;

public class DAOVeiculo implements DAO<Veiculo> {

	@Override
	public Integer inserir(Veiculo f) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void atualizar(Veiculo f) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void excluir(Integer id) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<Veiculo> todos() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Veiculo getPorId(Integer id) {
		// TODO Auto-generated method stub
		return null;
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
			PreparedStatement prep = con.prepareStatement("select * from veiculos where placa=?");
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
			
			return v;
		}catch(SQLException ex){
			ex.printStackTrace();
			throw new RuntimeException(ex);
		}
	}
}
