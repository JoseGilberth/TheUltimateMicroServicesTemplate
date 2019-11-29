package micro.gateway.filtros;

import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.netflix.zuul.util.ZuulRuntimeException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;

import micro.gateway._config.throttling.CustomThrottling;

@Component
public class PreFilter extends ZuulFilter {

	private static Logger log = LoggerFactory.getLogger(PreFilter.class);

	@Autowired
	CustomThrottling customThrottling;

	@Override
	public String filterType() {
		return "pre";
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
		RequestContext context = RequestContext.getCurrentContext();
		HttpServletRequest request = context.getRequest();
		HttpServletResponse response = context.getResponse();
		Enumeration<?> headerNames = request.getHeaderNames();
		log.info(String.format("%s   %s request to %s", "PRE:  ", request.getMethod(), request.getRequestURL().toString()));
		while (headerNames.hasMoreElements()) {
			String key = (String) headerNames.nextElement();
			String value = request.getHeader(key);
			log.info(String.format("HEADERS to %s : %s %s", "PRE:  ", key, value));
		}
		boolean hasTooManyRequest = customThrottling.apply(context, request, response);
		log.info("hasTooManyRequest: " + hasTooManyRequest);
		if( hasTooManyRequest ) {
			context.setResponseStatusCode(HttpStatus.TOO_MANY_REQUESTS.value());
			throw new ZuulRuntimeException(new ZuulException("GATEWAY NO AUTH", HttpStatus.TOO_MANY_REQUESTS.value(), "GATEWAY CustomRateLimiter NO ATUTORIZADO"));
		}
		return null;
	}

}
