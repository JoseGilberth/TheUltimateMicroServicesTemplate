package micro.auth._config.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.common.exceptions.OAuth2Exception;
import org.springframework.security.oauth2.provider.error.DefaultWebResponseExceptionTranslator;
import org.springframework.security.oauth2.provider.error.WebResponseExceptionTranslator;

@Configuration
public class Oauth2ExceptionTranslatorConfiguration {

	@Bean
	public WebResponseExceptionTranslator<OAuth2Exception> oauth2ResponseExceptionTranslator() {
		return new DefaultWebResponseExceptionTranslator() {

			@Override
			public ResponseEntity<OAuth2Exception> translate(Exception e) throws Exception {
				e.printStackTrace();

				ResponseEntity<OAuth2Exception> responseEntity = super.translate(e);
				OAuth2Exception body = responseEntity.getBody();
				HttpStatus statusCode = responseEntity.getStatusCode();

				body.addAdditionalInformation("codigoHttp", "401");
				body.addAdditionalInformation("codigo", "401");
				body.addAdditionalInformation("cuerpo", body.getMessage());
				body.addAdditionalInformation("mensaje", body.getMessage());
				body.addAdditionalInformation("estado", "false");

				HttpHeaders headers = new HttpHeaders();
				headers.setAll(responseEntity.getHeaders().toSingleValueMap());
				return new ResponseEntity<>(body, headers, statusCode);
			}
		};
	}

}
