package micro.auth.usuarios.publicos.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import dao.auth.usuarios.publicos.UsuarioPublicoDao;
import modelo.auth.usuarios.publicos.UsuarioPublico;
 

@Service("usuariosPublicos")
public class UsuarioPublicoDetailService implements UserDetailsService {

	Logger logger = LoggerFactory.getLogger(UsuarioPublicoDetailService.class);

	@Autowired
	private UsuarioPublicoDao usuarioPublicoDao;
	
	@Override
	public UserDetails loadUserByUsername(String username) {
		
		logger.info("loadUserByUsername - publico - " + username);
		UsuarioPublico user = usuarioPublicoDao.buscarPorUsuario(username);
		
		if (user == null) {
			throw new UsernameNotFoundException(String.format("Usuario no existe", username));
		}
		
		List<GrantedAuthority> authorities = new ArrayList<>();
		
		user.getPermisos().forEach(permiso -> {
			logger.info("loadUserByUsername publico - permiso: " + permiso.getEtiqueta());
			authorities.add(new SimpleGrantedAuthority(permiso.getEtiqueta()));
		});
		UserDetails userDetails = new User(user.getUsername(), user.getPassword(), authorities);
		return userDetails;
	}



}
