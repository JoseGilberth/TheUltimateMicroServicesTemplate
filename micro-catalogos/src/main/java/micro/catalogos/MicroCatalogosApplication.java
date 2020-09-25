package micro.catalogos;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@EnableEurekaClient
@RestController
@SpringBootApplication
@EntityScan({ "model" })
@ComponentScan({ "micro.catalogos", "excepciones", "utils" })
@EnableJpaRepositories({ "dao" })
public class MicroCatalogosApplication extends SpringBootServletInitializer {

	public static void main(String[] args) {
		SpringApplication.run(MicroCatalogosApplication.class, args);
	}

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(MicroCatalogosApplication.class);
	}

	@GetMapping("/heartbeat")
	public ResponseEntity<String> getUser() {
		return new ResponseEntity<String>("IM OK", HttpStatus.OK);
	}

}