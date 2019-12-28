package micro.gateway;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.time.LocalDateTime;
import java.util.Enumeration;
import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.common.io.CharStreams;
import com.netflix.zuul.context.RequestContext;

import dto.main.MessageWebsocket;
import micro.gateway.services.WebSocketService;
import modelo.auth.log.Log;
import utils.Content;
import utils.Token;

@Component
public class Utils {

	static Logger logger = LoggerFactory.getLogger(Utils.class);

	@Autowired
	WebSocketService webSocketService;

	public Log fillLog(RequestContext ctx, String token, boolean responseData) {

		final HttpServletRequest request = ctx.getRequest();
		final HttpServletResponse response = ctx.getResponse();
		final List<String> data = Token.getUsuarioYTipo(token);
		final Log log = new Log();
		final String usuario = data.get(0);
		final String tipoUsuario = data.get(1);
		log.setFechaAlta(LocalDateTime.now());
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

		log.setStatusCode(response.getStatus());

		if (responseData) {
			// RESPONSE DATA
			try (final InputStream responseDataStream = ctx.getResponseDataStream()) {
				final String datas = CharStreams.toString(new InputStreamReader(responseDataStream, "UTF-8"));
				ctx.setResponseBody(datas);
				log.setResponse(datas);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		// REQUEST DATA
		String payloadRequest = "";
		try {
			payloadRequest = Content.getBody(request);
		} catch (Exception e) {
			e.printStackTrace();
		}
		log.setRequest(payloadRequest);

		String headers = "";
		final Enumeration<?> headerNames = request.getHeaderNames();
		while (headerNames.hasMoreElements()) {
			String key = (String) headerNames.nextElement();
			String value = request.getHeader(key);
			if (key.equals("authorization")) {
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

	public void notifyAdmin(RequestContext ctx, Log log) {
		HttpServletRequest request = ctx.getRequest();

		String token = request.getHeader("Authorization").replace("Bearer ", "");

		final List<String> data = Token.getUsuarioYTipo(token);
		String mensaje = null;

		try {
			mensaje = request.getHeader("accion");
		} catch (Exception e) {
			mensaje = null;
		}

		try {
			if (mensaje == null) {
				final JSONParser parser = new JSONParser();
				JSONObject json = null;
				json = (JSONObject) parser.parse(log.getResponse());
				mensaje = json.get("mensaje").toString();
			}
		} catch (Exception ex) {
			mensaje = "Error";
		}

		try {
			webSocketService.sendMessage(new MessageWebsocket(data.get(0),
					"El usuario " + data.get(0) + " -> '" + mensaje + "' ", request.getMethod()));
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

}
