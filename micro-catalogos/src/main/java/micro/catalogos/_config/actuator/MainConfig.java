package micro.catalogos._config.actuator;

import org.springframework.boot.actuate.trace.http.HttpTraceRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Configuration
public class MainConfig {

	//https://www.programmersought.com/article/9988628357/
	
	@Bean
	@Primary
	public HttpTraceRepository htttpTraceRepository() {
		return new CustomTraceRepository();
	}

}