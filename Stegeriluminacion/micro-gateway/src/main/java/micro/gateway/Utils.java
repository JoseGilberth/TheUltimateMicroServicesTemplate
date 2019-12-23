package micro.gateway;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.google.common.io.CharStreams;
import com.netflix.zuul.context.RequestContext;

import modelo.auth.log.Log;

public class Utils {

	public static Log fillLog(RequestContext ctx, String token, boolean responseData) {

		final HttpServletRequest request = ctx.getRequest();
		final HttpServletResponse response = ctx.getResponse();

		final List<String> data = decodeToken(token);
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

		log.setStatusCode( response.getStatus() );
		
		if( responseData ) {
			// RESPONSE DATA 
			try (final InputStream responseDataStream = ctx.getResponseDataStream()) {
				final String datas  = CharStreams.toString(new InputStreamReader(responseDataStream, "UTF-8"));
				ctx.setResponseBody(datas);
				log.setResponse(datas);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		// REQUEST DATA
		String payloadRequest = "";
		try {
			payloadRequest = getBody(request);
		} catch (Exception e) { 
			e.printStackTrace();
		}
		log.setRequest(payloadRequest);

		
		String headers = "";
		final Enumeration<?> headerNames = request.getHeaderNames();
		while (headerNames.hasMoreElements()) {
			String key = (String) headerNames.nextElement();
			String value = request.getHeader(key);
			if( key.equals("authorization")) {
				value = "<DATO OCULTO>";
			}
			headers += String.format("%s: %s||", key, value);
		}

		String cookies = "";
		final Cookie[] s = request.getCookies();
		if (s != null) {
			for (Cookie cookie : s) {
				cookies += cookie + "||"
						+ "";
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
		log.setHttpServletMapping( request.getHttpServletMapping() == null ? "" : request.getHttpServletMapping().getServletName());
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

	
 
	
	
	
	private static List<String> decodeToken(String jwtToken) {
		final List<String> data = new ArrayList<String>();
		final java.util.Base64.Decoder decoder = java.util.Base64.getUrlDecoder();
		final String[] parts = jwtToken.split("\\.");
		final String payloadJson = new String(decoder.decode(parts[1]));
		final JSONParser parser = new JSONParser();
		JSONObject json = null;
		try {
			json = (JSONObject) parser.parse(payloadJson);
			data.add(json.get("user_name").toString());
			data.add(json.get("TipoUsuario").toString());
			return data;
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}

	
	public static String getBody(HttpServletRequest request) throws IOException {

	    String body = null;
	    final StringBuilder stringBuilder = new StringBuilder();
	    BufferedReader bufferedReader = null;

	    try {
	    	final InputStream inputStream = request.getInputStream();
	        if (inputStream != null) {
	            bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
	            final char[] charBuffer = new char[128];
	            int bytesRead = -1;
	            while ((bytesRead = bufferedReader.read(charBuffer)) > 0) {
	                stringBuilder.append(charBuffer, 0, bytesRead);
	            }
	        } else {
	            stringBuilder.append("");
	        }
	    } catch (IOException ex) {
	        throw ex;
	    } finally {
	        if (bufferedReader != null) {
	            try {
	                bufferedReader.close();
	            } catch (IOException ex) {
	                throw ex;
	            }
	        }
	    } 
	    body = stringBuilder.toString();
	    return body;
	}
	
	

}
