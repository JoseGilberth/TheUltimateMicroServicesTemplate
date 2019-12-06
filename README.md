# The Ultimate Micro Services Template
Una plantilla de microservicios con multiples librerias integradas - plantilla base

NOTA: esta plantilla está dirigida a java developers con experiencia en desarrollo de aplicaciones mvc utilizando el stack tecnológico de spring. 

## Motivación 

Esta plantilla esta creada con el fin de apoyar a toda la comunidad Java de habla hispana, ayudar a entender como funcionan los microservicios y algunas librerias de monitorización que se implementarón, la plantilla aún sigue en construcción , por lo que solo debe tomarse como una guía de aprendizaje y no una implementación a sistemas productivos.

Espero esta plantilla ayude a muchos programadores que se encuentren en una situación de aprendizaje de microservicios , el compartir y dar creditos a esta plantilla se agradece !

## Metas

- Apoyar a la comunidad de java de habla hispana a entender los microservicios
- Crear una implementación grande y solida con toda la comunidad.
- Que esta plantilla sea una base para multiples proyectos de microservicios

## Retos y mejoras

**Integrar Multiresources dentro de un microservicio para poder usar multiples cliente id**

- Tomaremos como ejemplo el recursos del microservicio de autenticacion

<div align="center">
    <img src="images/Archivos de seguridad - retos.png" width="400px"</img> 
</div>

Pero solo se ha configurado un "resourceID" a la vez
```
	@Override
	public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
		resources.resourceId(resourceId);
	}
```
El problema es que se puedan tener multiples configuraciones de recursos "ResourceServerConfiguration"
Por ejemplo tener el cliente  **stegerilumination** con el resourceid: **stegerilumination_resource**
DIgamos este cliente va a controlar todo lo relacionado con swagger y la administracion, 
por lo que se le debe negar el acceso a los endpoints de microservicio, a excepcion de los mencionados anteriormente

<div align="center">
    <img src="images/Archivos de seguridad - retos2.png" width="400px"</img> 
</div>

y a su vez poder añadir un nuevo cliente que pueda hacer peticiones a los endpoints del microservicio pero
no tenga permisos para entrar a los recursos de administración o swagger.

Por ende deberá tener una configuracion de recurso para el solo.

Ese es el problema

**Tener multiples configuraciones de recursos para multiples clientes**
y cada configuración tener reglas de seguridad.

DE momento sabemos que con "hasAnyAuthority" puede solventarse este problema de reglas de seguridad pero este
solo contempla 1 solo cliente y no varios

<div align="center">
    <img src="images/Archivos de seguridad - retos3.png" width="400px"</img> 
</div>


## Estructura del proyecto

El proyecto esta construido con maven , es un proyecto multimodulos , por lo que se tratan de separar algunas capas en proyectos mas pequeños, como la capa de modelo o la capa dao para tene una estuctura mas definida , pero aceptamos recomendaciones.

Dentro de tu proyecto e la carpeta \Stegeriluminacion ejecuta el siguiente comando:
```
mvn clean install
```
Para compilarlo y haga una descarga de todas las librerias del mismo.

Cabe mencionar que el proyecto es una leve **implementación de una tienda en linea** , pero pueden eliminarse elementos que no necesite.
 
Todos los microservicios a excepcion de eureka y zipkin , apuntan al microservicio de 
autorización y al micro servicio de eureka

 
## Librerias implementadas
 - Spring-boot
   - spring-boot-starter-parent - 2.1.0.RELEASE 
   - spring-cloud-dependencies - Greenwich.RELEASE
   - spring-boot-starter-tomcat - provided
   - spring-boot-starter-test
   - spring-boot-starter
   - spring-boot-starter-web
   - spring-cloud-starter-netflix-hystrix
   - spring-cloud-starter-netflix-hystrix-dashboard
   - spring-cloud-starter-openfeign
   - spring-boot-starter-actuator
   - spring-cloud-starter-sleuth
   - spring-cloud-starter-zipkin
   - spring-boot-starter-validation
   - spring-cloud-starter-netflix-eureka-client
   - spring-security-oauth2
   - spring-boot-starter-security
   - spring-security-oauth2-autoconfigure
   - spring-boot-starter-hateoas
   - spring-boot-starter-data-jpa
   - spring-boot-starter-thymeleaf
   - spring-boot-admin-starter-client
   - spring-boot-starter-mail
   - spring-cloud-starter-netflix-eureka-server
   - spring-boot-admin-starter-server
   - spring-boot-admin-server-ui-login
   - spring-cloud-starter-netflix-zuul
   - zipkin-autoconfigure-ui
   - zipkin-autoconfigure-storage-mysql

## Como compilar el proyecto
  - paso 1:  Descarga el proyecto con Git
  ``` 
    git clone https://github.com/JoseGilberth/TheUltimateMicroServicesTemplate 
  ```
  - paso 2:  Ve a la carpeta de descarga y dentro de la carpeta \Stegeriluminacion , ejecuta lo siguiente
  ``` 
    mvn clean install 
  ```
 - paso 3: Crea una base de datos en MYSQL , llamada "stegeriluminacion" , con codificación UTF-8 y utf8_general_ci
 
 - paso 4: Por default si inicias el proyecto tomara el perfil "dev" el cual ya tiene configurado un usuario y un password "root" - "<sin contraseña>" asi que iniciara la creacion de las tablas en la base de datos


## Puertos usados en microservicios

- http://localhost:8600/discovery-server/actuator -- Eureka
- http://localhost:8601/stegeriluminacion/actuator -- Gateway
- http://localhost:8602/micro-auth/actuator  -- Micro servicio de autenticación
- http://localhost:8603/micro-correos/actuator -- Micro servicio de correos
- http://localhost:8604/micro-usuarios/actuator -- Micro servicio de usuarios
- http://localhost:8605/micro-administracion/actuator -- Micro servicio de administracion
- http://localhost:8606/micro-publico/actuator -- Micro servicio publico
- http://localhost:8607/micro-catalogos/actuator -- Micro servicio de catalogos
 
| Micro servicio | Puerto |
| ------------- | ------------- |
| micro-eureka  |   8600  |
| stegeriluminacion  |  8601  |
| micro-auth | 8602  |  
| micro-correos  |  8603  |  
| micro-usuarios | 8604  |  
| micro-administracion  |  8605  |
| micro-publico |  8606  |
| micro-catalogos| 8607  |  

## Caracteristicas especiales de esta plantilla

### Seguridad ###

Dentro del microservicio "Auth" , en la clase **AuthorizationServerConfig** dentro del paquete package micro.auth._config.security; se agrego un certificado para que la generación de token sea más segura, asi que se modifico el Bean "accessTokenConverter()" 

```
	@Bean
	public JwtAccessTokenConverter accessTokenConverter() {

		JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
		try {

			org.springframework.core.io.Resource resource = new ClassPathResource("/auth/stegerilumination.jks");
			char[] storepass = "G1lb3rt0.".toCharArray();
			char[] keypass = "gilberto".toCharArray();
			String alias = "stegerilumination";

			KeyStore store = KeyStore.getInstance("jks");
			store.load(resource.getInputStream(), storepass);
			RSAPrivateCrtKey key = (RSAPrivateCrtKey) store.getKey(alias, keypass);
			RSAPublicKeySpec spec = new RSAPublicKeySpec(key.getModulus(), key.getPublicExponent());
			PublicKey publicKey = KeyFactory.getInstance("RSA").generatePublic(spec);
			converter.setKeyPair(new KeyPair(publicKey, key));

		} catch (Exception ex) {
			ex.printStackTrace();
		}

		String publicKey = null;
		try {
			publicKey = IOUtils.toString(new ClassPathResource("/auth/public.txt").getInputStream());
		} catch (final IOException ex) {
			ex.printStackTrace();
		}
		converter.setVerifierKey(publicKey);
		return converter;
	}
  ```

Los archivos que se usaron para la seguridad del token se encuentran en recursos dentro de la carpeta de recursos.

<div align="center">
    <img src="images/Archivos de Seguridad.png" width="400px"</img> 
</div>
  
- ELIMINA JKS DE CERTIFICADOS SI YA EXISTEN 
 ```
keytool -delete -alias stegerilumination -keystore "path/stegerilumination.jks" -storepass G1lb3rt0. -keypass gilberto 
 ```
 ```
keytool -delete -alias stegerilumination -v -trustcacerts -keystore cacerts.jks -storepass G1lb3rt0. -keypass gilberto
 ```
- GENERAR JKS 
 ```
keytool -genkey -alias stegerilumination -keyalg RSA -keypass gilberto -storepass G1lb3rt0. -keystore "Path/stegerilumination.jks"
 ```
- PUBLIC - PEM , CER , TEXT 
 ```
keytool -export -alias stegerilumination -keystore "path/stegerilumination.jks" -file "path/stegerilumination-public-key.pem" -storepass G1lb3rt0. -keypass gilberto 
 ```
  ```
keytool -export -alias stegerilumination -keystore "path/stegerilumination.jks" -file "path stegerilumination-public-key.cer" -storepass G1lb3rt0. -keypass gilberto 
 ```
```
keytool -list -alias stegerilumination -rfc -keystore "path/stegerilumination.jks" -storepass G1lb3rt0. -keypass gilberto
 ```
- IMPORT JKS A CERTIFICADOS DE CONFIANZA
 ```
keytool -import -alias stegerilumination -v -trustcacerts -file "path stegerilumination-public-key.cer" -keystore cacerts.jks -keypass gilberto -storepass G1lb3rt0.
 ```

Los comandos anteriores nos sirvieron para eliminar , crear y dar de alta un certificados jks,pem,cer etc.. 

para que asi obtengamos al final un token con firma RSA.

<div align="center">
    <img src="images/Archivos de Seguridad2.png" width="400px"</img> 
</div>

#### Configuración a nivel base de datos ####

Tanto la configuración de clientes como los token son guardados a nivel base de datos y no a nivel memoria interna

```
	@Resource(name = "cliente")
	private ClientDetailsService clientes;
 
	@Override
	public void configure(ClientDetailsServiceConfigurer configurer) throws Exception {
		configurer.withClientDetails(clientes);
	}
```

```
	@Bean
	public TokenStore tokenStore() {
		return new JdbcTokenStore(this.dataSource);
	}
```

#### Expiración de token a nivel usuario ####

Con esta forma de generación de token, podremos hacer que cada usuario tenga una expiración de token diferente, ademas de usar **Throttling** en los usuarios publicos, con el uso de **Throttling** podemos tambien definir el limite de peticiones por Tiempo en cada usuario , mas abajo se muestra la configuración.

```
	@Autowired
	public UsuarioPublicoDao usuarioPublicoDao;

	// ADDITIONAL INFO
	public static final String LIMIT = "limit";

	@Override
	public OAuth2AccessToken enhance(OAuth2AccessToken accessToken, OAuth2Authentication authentication) {

		String usuario = authentication.getPrincipal().toString();
		UsuarioPublico usuarioPublico = usuarioPublicoDao.buscarPorUsuario(usuario);
		DefaultOAuth2AccessToken defaultOAuth2AccessToken = ((DefaultOAuth2AccessToken) accessToken);

		if (usuarioPublico != null) {
			int tokenTIme = usuarioPublico.getTokenExpiration() == null ? defaultOAuth2AccessToken.getExpiresIn() : usuarioPublico.getTokenExpiration();
			Calendar now = Calendar.getInstance();
			now.add(Times.converTimeUnitToCalendar(usuarioPublico.getTimeUnit()), tokenTIme);
			defaultOAuth2AccessToken.setExpiration(now.getTime());
			final Map<String, Object> additionalInfo = new HashMap<>();
			additionalInfo.put(LIMIT, usuarioPublico.getLimitRequest());
			defaultOAuth2AccessToken.setAdditionalInformation(additionalInfo);
		}
		return accessToken;
	}
```
Esta configuración nos permite tambien agregar información adicional a nuestro token, que de hecho solo se aplica el Throttling a usuarios publicos

<div align="center">
    <img src="images/Archivos de Seguridad3.png" width="400px"</img> 
</div>


#### Throttling ####

Para la configuración de throttling, se agrego dentro del microservicio de gateway, esta configuración, nos permitira mantener el control de cada uno de los token logeados, la configuración en si esta montada sobre "PreFilter"

```
	@Autowired
	private TokenStore tokenStore;

	@Autowired
	private UsuarioPublicoDao usuarioPublicoDao;

	public boolean apply(RequestContext context, HttpServletRequest request, HttpServletResponse response) {
		String tokenJWT = request.getHeader("authorization");
		UsuarioPublico usuarioPublico = null;
		if (tokenJWT != null) {
			if (tokenJWT.contains("Bearer")) {
				tokenJWT = tokenJWT.replace("Bearer ", "");
				OAuth2AccessToken token = tokenStore.readAccessToken(tokenJWT);
				OAuth2Authentication auth = tokenStore.readAuthentication(token);
				String principal = auth.getPrincipal().toString();
				usuarioPublico = checkIfUserExist(principal);
				if (usuarioPublico == null) {
					usuarioPublico = usuarioPublicoDao.buscarPorUsuario(principal);
				};
			} else if (tokenJWT.contains("Basic")) {
				log.info("PreFilter - ZuulFilter Basic: " + tokenJWT);
			}
		}
		if (usuarioPublico != null) {
			CustomRateLimiter rateLimiter = getRateLimiter(gson.toJson(usuarioPublico), usuarioPublico);
			log.info("limiter getTimePeriod: " + rateLimiter.getTimePeriod());
			log.info("limiter getMaxPermits: " + rateLimiter.getMaxPermits());
			log.info("limiter availablePermits: " + rateLimiter.getSemaphore().availablePermits());
			boolean allowRequest = rateLimiter.tryAcquire();
			if (!allowRequest) {
				return true;
			}
		}
		return false;
	}
```

Bueno esta parte espero mejorarla realmente, ya que es un poco chafa , primero hay que tener en cuenta dos cosas , al menos en esta configuración de plantilla tenemos dos tipos de "authorization" , la primera que es cuando un usuario solicita un token y la siguiente cuando el mismo token es enviado al gateway para posteriormente ser validado por los "Recursos" del microservicio del que se requiere.

- Authorization Basic <-Base64->
- Authorization Bearer <-Token->

Bueno dicho lo anterior lo que se hace es un simple filtro , si el token contiene Basic o Bearer ,
y dependiendo de cual tenga pues procesara el token en caso de ser bearer y dejara pasar la peticion en caso de se basic.

Cuando la peticion contenga bearer entonces decodifica el token , y busca al usuario a travez de el, si encuentra al usuario lo serializo en un json y lo guardo como si fuera una llave, para despues aplicar Throttling a ese usuario y contabilizar sus request que le quedan a ese token , de momento el Throttling no esta bajo base de datos por lo que si llegaras a reiniciar el servidor de gateway entonces reiniciarias los conteos del Throttling.
 
#### Idioma ####

Como es sabido un solo idioma no sera suficiente para grandes proyectos , por eso se decidio incluir multiidioma en esta plantilla , ademas de que no solo tendriamos un buen manejo de multiples idiomas sino tambien tendriamos un buen manejo de todos los mensajes de nuestra plataforma en un solo lugar , hay alguna partes de la plantilla que necesitaran una revisada ya que muchas cosas estan hardcodeadas y hay que implementarles el "Translation"

Para esta fase de idiomas se implementarón 2 funciones principales

- Idioma al enviar excepciones 
- Idioma al validar los modelos de datos

Para la primera configuración es necesario implementar un bean y extender a la clase AcceptHeaderLocaleResolver 
para que todas nuestras peticiones que enviemos con el header: 
- Accept-Language: en 
- Accept-Language: es 
- etc.....

cambien el idioma en automatico

```
	@Override
	public Locale resolveLocale(HttpServletRequest request) {
		String headerLang = request.getHeader(header);
		Locale locale = headerLang == null || headerLang.isEmpty() ? Locale.getDefault()
				: Locale.lookup(Locale.LanguageRange.parse(headerLang), locales());
		return locale;
	}

  
	@Bean
	public ReloadableResourceBundleMessageSource messageSource() {
		ReloadableResourceBundleMessageSource rs = new ReloadableResourceBundleMessageSource();
		rs.setBasenames(baseName);
		rs.setDefaultEncoding(encoding);
		rs.setUseCodeAsDefaultMessage(true);
		return rs;
	}

```

Para esta configuración ahora ser aun poco tediosa , ya que las validaciones en JPA tambien deben modificarse y no es suficiente con la configuración de arriba.

Asi que lo mejor fue implementar directamente nuestro custom y ese custom agregarlo a las propiedades HIBERNATE,
"javax.persistence.validation.factory"

``` 
	// TRADUCCION Y MANEJO DE MENSAJES DESDE EL MODELO CON JPA
	@Bean
	@Lazy
	public HibernatePropertiesCustomizer hibernatePropertiesCustomizer(final Validator validator) {
		ValidatorFactory validatorFactory = Validation.byDefaultProvider().configure()
				.messageInterpolator(new ContextualMessageInterpolator(new ResourceBundleMessageInterpolator(
						new AggregateResourceBundleLocator(Arrays.asList(validationBaseName)))))
				.buildValidatorFactory();
		return new HibernatePropertiesCustomizer() {
			@Override
			public void customize(Map<String, Object> hibernateProperties) {
				hibernateProperties.put("javax.persistence.validation.factory", validatorFactory);
			}
		};
	}
```

Ahora todo esto se hace para que tu puedas tener esto en el modelo de datos y pueda cambiar tambien de idioma:

```
	@Id
	@Column(name = "id", updatable = false, nullable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@NotNull(message = "{usuario.username.notnull}")
	@Length(min = 5, max = 250, message = "{usuario.username.lenght}")
	@Column(length = 255, nullable = false, unique = true)
	private String username;
 
	@JsonIgnore
	@NotNull(message = "{usuario.password.notnull}")
	@Length(min = 5, max = 255, message = "{usuario.password.lenght}")
	@Column(length = 255, nullable = false)
	private String password;
	
	@Transient
	private String repetirPassword;

	@Pattern(regexp = ".+@.+\\..+", message = "{usuario.correo.pattern}")
	@NotNull(message = "{usuario.correo.notnull}")
	@Length(min = 3, max = 250, message = "{usuario.correo.length}")
	@Column(length = 255, nullable = false, unique = true)
	private String correo;
```

Y ahora podemos recibir las validaciones del modelo en el idioma solicitado

<div align="center">
    <img src="images/Archivos de idioma.png" width="500px"></img> 
</div>

<div align="center">
    <img src="images/Archivos de idioma2.png" width="500px"></img> 
</div>







## Urls de monitoreo
Las siguientes url de monitoreo serviran para acceder a algunas caracteristicas implementadas dentro de la plantilla , esto y una vez ejecutandose todos los microservicios (deben estar corriendo al menos , El microservicio micro-Auth , Micro-Gateway , Micro-Eureka  los demas son opcionales)


- EUREKA
  - http://localhost:8600/discovery-server/

- ACTUATOR
  - http://localhost:8601/stegeriluminacion/actuator 
  - http://localhost:8602/micro-auth/actuator 
  - http://localhost:8603/micro-correos/actuator 
  - http://localhost:8604/micro-usuarios/actuator 
  - http://localhost:8605/micro-administracion/actuator 
  - http://localhost:8606/micro-publico/actuator 
  - http://localhost:8607/micro-catalogos/actuator 

  - http://localhost:8601/stegeriluminacion/uaa/actuator
  - http://localhost:8601/stegeriluminacion/micro-auth/actuator
  - http://localhost:8601/stegeriluminacion/micro-correos/actuator
  - http://localhost:8601/stegeriluminacion/micro-usuarios/actuator
  - http://localhost:8601/stegeriluminacion/micro-administracion/actuator
  - http://localhost:8601/stegeriluminacion/micro-publico/actuator
  - http://localhost:8601/stegeriluminacion/micro-catalogos/actuator 


- SWAGGER 
  - http://localhost:8601/stegeriluminacion/swagger-ui.html

- ZIPKIN
  - http://localhost:8609/micro-zipkin/zipkin/

- HYSTRIX
  - http://localhost:8601/stegeriluminacion/hystrix
  - http://localhost:8601/stegeriluminacion/actuator/hystrix.stream

  - http://localhost:8602/micro-auth/hystrix 
  - http://localhost:8602/micro-auth/actuator/hystrix.stream

  - http://localhost:8603/micro-correos/hystrix
  - http://localhost:8603/micro-correos/actuator/hystrix.stream

  - http://localhost:8604/micro-usuarios/hystrix
  - http://localhost:8604/micro-usuarios/actuator/hystrix.stream

  - http://localhost:8605/micro-administracion/hystrix
  - http://localhost:8605/micro-administracion/actuator/hystrix.stream

  - http://localhost:8606/micro-publico/hystrix
  - http://localhost:8606/micro-publico/actuator/hystrix.stream

  - http://localhost:8607/micro-catalogos/hystrix
  - http://localhost:8607/micro-catalogos/actuator/hystrix.stream

- SPRING BOOT ADMIN
  - http://localhost:8600/discovery-server/admin

 
## TODOS LOS APORTES SON BIENVENIDOS !!!



