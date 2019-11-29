package micro.auth.usuarios;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.session.SessionAuthenticationException;

import dao.auth.usuarios.administradores.UsuarioAdministradorDao;
import dao.auth.usuarios.publicos.UsuarioPublicoDao;
import modelo.auth.usuarios.administradores.UsuarioAdministrador;
import modelo.auth.usuarios.publicos.UsuarioPublico;
 

public class CustomDaoAuthenticationProvider implements AuthenticationProvider  {

	Logger logger = LoggerFactory.getLogger(CustomDaoAuthenticationProvider.class);

	@Autowired
	private UsuarioAdministradorDao usuarioAdministradorDao;

	@Autowired
	public UsuarioPublicoDao usuarioPublicoDao;
	
	@Autowired
	private BCryptPasswordEncoder bcrypt;
	
	
	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
	
		String username = authentication.getPrincipal() + "";
		String password = authentication.getCredentials() + "";
		List<GrantedAuthority> authorities = new ArrayList<>();
		
		UsuarioAdministrador usuarioAdministrador = usuarioAdministradorDao.buscarPorUsuario(username);
		UsuarioPublico usuarioPublico = usuarioPublicoDao.buscarPorUsuario(username);
		
		if( usuarioPublico == null && usuarioAdministrador == null ) {
			throw new UsernameNotFoundException(String.format("Usuario no existe", username));
		} 
		
		if( usuarioPublico != null && usuarioAdministrador != null ) {
			throw new SessionAuthenticationException(String.format("Whoops tenemos problemas con tu usario - Usuario repetido como administrador", username));
		} 

		// VALIDACION DE USUARIOS ADMINISTRADORES
		if( usuarioAdministrador != null ) {
			if ( !usuarioAdministrador.isEnabled() ) {
				throw new DisabledException("El usuario se encuentra deshabilitado");
			}
			if ( !bcrypt.matches(password, usuarioAdministrador.getPassword())) {
				throw new BadCredentialsException("Password incorrecto");
			}
			usuarioAdministrador.getPermisos().forEach(permiso -> {
				authorities.add(new SimpleGrantedAuthority(permiso.getEtiqueta()));
			});
		}
		
		//VALIDACION DE USUARIOS PUBLICOS
		if( usuarioPublico != null ) {
			if ( !usuarioPublico.isEnabled() ) {
				throw new DisabledException("El usuario se encuentra deshabilitado");
			}
			if ( !bcrypt.matches(password, usuarioPublico.getPassword())) { 
				throw new BadCredentialsException("Password incorrecto");
			}
			usuarioPublico.getPermisos().forEach(permiso -> {
				authorities.add(new SimpleGrantedAuthority(permiso.getEtiqueta()));
			});
		}
		
		Authentication auth = new UsernamePasswordAuthenticationToken(username, password, authorities);
		return auth;
	}

	
	
	@Override
	public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
	}
	

	
}
