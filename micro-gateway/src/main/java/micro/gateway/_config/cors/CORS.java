package micro.gateway._config.cors;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class CORS implements Filter {

	@Value("${cors.Access-Control-Allow-Origin}")
	private String allowOrigin;

	@Value("${cors.Access-Control-Allow-Methods}")
	private String allowMethods;

	@Value("${cors.Access-Control-Max-Age}")
	private String maxAge;

	@Value("${cors.Access-Control-Allow-Headers}")
	private String allowHeaders;

	@Value("${cors.Access-Control-Allow-Credentials}")
	private String allowCredentials;

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
			throws IOException, ServletException {

		HttpServletResponse response = (HttpServletResponse) res;
		HttpServletRequest request = (HttpServletRequest) req;

		response.addHeader("Access-Control-Allow-Origin", allowOrigin);
		response.addHeader("Access-Control-Allow-Methods", allowMethods);
		response.addHeader("Access-Control-Max-Age", maxAge);
		response.addHeader("Access-Control-Allow-Headers", allowHeaders);
		response.addHeader("Access-Control-Allow-Credentials", allowCredentials);

		if ("OPTIONS".equalsIgnoreCase(request.getMethod())) {
			response.setStatus(HttpServletResponse.SC_OK);
		} else {
			chain.doFilter(req, res);
		}
	}

	@Override
	public void destroy() {
	}

}
