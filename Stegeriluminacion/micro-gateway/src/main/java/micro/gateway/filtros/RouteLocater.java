package micro.gateway.filtros;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.netflix.zuul.filters.SimpleRouteLocator;
import org.springframework.cloud.netflix.zuul.filters.ZuulProperties;

public class RouteLocater extends SimpleRouteLocator {
	private Logger logger = LoggerFactory.getLogger(RouteLocater.class);

	public RouteLocater(String servletPath, ZuulProperties properties) {
		super(servletPath, properties);
	}

	@Override
	protected ZuulProperties.ZuulRoute getZuulRoute(String adjustedPath) {
		ZuulProperties.ZuulRoute zuulRoute = super.getZuulRoute(adjustedPath);
		//logger.info(">>>>>>>" + zuulRoute.toString());
		return zuulRoute;
	}
}