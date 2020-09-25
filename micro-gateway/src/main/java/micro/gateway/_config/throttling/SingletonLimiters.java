package micro.gateway._config.throttling;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class SingletonLimiters {

	static SingletonLimiters singletonLimiters = null;

	private Map<String, CustomRateLimiter> limiters = new ConcurrentHashMap<>();

	public static SingletonLimiters Instance() {
		if (singletonLimiters == null) {
			singletonLimiters = new SingletonLimiters();
		}
		return singletonLimiters;
	}

	public Map<String, CustomRateLimiter> getLimiters() {
		return limiters;
	}

	public void setLimiters(Map<String, CustomRateLimiter> limiters) {
		this.limiters = limiters;
	}

}
