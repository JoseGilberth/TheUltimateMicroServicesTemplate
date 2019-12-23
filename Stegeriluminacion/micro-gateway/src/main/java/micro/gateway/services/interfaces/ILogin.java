package micro.gateway.services.interfaces;

import com.netflix.zuul.context.RequestContext;

public interface ILogin {

	boolean logSession(RequestContext ctx);
	boolean logCerrarSesion(RequestContext ctx);
	
}
