package micro.gateway.filtros;

import javax.annotation.PreDestroy;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

@Component
public class RateLimitingInterceptor extends HandlerInterceptorAdapter {
 	 
    private static final Logger logger = LoggerFactory.getLogger(RateLimitingInterceptor.class);
     
    //@Value("${rate.limit.enabled}")
    //private boolean enabled;
     
    //@Value("${rate.limit.hourly.limit}")
    //private int hourlyLimit;
 
    //private Map<String, CustomRateLimiter> limiters = new ConcurrentHashMap<>();
     
    
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
       
    	/*
    	if (!enabled) {
            return true;
        }
        String clientId = request.getHeader("Client-Id");
        // let non-API requests pass
        if (clientId == null) {
            return true;
        }
        CustomRateLimiter rateLimiter = getRateLimiter(clientId);
        boolean allowRequest = rateLimiter.tryAcquire();
     
        if (!allowRequest) {
            response.setStatus(HttpStatus.TOO_MANY_REQUESTS.value());
        }
        response.addHeader("X-RateLimit-Limit", String.valueOf(hourlyLimit));
        
        */
    	logger.info("RateLimitingInterceptor - preHandle");
        return true;
    }
     
    /*
    private CustomRateLimiter getRateLimiter(String clientId) {
        if (limiters.containsKey(clientId)) {
            return limiters.get(clientId);
        } else {
            synchronized(clientId.intern()) {
                // double-checked locking to avoid multiple-reinitializations
                if (limiters.containsKey(clientId)) {
                    return limiters.get(clientId);
                }
                 
                TimeUnit timeUnit = TimeUnit.HOURS;
                CustomRateLimiter rateLimiter = CustomRateLimiter.create( 10 , timeUnit);//createRateLimiter(clientId);
                 
                limiters.put(clientId, rateLimiter);
                return rateLimiter;
            }
        }
    }
    */
     
  
    
    @PreDestroy
    public void destroy() {
        // loop and finalize all limiters
    }
    
    
}