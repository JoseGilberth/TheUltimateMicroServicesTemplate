package micro.usuarios._config.metrics;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import brave.sampler.Sampler;

@Profile({ "dev" })
@Configuration
public class Metrics {

	@Bean
	public Sampler defaultSampler() {
		return Sampler.ALWAYS_SAMPLE;
	}
}
