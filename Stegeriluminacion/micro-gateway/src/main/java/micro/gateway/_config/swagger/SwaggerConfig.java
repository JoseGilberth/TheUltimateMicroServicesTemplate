package micro.gateway._config.swagger;

import static com.google.common.base.Predicates.or;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import com.google.common.base.Predicate;

import springfox.documentation.builders.OAuthBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.Contact;
import springfox.documentation.service.GrantType;
import springfox.documentation.service.ResourceOwnerPasswordCredentialsGrant;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.service.SecurityScheme;
import springfox.documentation.service.VendorExtension;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger.web.InMemorySwaggerResourcesProvider;
import springfox.documentation.swagger.web.SecurityConfiguration;
import springfox.documentation.swagger.web.SecurityConfigurationBuilder;
import springfox.documentation.swagger.web.SwaggerResource;
import springfox.documentation.swagger.web.SwaggerResourcesProvider;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableSwagger2
@Configuration
public class SwaggerConfig {

	@Value("${swagger.config.base-package}")
	private String basePackage;

	@Value("${swagger.config.variables.resource-owner-password-credentials-grant}")
	private String tokenURL;

	@Value("${swagger.config.variables.client-id}")
	private String clientId;

	@Value("${swagger.config.variables.client-secret}")
	private String clientSecret;

	@Value("${swagger.config.variables.app-name}")
	private static String appName;

	@Value("${swagger.config.variables.scope-separator}")
	private String scopeSeparator;

	@Value("${swagger.config.variables.oauth-builder}")
	private String oauthBuilderName;

	@Value("${swagger.config.variables.secured-paths}")
	private String[] securedPaths;

	public static final Contact DEFAULT_CONTACT = new Contact("Indra Company", "https://www.indracompany.com", "jgcolin@indracompany.mx");

    @SuppressWarnings("rawtypes")
	public static final ApiInfo DEFAULT_API_INFO = new ApiInfo("Micro-Gateway",
			"Documentación de API del microservicio " + "Micro-Gateway", "1.0.0", "Url termino de servicio",
			DEFAULT_CONTACT, "Apache 2.0", "http://www.apache.org/licenses/LICENSE-2.0",
			new ArrayList<VendorExtension>());

	@Bean
	public Docket api() {
		return new Docket(DocumentationType.SWAGGER_2).apiInfo(DEFAULT_API_INFO).select()
				.apis(RequestHandlerSelectors.basePackage(basePackage)).build() 
				.securitySchemes(Arrays.asList(securityScheme())).securityContexts(Arrays.asList(securityContext()));
	}

	@Bean
	public SecurityConfiguration security() {
		return SecurityConfigurationBuilder.builder().clientId(clientId).clientSecret(clientSecret).appName(appName)
				.scopeSeparator(scopeSeparator).additionalQueryStringParams(null)
				.useBasicAuthenticationWithAccessCodeGrant(false).build();
	}

	private SecurityScheme securityScheme() {
		GrantType grantType = new ResourceOwnerPasswordCredentialsGrant(tokenURL);
		SecurityScheme oauth = new OAuthBuilder().name(oauthBuilderName).grantTypes(Arrays.asList(grantType)).build();
		return oauth;
	}

	private AuthorizationScope[] scopes() {
		AuthorizationScope[] scopesVar = { new AuthorizationScope("read", "Lectura"), new AuthorizationScope("write", "Escritura") };
		return scopesVar;
	}

	private Predicate<String> securePaths() {
		List<Predicate<String>> predicates = new ArrayList<Predicate<String>>();
		for (int i = 0; i < securedPaths.length; i++) {
			predicates.add(PathSelectors.regex(securedPaths[i]));
		}
		return or(predicates);
	}

	private SecurityContext securityContext() {
		return SecurityContext.builder()
				.securityReferences(Arrays.asList(new SecurityReference(oauthBuilderName, scopes())))
				.forPaths(securePaths()).build();
	}
	
	
    @Primary
    @Bean
    public SwaggerResourcesProvider swaggerResourcesProvider(InMemorySwaggerResourcesProvider defaultResourcesProvider) {
        return () -> { 
            List<SwaggerResource> resources = new ArrayList<>(defaultResourcesProvider.get());
            resources.add(swaggerResource("micro-auth"			, "/uaa/v2/api-docs", "2.0"));
            resources.add(swaggerResource("micro-correos"		, "/micro-correo/v2/api-docs", "2.0"));
            resources.add(swaggerResource("micro-administracion", "/micro-administracion/v2/api-docs", "2.0"));
            resources.add(swaggerResource("micro-publico"		, "/micro-publico/v2/api-docs", "2.0")); 
            resources.add(swaggerResource("micro-catalogos"		, "/micro-catalogos/v2/api-docs", "2.0"));  
            resources.add(swaggerResource("micro-usuarios"		, "/micro-usuarios/v2/api-docs", "2.0"));  
            return resources;
        };
    }

    private SwaggerResource swaggerResource(String name, String location, String version) {
        SwaggerResource swaggerResource = new SwaggerResource();
        swaggerResource.setName(name);
        swaggerResource.setLocation(location);
        swaggerResource.setSwaggerVersion(version);
        return swaggerResource;
    }

}