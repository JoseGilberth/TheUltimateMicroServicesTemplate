package micro.auth._config.security.custom;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;

public class CustomTokenService extends DefaultTokenServices {

	private static final Logger logger = LoggerFactory.getLogger(CustomTokenService.class);

	@Override
	public OAuth2AccessToken createAccessToken(OAuth2Authentication authentication) throws AuthenticationException {
		try {
			return super.createAccessToken(authentication);
		} catch (DuplicateKeyException ex) {
			logger.error(ex.getMessage());
			return super.getAccessToken(authentication);
		} catch (Exception ex) {
			logger.error(ex.getMessage());
		}
		return null;
	}

}