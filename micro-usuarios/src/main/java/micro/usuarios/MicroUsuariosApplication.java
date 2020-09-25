package micro.usuarios;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@EnableEurekaClient
@EnableJpaAuditing
@SpringBootApplication
@EnableFeignClients
@EnableCircuitBreaker
@EntityScan({ "model" })
@ComponentScan({ "micro.usuarios", "excepciones", "interfaces", "utils" })
@EnableJpaRepositories({ "dao" })
public class MicroUsuariosApplication extends SpringBootServletInitializer {

	public static void main(String[] args) {
		SpringApplication.run(MicroUsuariosApplication.class, args);
	}

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(MicroUsuariosApplication.class);
	}

	@GetMapping("/heartbeatt")
	public ResponseEntity<String> getUser() {
		return new ResponseEntity<String>("IM OK", HttpStatus.OK);
	}

}
