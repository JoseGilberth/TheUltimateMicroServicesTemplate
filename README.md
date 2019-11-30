# The Ultimate Micro Services Template
Una plantilla de microservicios con multiples librerias integradas - plantilla base

NOTA: esta plantilla está dirigida a java developers con experiencia en desarrollo de aplicaciones mvc utilizando el stack tecnológico de spring. 

## Motivación 

Esta plantilla esta creada con el fin de apoyar a toda la comunidad Java de habla hispana, ayudar a entender como funcionan los microservicios y algunas librerias de monitorización que se implementarón, la plantilla aún sigue en construcción , por lo que solo debe tomarse como una guía de aprendizaje y no una implementación a sistemas productivos.

Espero esta plantilla ayude a muchos programadores que se encuentren en una situación de aprendizaje de microservicios , el compartir y dar creditos a esta plantilla se agradece !

## Metas

- Apoyar a la comunidad de java de habla hispana a entender los microservicios
- Crear una implementación grande y solida con toda la comunidad.
-  Que esta plantilla sea una base para multiples proyectos de microservicios

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

- http://localhost:8601/stegeriluminacion/actuator -- Gateway
- http://localhost:8602/micro-auth/actuator  -- Micro servicio de autenticación
- http://localhost:8603/micro-correos/actuator -- Micro servicio de correos
- http://localhost:8604/micro-usuarios/actuator -- Micro servicio de usuarios
- http://localhost:8605/micro-administracion/actuator -- Micro servicio de administracion
- http://localhost:8606/micro-publico/actuator -- Micro servicio publico
- http://localhost:8607/micro-catalogos/actuator -- Micro servicio de catalogos

  
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



