package micro.auth;

import java.security.Principal;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.netflix.hystrix.dashboard.EnableHystrixDashboard;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@EnableHystrix
@EnableHystrixDashboard
@EnableEurekaClient
@SpringBootApplication
@EnableFeignClients()
@EntityScan({ "modelo", "modelo.auth" })
@ComponentScan({ "micro.auth", "steger.excepciones" })
@EnableJpaRepositories({ "dao", "dao.auth" })
public class MicroAuthApplication extends SpringBootServletInitializer {

	public static void main(String[] args) {
		SpringApplication.run(MicroAuthApplication.class, args);
	}

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(MicroAuthApplication.class);
	}

	@GetMapping("/heartbit")
	public ResponseEntity<String> getUser() {
		return new ResponseEntity<String>("IM OK", HttpStatus.OK);
	}

	@GetMapping("/usuario")
	public Principal getUser(Principal usuario) {
		return usuario;
	}

}