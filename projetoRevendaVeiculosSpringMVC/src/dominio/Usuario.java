package dominio;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotEmpty;

/**
 * Representa um usuário do sistema.
 */
public class Usuario extends Entidade{
	
	@NotNull
	@NotEmpty
	private String nome;
	@Pattern(regexp="\\d{11}")
	private String cpf;
	private String telefone;
	@NotNull
	@NotEmpty
	private String login;
	@NotNull
	@NotEmpty
	private String senha;
	@NotNull
	private Boolean ativo;
	@NotNull
	private Boolean gerente;
	
	public Usuario(){
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public Boolean isAtivo() {
		return ativo;
	}

	public void setAtivo(Boolean ativo) {
		this.ativo = ativo;
	}

	public Boolean isGerente() {
		return gerente;
	}

	public void setGerente(Boolean gerente) {
		this.gerente = gerente;
	}
}
