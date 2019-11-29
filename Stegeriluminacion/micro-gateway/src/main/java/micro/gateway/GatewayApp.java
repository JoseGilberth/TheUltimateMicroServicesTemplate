package micro.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

@RestController
@EnableDiscoveryClient
@EnableZuulProxy
@EnableHystrix
@EnableHystrixDashboard
@EnableCircuitBreaker 
@SpringBootApplication
@EntityScan({"modelo","modelo.auth" })
@ComponentScan({"micro.gateway" , "steger.excepciones"})
@EnableJpaRepositories({ "dao" , "dao.auth" })
public class GatewayApp extends SpringBootServletInitializer {

	public static void main(String[] args) {
		SpringApplication.run(GatewayApp.class, args);
	}

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(GatewayApp.class);
	}

	@GetMapping("/heartbit")
    @HystrixCommand(fallbackMethod = "defaultHeartbit") 
	public ResponseEntity<String> heartbit() {
		return new ResponseEntity<String>("IM OK", HttpStatus.OK);
	}
	
	public ResponseEntity<String> defaultHeartbit() {
		return new ResponseEntity<String>("IM NOT OK", HttpStatus.OK);
	}
	
}
