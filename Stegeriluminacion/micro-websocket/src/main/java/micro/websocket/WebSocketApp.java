package micro.websocket;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@EnableEurekaClient
@SpringBootApplication
public class WebSocketApp extends SpringBootServletInitializer {

	public static void main(String[] args) {
		SpringApplication.run(WebSocketApp.class, args);
	}

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(WebSocketApp.class);
	}

	@GetMapping("/heartbit")
	public ResponseEntity<String> getUser() {
		return new ResponseEntity<String>("IM OK", HttpStatus.OK);
	}

}
