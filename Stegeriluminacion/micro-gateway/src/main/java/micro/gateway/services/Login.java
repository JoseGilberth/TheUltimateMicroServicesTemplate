package micro.gateway.services;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import javax.servlet.http.HttpServletRequest;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.io.CharStreams;
import com.netflix.zuul.context.RequestContext;

import dao.auth.LogDao;
import micro.gateway.Utils;
import micro.gateway.services.interfaces.ILogin;
import modelo.auth.log.Log;

@Service
public class Login implements ILogin {

	private static Logger log = LoggerFactory.getLogger(Login.class);

	@Autowired
	LogDao logDao;
	
	@Autowired
	Utils utils;

	@Override
	public boolean logSession(RequestContext ctx) { 
		try (final InputStream responseDataStream = ctx.getResponseDataStream()) {
			final String responseData = CharStreams.toString(new InputStreamReader(responseDataStream, "UTF-8"));
			ctx.setResponseBody(responseData);

			JSONParser parser = new JSONParser();
			JSONObject json = (JSONObject) parser.parse(responseData);
			String accessTokenValue = json.get("access_token").toString();
			Log log = utils.fillLog(ctx, accessTokenValue, false); 
			log.setAccion("Inicio sesión");
			log.setApartado("PostFilter");
			logDao.saveAndFlush(log);

		} catch (IOException e) {
			log.warn("Error reading body", e);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return false;
	}

	@Override
	public boolean logCerrarSesion(RequestContext ctx) {
		HttpServletRequest request = ctx.getRequest();
		Log log = utils.fillLog(ctx, request.getHeader("Authorization").replace("Bearer ", ""), true); 
		log.setAccion("Cerro sesión");
		log.setApartado("PostFilter");
		logDao.saveAndFlush(log);
		return false;
	}

}
