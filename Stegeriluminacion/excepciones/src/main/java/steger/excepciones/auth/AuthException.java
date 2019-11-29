package steger.excepciones.auth;

import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import com.fasterxml.jackson.databind.ObjectMapper;

import steger.excepciones.NoAutorizado;

public class AuthException implements AuthenticationEntryPoint {
	
	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException arg2) throws IOException, ServletException { 

		final Map<String, Object> mapException = NoAutorizado.noAutorizadoMap();
		response.setContentType("application/json");
		response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
		final ObjectMapper mapper = new ObjectMapper();
		mapper.writeValue( response.getOutputStream() , mapException);
	}
}