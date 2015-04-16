package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import dominio.Usuario;

@Repository
public class DAOUsuario {
	
	@Autowired
	private DataSource dataSource;
	
	public Usuario getPorLogin(String login){
		try{
			Connection con = dataSource.getConnection();
			PreparedStatement prep = con.prepareStatement("select * from USUARIOS where LOGIN=?");
			prep.setString(1, login);
			ResultSet rs = prep.executeQuery();
			if(rs.next()){
				return montarUsuario(rs);
			}
			else{ //não há usuário com o login informado
				return null;
			}
		}catch(SQLException ex){
			ex.printStackTrace();
			throw new RuntimeException(ex);
		}
	}
	
	private Usuario montarUsuario(ResultSet rs){
		try{
			Usuario u = new Usuario();
			u.setId( rs.getInt("id") );
			u.setNome( rs.getString("nome") );
			u.setCpf( rs.getString("cpf") );
			u.setTelefone( rs.getString("telefone") );
			u.setLogin( rs.getString("login") );
			u.setSenha( rs.getString("senha") );
			u.setNome( rs.getString("nome") );
			u.setAtivo( rs.getBoolean("ativo") );
			u.setGerente( rs.getBoolean("gerente") );
			
			return u;
		}catch(SQLException ex){
			ex.printStackTrace();
			throw new RuntimeException(ex);
		}
	}
}
