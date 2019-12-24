package micro.gateway.filtros;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;

import micro.gateway.services.RequestLog;
import micro.gateway.services.interfaces.ILogin;

@Component
public class PostFilter extends ZuulFilter {

	private static Logger log = LoggerFactory.getLogger(PostFilter.class);

	@Autowired
	ILogin ilogin;

	@Autowired
	RequestLog requestLog;

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
		log.info(String.format("%s   %s request to %s", "POST:  ", request.getMethod(),
				request.getRequestURL().toString()));

		if ((response.getStatus() >= 200 && response.getStatus() < 300)) {
			if (request.getRequestURI().contains("/oauth/token")) {
				ilogin.logSession(ctx);
			} else if (request.getRequestURI().contains("/sesiones/cerrar")) {
				ilogin.logCerrarSesion(ctx);
			} else if (request.getMethod().equals("DELETE")) {
				requestLog.deleteLog(ctx);
			} else if (request.getMethod().equals("PUT")) {
				requestLog.updateLog(ctx);
			} else if (request.getMethod().equals("POST") && !request.getRequestURI().contains("/oauth/token")) {
				if (request.getQueryString() != null) {
					if (!request.getQueryString().contains("page") || !request.getQueryString().contains("size")) { 
						requestLog.postLog(ctx);
					}
				} else {
					requestLog.postLog(ctx);
				}
			}
		}
		return null;
	}

}
