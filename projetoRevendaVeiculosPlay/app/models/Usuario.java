package models;

/**
 * Representa um usuário do sistema.
 *
 */
public class Usuario extends Model{
	
	public String nome, cpf, telefone, login, senha;
	public boolean ativo, gerente;
	
	public Usuario(){
		super();
	}
	
}
