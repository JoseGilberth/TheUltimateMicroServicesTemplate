package steger.excepciones.auth;

import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import com.fasterxml.jackson.databind.ObjectMapper;

import steger.excepciones.NoAutorizado;

public class AccessDeniedHandlerException implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest req,
                       HttpServletResponse res,
                       AccessDeniedException accessDeniedException) throws IOException, ServletException {

		final Map<String, Object> mapException = NoAutorizado.accesoDenegadoMap();
		res.setContentType("application/json");
		res.setStatus(HttpServletResponse.SC_FORBIDDEN);
		final ObjectMapper mapper = new ObjectMapper();
		mapper.writeValue( res.getOutputStream() , mapException);
    }
}