package modelo.auth.log;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.xml.bind.annotation.XmlRootElement;

import org.springframework.data.annotation.CreatedDate;

import lombok.Data;

@Data
@Entity
@XmlRootElement
@Table(indexes = { @Index(columnList = "usuario", name = "index_usuario") })
public class Log implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "id", updatable = false, nullable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
 
	@NotBlank( message="{log.usuario.notblank}" )
	@Column(nullable = false, length = 500)
	private String usuario;
 
	@NotBlank( message="{log.tipoUsuario.notblank}" )
	@Column(nullable = false, length = 500)
	private String tipoUsuario;
 
	@NotBlank( message="{log.apartado.notblank}" )
	@Column(nullable = false, length = 500)
	private String apartado;

	@NotBlank( message="{log.accion.notblank}" ) 
	@Column(nullable = false, length = 500)
	private String accion;
	
	@Lob
	@Column(nullable = true )
	private String response;

	@Lob
	@Column(nullable = true )
	private String request;

	@Column(nullable = true)
	private Integer statusCode;
	
	@Column(nullable = true, length = 500)
	private String remoteAddr;

	@Column(nullable = true, length = 500)
	private Long contentLengthLong;
	
	@Column(nullable = true, length = 500)
	private String contentType;

	@Column(nullable = true, length = 500)
	private String contextPath;

	@Column(nullable = true, length = 500)
	private String method;
	
	@Column(nullable = true, length = 500)
	private String servletPath;

	@Column(nullable = true, length = 500)
	private String requestURI;

	@Column(nullable = true, length = 500)
	private String protocol;

	@Column(nullable = true, length = 500)
	private String characterEncoding;

	@Lob
	@Column(nullable = true)
	private String cookies;

	@Column(nullable = true, length = 500)
	private String dispatcherType;

	@Lob
	@Column(nullable = true )
	private String headerNames;
	
	@Column(nullable = true, length = 1000)
	private String httpServletMapping;

	@Column(nullable = true, length = 1000)
	private String localAddr;
	
	@Column(nullable = true, length = 1000)
	private String locale;

	@Column(nullable = true, length = 1000)
	private String localName;

	@Column(nullable = true, length = 1000)
	private String localPort;

	@Lob
	@Column(nullable = true )
	private String parameterNames;

	@Column(nullable = true, length = 1000)
	private String pathInfo;

	@Column(nullable = true, length = 1000)
	private String queryString;

	@Column(nullable = true, length = 1000)
	private String remoteUser;

	@Column(nullable = true, length = 1000)
	private String requestedSessionId;

	@Column(nullable = true, length = 1000)
	private String scheme;
 
	@CreatedDate
	@Column(updatable = false)
	LocalDateTime fechaAlta;
 
}
