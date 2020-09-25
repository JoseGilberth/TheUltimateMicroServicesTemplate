package micro.gateway._config.throttling;

import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import model.auth.usuarios.publicos.UsuarioPublico;

@Component
public class CustomThrottling {

	private static Logger log = LoggerFactory.getLogger(CustomThrottling.class);

	final Gson gson = new Gson();
	
	public boolean apply( UsuarioPublico usuarioPublico) {
		if (usuarioPublico != null) {
			Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
			String jsonString = gson.toJson(usuarioPublico);
			CustomRateLimiter rateLimiter = getRateLimiter(jsonString, usuarioPublico);
			log.info("limiter getPeriod: " + rateLimiter.getPeriod() + "  -  " + usuarioPublico.getPeriodRequest());
			log.info("limiter getTimePeriod: " + rateLimiter.getTimePeriod() + "  -  " + usuarioPublico.getTimeUnitRequest());
			log.info("limiter getMaxPermits: " + rateLimiter.getMaxPermits() + "  -  " + usuarioPublico.getLimitRequest());
			log.info("limiter availablePermits: " + rateLimiter.getSemaphore().availablePermits());
			log.info("User: " + usuarioPublico.getUsername()  + " Correo: " + usuarioPublico.getCorreo());
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
				CustomRateLimiter rateLimiter = CustomRateLimiter.create(usuarioPublico.getLimitRequest(), usuarioPublico.getPeriodRequest(),usuarioPublico.getTimeUnitRequest());// # request, #time
				limiters.put(jsonObject, rateLimiter);
				return rateLimiter;
			}
		}
	}
	
	private Map<String, CustomRateLimiter> getSingletonLimiters() {
		SingletonLimiters singletonLimiters = SingletonLimiters.Instance();
		Map<String, CustomRateLimiter> limiters = singletonLimiters.getLimiters();
		return limiters;
	}


	public UsuarioPublico checkIfUserExist(String usuario) {
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

}
