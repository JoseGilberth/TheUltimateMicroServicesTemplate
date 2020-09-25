package micro.gateway.filtros;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;

import dao.auth.LogRequestDao;
import dto.main.MessageWebsocket;
import micro.gateway.services.externos.websocket.interfaces.IWebSocketService;
import micro.gateway.services.interfaces.ICustomLogService;
import model.auth.log.LogRequest;
import utils.StaticVariables;

@Component
public class PostFilter extends ZuulFilter {

	Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	ICustomLogService iLogger;

	@Autowired
	LogRequestDao logRequestDao;

	@Autowired
	IWebSocketService iWebSocket;

	@Value("${websocket.topics.log}")
	private String topicLog;
	
	@Override
	public String filterType() {
		return "post";
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
		RequestContext ctx = RequestContext.getCurrentContext();
		HttpServletRequest request = ctx.getRequest();
		HttpServletResponse response = ctx.getResponse();
		
		logger.info("========================= PostFilter =========================");
		logger.info("========================= Log request");
		String token = null;
		try {

			token = request.getHeader(HttpHeaders.AUTHORIZATION);
			if (token != null) {
				token = token.replace(StaticVariables.BEARER, StaticVariables.EMPTY_STRING).trim();
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		
		try {
			LogRequest log = iLogger.fillLog(request, response, token);
			logRequestDao.saveAndFlush(log);
			logger.info("========================= / Log request");
			
			iWebSocket.sendMessage(new MessageWebsocket("Sistema", new Gson().toJson(log), topicLog , "LogRequest"));
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		logger.info("========================= / PostFilter =========================");
		return true;
	}

}
