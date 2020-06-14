package steger.excepciones;


import java.util.LinkedHashMap;

import javax.persistence.EntityNotFoundException;
import javax.validation.ConstraintViolationException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory; 
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import dto.errores.Errores;
import dto.main.Respuesta;
import steger.excepciones.nocontroladas.ErrorInternoNoControlado;

@ControllerAdvice
public class ResponseExceptionHandler extends ResponseEntityExceptionHandler  {

	Logger logger = LoggerFactory.getLogger(ResponseExceptionHandler.class);
	
	@ExceptionHandler(Exception.class)
	public final ResponseEntity<Object> manejarTodasExcepciones(Exception ex, WebRequest request){
 
		logger.error("EL ERROR manejarTodasExcepciones: " + ex.getMessage() );
		logger.error("EL ERROR: " + ex.getClass().getName() );
		ex.printStackTrace();
		
		Errores errores = new Errores();
		LinkedHashMap<String,String> listaErrores = new LinkedHashMap<String,String>();
		
		Respuesta<Errores> respuesta = new Respuesta<Errores>();
		respuesta.setCodigo(500);
		respuesta.setCodigoHttp( 500 );
		respuesta.setCuerpo( errores );
		respuesta.setEstado( false );
		respuesta.setMensaje("Error desconocido");
		
		if( ex instanceof EntityNotFoundException) { // 404 ELEMENTO NO ECONTRADO
			
			ex = (EntityNotFoundException) ex;
			errores.setMensaje( ((EntityNotFoundException) ex).getLocalizedMessage()  );
			errores.setGeneral( "EntityNotFoundException" );
			errores.setModelo( ex.getMessage()  );
			errores.setLista( listaErrores );
			respuesta = ErrorInternoNoControlado.recursoNoEncontrado( errores );
			
		} else if( ex instanceof ConstraintViolationException) { //VALIDACIÓN DE MODELO
			
			ex = (ConstraintViolationException) ex;	
			errores.setMensaje( ((ConstraintViolationException) ex).getLocalizedMessage()  );
			errores.setGeneral( "ConstraintViolationException"  );
			((ConstraintViolationException) ex).getConstraintViolations().forEach( constraintViolation -> { 
				listaErrores.put( constraintViolation.getPropertyPath().toString() , constraintViolation.getMessage().toString());
				errores.setModelo( constraintViolation.getRootBeanClass().toString() );
			});
			errores.setLista( listaErrores );
			respuesta = ErrorInternoNoControlado.errorValidacionModelo( errores );
			
		} else if ( ex instanceof DataIntegrityViolationException ){ // VALIDACIÓN DE LA BASE DE DATOS
			
			ex = (DataIntegrityViolationException) ex; 
			errores.setMensaje( ((DataIntegrityViolationException) ex).getMostSpecificCause().getMessage() );
			errores.setGeneral( "DataIntegrityViolationException" );
			errores.setModelo( ex.getMessage()  );
			errores.setLista( listaErrores );
			respuesta = ErrorInternoNoControlado.integridadDeDatos( errores );
			
		}  else if ( ex instanceof MethodArgumentNotValidException ){ // VALIDACIÓN DE LA BASE DE DATOS
			
			ex = (MethodArgumentNotValidException) ex; 
			errores.setMensaje( ((MethodArgumentNotValidException) ex).getLocalizedMessage() );
			errores.setGeneral( "MethodArgumentNotValidException" );
			errores.setModelo( ex.getMessage()  );
			((MethodArgumentNotValidException)ex).getBindingResult().getAllErrors().forEach( constraintViolation -> {
				listaErrores.put( constraintViolation.getCode().toString() , constraintViolation.getObjectName() );
			});
			errores.setLista( listaErrores );
			respuesta = ErrorInternoNoControlado.errorValidacionModelo( errores );
	
		} else {
			
			errores.setMensaje( ex.getMessage() );
			errores.setGeneral( "UnhandledException" );
			errores.setModelo( ex.getMessage()  );
			respuesta = ErrorInternoNoControlado.excepcionDesconocida( errores );
			
		} 
		
		return new ResponseEntity<Object>(respuesta, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	
	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid( MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request ) {

		logger.error("EL ERROR handleMethodArgumentNotValid: " + ex.getMessage() );
		
		Errores errores = new Errores();
		LinkedHashMap<String,String> listaErrores = new LinkedHashMap<String,String>();
		Respuesta<Errores> respuesta = null;	
		errores.setMensaje( null );
		errores.setModelo(  null  );
		errores.setGeneral( "MethodArgumentNotValidException" );

		ex.getBindingResult().getAllErrors().forEach( constraintViolation -> { 
			listaErrores.put( constraintViolation.getCode().toString() , constraintViolation.getDefaultMessage() );
		});
		
		errores.setLista( listaErrores );
		respuesta = ErrorInternoNoControlado.errorValidacionModelo( errores );
		return new ResponseEntity<Object>(respuesta, HttpStatus.BAD_REQUEST);
	}
	
	
}
