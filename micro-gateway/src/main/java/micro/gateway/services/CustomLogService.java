package micro.gateway.services;

import java.util.Enumeration;
import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;

import micro.gateway.services.interfaces.ICustomLogService;
import model.auth.log.LogRequest;
import utils.Token;

@Component
public class CustomLogService implements ICustomLogService {

	Logger logger = LoggerFactory.getLogger(this.getClass());

	public LogRequest fillLog(HttpServletRequest request, HttpServletResponse response, String token) {

		final List<String> data = Token.getUsuarioYTipo(token);
		final LogRequest log = new LogRequest();
		final String usuario = data.get(0);
		final String tipoUsuario = data.get(1);
		log.setTipoUsuario(tipoUsuario);
		log.setUsuario(usuario);
		log.setCharacterEncoding(request.getCharacterEncoding());
		log.setContentLengthLong(request.getContentLengthLong());
		log.setContentType(request.getContentType());
		log.setContextPath(request.getContextPath());
		log.setMethod(request.getMethod());
		log.setProtocol(request.getProtocol());
		log.setRemoteAddr(request.getRemoteAddr());
		log.setRequestURI(request.getRequestURI());
		log.setServletPath(request.getServletPath());
		log.setAccion(request.getMethod());
		log.setStatusCode(response.getStatus());

		String headers = "";
		final Enumeration<?> headerNames = request.getHeaderNames();
		while (headerNames.hasMoreElements()) {
			String key = (String) headerNames.nextElement();
			String value = request.getHeader(key);
			if (key.equals(HttpHeaders.AUTHORIZATION.toString())
					|| key.equals(HttpHeaders.AUTHORIZATION.toLowerCase().toString())) {
				value = "<DATO OCULTO>";
			}
			headers += String.format("%s: %s||", key, value);
		}

		String cookies = "";
		final Cookie[] s = request.getCookies();
		if (s != null) {
			for (Cookie cookie : s) {
				cookies += cookie + "||" + "";
			}
		}

		String parameters = "";
		final Enumeration<?> parametersNames = request.getParameterNames();
		while (parametersNames.hasMoreElements()) {
			String key = (String) parametersNames.nextElement();
			String value = request.getHeader(key);
			parameters += String.format("%s: %s||", key, value);
		}

		log.setCookies(cookies);
		log.setHeaderNames(headers);
		log.setParameterNames(parameters);
		log.setDispatcherType(request.getDispatcherType() == null ? "" : request.getDispatcherType().name());
		log.setHttpServletMapping(
				request.getHttpServletMapping() == null ? "" : request.getHttpServletMapping().getServletName());
		log.setLocalAddr(request.getLocalAddr() == null ? "" : request.getLocalAddr());
		log.setLocale(request.getLocale() == null ? "" : request.getLocale().getCountry());
		log.setLocalName(request.getLocalName() == null ? "" : request.getLocalName());
		log.setLocalPort(String.valueOf(request.getLocalPort()));
		log.setPathInfo(request.getPathInfo() == null ? "" : request.getPathInfo());
		log.setQueryString(request.getQueryString() == null ? "" : request.getQueryString());
		log.setRemoteUser(request.getRemoteUser() == null ? "" : request.getRemoteUser());
		log.setRequestedSessionId(request.getRequestedSessionId() == null ? "" : request.getRequestedSessionId());
		log.setScheme(request.getScheme() == null ? "" : request.getScheme());
		return log;
	}
}
