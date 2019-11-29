package micro.eureka;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import de.codecentric.boot.admin.server.config.EnableAdminServer; 

@EnableAdminServer
@EnableEurekaServer
@EnableDiscoveryClient
@SpringBootApplication
@EntityScan({ "modelo" })
@ComponentScan({ "micro.eureka", "modelo" })
public class EurekaApp extends SpringBootServletInitializer {

	public static void main(String[] args) {
		SpringApplication.run(EurekaApp.class, args);
	}

	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(EurekaApp.class);
	}
 

}
