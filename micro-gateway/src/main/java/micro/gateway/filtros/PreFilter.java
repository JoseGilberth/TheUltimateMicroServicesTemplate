package micro.gateway.filtros;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.netflix.zuul.util.ZuulRuntimeException;
import org.springframework.http.HttpStatus;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.stereotype.Component;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;

import dao.auth.usuarios.publicos.UsuarioPublicoDao;
import micro.gateway._config.throttling.CustomThrottling;
import model.auth.usuarios.publicos.UsuarioPublico;
import utils.StaticVariables;
import utils.Token;

@Component
public class PreFilter extends ZuulFilter {

	private static Logger logger = LoggerFactory.getLogger(PreFilter.class);

	@Autowired
	CustomThrottling customThrottling;

	@Autowired
	private TokenStore tokenStore;

	@Autowired
	private UsuarioPublicoDao usuarioPublicoDao;

	@Override
	public String filterType() {
		return "pre";
	}

	@Override
	public int filterOrder() {
		return 1;
	}

	@Override
	public boolean shouldFilter() {
		return true;
	}

	@Override
	public Object run() {
		RequestContext context = RequestContext.getCurrentContext();
		HttpServletRequest request = context.getRequest();

		logger.info("========================= PreFilter =========================");
		
		String tokenJWT = request.getHeader( StaticVariables.AUTHORIZATION );
		logger.info("TOKEN: " + tokenJWT );
		UsuarioPublico usuarioPublico = null;
		if (tokenJWT != null) {
			if (tokenJWT.contains(StaticVariables.BEARER)) {
				tokenJWT = tokenJWT.replace(StaticVariables.BEARER, StaticVariables.EMPTY_STRING).trim();
				List<String> usuarioTipo = Token.getUsuarioYTipo(tokenJWT);
				if (usuarioTipo.get(1).equals(StaticVariables.PUBLICO)) {
					OAuth2AccessToken token = tokenStore.readAccessToken(tokenJWT);
					OAuth2Authentication auth = tokenStore.readAuthentication(token);
					String principal = auth.getPrincipal().toString();
					usuarioPublico = customThrottling.checkIfUserExist(principal);
					if (usuarioPublico == null) {
						usuarioPublico = usuarioPublicoDao.findByUsernameOrEmail(principal, principal);
					}
				}
			}
		}

		boolean hasTooManyRequest = customThrottling.apply(usuarioPublico);

		if (hasTooManyRequest) {
			context.setResponseStatusCode(HttpStatus.TOO_MANY_REQUESTS.value());
			throw new ZuulRuntimeException(new ZuulException("GATEWAY NO AUTH", HttpStatus.TOO_MANY_REQUESTS.value(),"GATEWAY CustomRateLimiter NO ATUTORIZADO"));
		}

		logger.info("========================= / PreFilter =========================");
		return null;
	}

}
