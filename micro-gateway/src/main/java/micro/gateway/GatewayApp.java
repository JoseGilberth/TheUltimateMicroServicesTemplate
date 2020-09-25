package micro.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@EnableDiscoveryClient
@EnableZuulProxy
@EnableCircuitBreaker
@EnableFeignClients
@SpringBootApplication
@EntityScan({ "model" })
@ComponentScan({ "micro.gateway", "excepciones", "utils" })
@EnableJpaRepositories({ "dao" })
public class GatewayApp extends SpringBootServletInitializer {

	public static void main(String[] args) {
		SpringApplication.run(GatewayApp.class, args);
	}

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(GatewayApp.class);
	}

	@GetMapping("/heartbeat")
	public ResponseEntity<String> heartbit() {
		return new ResponseEntity<String>("IM OK", HttpStatus.OK);
	}

}
