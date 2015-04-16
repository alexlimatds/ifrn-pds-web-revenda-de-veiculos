package infra;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import dao.DAOUsuario;
import dominio.Usuario;

@Component
public class AppAuthenticationProvider implements AuthenticationProvider {
	
	@Autowired
	private DAOUsuario daoUsuario;
	
	@Override
	public boolean supports(Class<?> authenticationClass) {
		return (UsernamePasswordAuthenticationToken.
				class.isAssignableFrom(authenticationClass));
	}
	
	@Override
	public Authentication authenticate(Authentication auth)
			throws AuthenticationException {
		String login = auth.getName();
		String senha = auth.getCredentials().toString();
		Usuario usuario = daoUsuario.getPorLogin(login);
		
		if(usuario == null || !usuario.isAtivo() || !usuario.getSenha().equals(senha))
			return null;
		
		List<GrantedAuthority> autorizacoes = new ArrayList<>();
		if(usuario.isGerente()){
			autorizacoes.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
		}
		else{
			autorizacoes.add(new SimpleGrantedAuthority("ROLE_VENDEDOR"));
		}
		
		UsernamePasswordAuthenticationToken principal = 
				new UsernamePasswordAuthenticationToken(login, senha, autorizacoes);
		principal.setDetails(usuario);
		return principal;
	}
}
