package excepciones;

import java.util.Iterator;
import java.util.LinkedHashMap;

import javax.persistence.EntityNotFoundException;
import javax.validation.ConstraintViolationException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import dto.errores.Errores;
import dto.main.Respuesta;
import excepciones.nocontroladas.ErrorInternoNoControlado;

@ControllerAdvice
public class ResponseExceptionHandler extends ResponseEntityExceptionHandler {

	Logger logger = LoggerFactory.getLogger(this.getClass());

	@ExceptionHandler(Exception.class)
	public final ResponseEntity<Object> manejarTodasExcepciones(Exception ex, WebRequest request) {

		logger.error("START:: @ExceptionHandler ::START");
		ex.printStackTrace();
		logger.error("END:: @ExceptionHandler ::END");

		Errores errores = new Errores();
		LinkedHashMap<String, String> listaErrores = new LinkedHashMap<String, String>();
		Respuesta<Errores> respuesta = ErrorInternoNoControlado.excepcionDesconocida(errores);

		if (ex instanceof EntityNotFoundException) { // 404 ELEMENTO NO ECONTRADO

			ex = (EntityNotFoundException) ex;
			errores.setMensaje(((EntityNotFoundException) ex).getLocalizedMessage());
			errores.setGeneral("EntityNotFoundException");
			errores.setModelo(ex.getMessage());
			errores.setLista(listaErrores);
			respuesta = ErrorInternoNoControlado.recursoNoEncontrado(errores);

		} else if (ex instanceof ConstraintViolationException) { // VALIDACIÓN DE MODELO

			ex = (ConstraintViolationException) ex;
			errores.setMensaje("Error con el modelo de datos");
			errores.setGeneral("ConstraintViolationException");
			errores.setModelo("Model");

			((ConstraintViolationException) ex).getConstraintViolations().forEach(constraintViolation -> {
				Iterator<javax.validation.Path.Node> iterator = constraintViolation.getPropertyPath().iterator();
				String nombre = "";
				while (iterator.hasNext()) {
					javax.validation.Path.Node nodo = iterator.next();
					nombre = nodo.getName();
				}
				listaErrores.put(nombre, constraintViolation.getMessage().toString());
			});

			errores.setLista(listaErrores);
			respuesta = ErrorInternoNoControlado.errorValidacionModelo(errores);

		} else if (ex instanceof DataIntegrityViolationException) { // VALIDACIÓN DE LA BASE DE DATOS

			ex = (DataIntegrityViolationException) ex;
			errores.setMensaje(((DataIntegrityViolationException) ex).getMostSpecificCause().getMessage());
			errores.setGeneral("DataIntegrityViolationException");
			errores.setModelo(ex.getMessage());
			errores.setLista(listaErrores);
			respuesta = ErrorInternoNoControlado.integridadDeDatos(errores);

		} else if (ex instanceof MethodArgumentNotValidException) { // VALIDACIÓN DE ARGUMENTOS

			ex = (MethodArgumentNotValidException) ex;
			errores.setMensaje(((MethodArgumentNotValidException) ex).getLocalizedMessage());
			errores.setGeneral("MethodArgumentNotValidException");
			errores.setModelo(ex.getMessage());
			((MethodArgumentNotValidException) ex).getBindingResult().getAllErrors().forEach(constraintViolation -> {
				listaErrores.put(constraintViolation.getCode().toString(), constraintViolation.getObjectName());
			});
			errores.setLista(listaErrores);
			respuesta = ErrorInternoNoControlado.errorValidacionModelo(errores);

		} else {

			errores.setMensaje(ex.getMessage());
			errores.setGeneral("UnhandledException");
			errores.setModelo(ex.getMessage());
			respuesta = ErrorInternoNoControlado.excepcionDesconocida(errores);

		}

		return ResponseEntity.status(respuesta.getCodigoHttp()).body(respuesta);
	}

}
