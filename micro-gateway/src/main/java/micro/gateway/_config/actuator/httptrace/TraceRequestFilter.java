package micro.gateway._config.actuator.httptrace;

import java.util.Arrays;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import org.springframework.boot.actuate.trace.http.HttpExchangeTracer;
import org.springframework.boot.actuate.trace.http.HttpTraceRepository;
import org.springframework.boot.actuate.web.trace.servlet.HttpTraceFilter;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;

@Component
public class TraceRequestFilter extends HttpTraceFilter {

	private final String[] excludedEndpoints = new String[] { "/css/**", "/js/**", "/trace"
			, "/actuator" , "/actuator/**" , "/usuario" };
	
	public TraceRequestFilter(HttpTraceRepository repository, HttpExchangeTracer tracer) {
		super(repository, tracer);
	}

	@Override
	protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
		return Arrays.stream(excludedEndpoints).anyMatch(e -> new AntPathMatcher().match(e, request.getServletPath()));

	}
}
