package micro.auth._config.security.custom;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;

import dao.auth.usuarios.administradores.UsuarioAdministradorDao;
import dao.auth.usuarios.publicos.UsuarioPublicoDao;
import model.auth.usuarios.administradores.UsuarioAdministrador;
import model.auth.usuarios.publicos.UsuarioPublico;
import utils.StaticVariables;
import utils.Times;
 

public class CustomTokenEnhancer implements TokenEnhancer {

	Logger logger = LoggerFactory.getLogger(CustomTokenEnhancer.class);

	@Autowired
	public UsuarioPublicoDao usuarioPublicoDao;

	@Autowired
	public UsuarioAdministradorDao usuarioAdministradorDao;

	@Override
	public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {

		String usuario = authentication.getPrincipal().toString();
		UsuarioPublico usuarioPublico = usuarioPublicoDao.findByUsernameOrEmail(usuario, usuario);
		UsuarioAdministrador usuarioAdministrador = usuarioAdministradorDao.findByUsername(usuario);

		DefaultOAuth2AccessToken defaultOAuth2AccessToken = ((DefaultOAuth2AccessToken) accessToken);
		final Map<String, Object> additionalInfo = new HashMap<>();

		if (usuarioPublico != null) {
			int tokenTime = usuarioPublico.getTokenExpiration() == null ? defaultOAuth2AccessToken.getExpiresIn()
					: usuarioPublico.getTokenExpiration();

			Calendar now = Calendar.getInstance();
			now.add(Times.converTimeUnitToCalendar(usuarioPublico.getTimeUnitToken()), tokenTime);

			defaultOAuth2AccessToken.setExpiration(now.getTime());
			additionalInfo.put(StaticVariables.LIMIT, usuarioPublico.getLimitRequest());
			additionalInfo.put(StaticVariables.TIPO_USUARIO, StaticVariables.PUBLICO);
		}
		if (usuarioAdministrador != null) {
			additionalInfo.put(StaticVariables.TIPO_USUARIO, StaticVariables.ADMINISTRADOR);
		}
		defaultOAuth2AccessToken.setAdditionalInformation(additionalInfo);

		return accessToken;
	}

}