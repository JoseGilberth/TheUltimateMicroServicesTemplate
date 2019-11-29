package micro.gateway._config.throttling;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;
import com.netflix.zuul.context.RequestContext;

import dao.auth.usuarios.publicos.UsuarioPublicoDao;
import micro.gateway.SingletonLimiters;
import micro.gateway.filtros.CustomRateLimiter;
import modelo.auth.usuarios.publicos.UsuarioPublico;

@Component
public class CustomThrottling {

	private static Logger log = LoggerFactory.getLogger(CustomThrottling.class);

	final Gson gson = new Gson();

	@Autowired
	private TokenStore tokenStore;

	@Autowired
	private UsuarioPublicoDao usuarioPublicoDao;

	public boolean apply(RequestContext context, HttpServletRequest request, HttpServletResponse response) {
		String tokenJWT = request.getHeader("authorization");
		UsuarioPublico usuarioPublico = null;
		if (tokenJWT != null) {
			if (tokenJWT.contains("Bearer")) {
				tokenJWT = tokenJWT.replace("Bearer ", "");
				OAuth2AccessToken token = tokenStore.readAccessToken(tokenJWT);
				OAuth2Authentication auth = tokenStore.readAuthentication(token);
				String principal = auth.getPrincipal().toString();
				usuarioPublico = checkIfUserExist(principal);
				if (usuarioPublico == null) {
					usuarioPublico = usuarioPublicoDao.buscarPorUsuario(principal);
				};
			} else if (tokenJWT.contains("Basic")) {
				log.info("PreFilter - ZuulFilter Basic: " + tokenJWT);
			}
		}
		if (usuarioPublico != null) {
			CustomRateLimiter rateLimiter = getRateLimiter(gson.toJson(usuarioPublico), usuarioPublico);
			log.info("limiter getTimePeriod: " + rateLimiter.getTimePeriod());
			log.info("limiter getMaxPermits: " + rateLimiter.getMaxPermits());
			log.info("limiter availablePermits: " + rateLimiter.getSemaphore().availablePermits());
			boolean allowRequest = rateLimiter.tryAcquire();
			if (!allowRequest) {
				return true;
			}
		}
		return false;
	}

	private CustomRateLimiter getRateLimiter(String jsonObject, UsuarioPublico usuarioPublico) {
		Map<String, CustomRateLimiter> limiters = getSingletonLimiters();
		if (limiters.containsKey(jsonObject)) {
			return limiters.get(jsonObject);
		} else {
			synchronized (jsonObject.intern()) {
				if (limiters.containsKey(jsonObject)) {
					return limiters.get(jsonObject);
				}
				CustomRateLimiter rateLimiter = CustomRateLimiter.create(usuarioPublico.getLimitRequest(),usuarioPublico.getTimeUnit());// # request, #time
				limiters.put(jsonObject, rateLimiter);
				return rateLimiter;
			}
		}
	}

	private UsuarioPublico checkIfUserExist(String usuario) {
		Map<String, CustomRateLimiter> limiters = getSingletonLimiters();
		for (String key : limiters.keySet()) {
			UsuarioPublico usuarioPublico = gson.fromJson(key, UsuarioPublico.class);
			if (usuarioPublico != null) {
				if (usuarioPublico.getUsername().equals(usuario)) {
					return usuarioPublico;
				}
			}
		}
		return null;
	}

	private Map<String, CustomRateLimiter> getSingletonLimiters() {
		SingletonLimiters singletonLimiters = SingletonLimiters.Instance();
		Map<String, CustomRateLimiter> limiters = singletonLimiters.getLimiters();
		return limiters;
	}

}
