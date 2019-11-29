package utils;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Component
public class EndPointsControllers {

	static Logger logger = LoggerFactory.getLogger(EndPointsControllers.class);

	@SuppressWarnings("rawtypes")
	public static List<String> readAllEndpoints(String basePackage) throws ClassNotFoundException, IOException {
		List<String> paths = new ArrayList<String>();
		List<Class> clases = UtilClasses.getClasses(basePackage);
		/*
		 * MappingDiscoverer discoverer = new
		 * AnnotationMappingDiscoverer(RequestMapping.class); for (Class clase : clases)
		 * { for (Method method : clase.getMethods()) { String endPoint =
		 * discoverer.getMapping(clase, method); List<String> metodos =
		 * readAllMethods(method); for (String metodo : metodos) { String variables[] =
		 * StringUtils.substringsBetween(endPoint, "${", "}"); if (variables != null) {
		 * for (String variable : variables) { String var = env.getProperty(variable);
		 * logger.info(metodo + "  " + endPoint + "  --  variable: " + var); } } else {
		 * logger.info(metodo + "  " + endPoint); } paths.add(endPoint); } } }
		 */
		return paths;
	}

	public static List<String> readAllMethods(Method method) {
		List<String> methodos = new ArrayList<String>();
		Annotation postMapping = method.getAnnotation(PostMapping.class);
		Annotation getMapping = method.getAnnotation(GetMapping.class);
		Annotation putMapping = method.getAnnotation(PutMapping.class);
		Annotation deleteMapping = method.getAnnotation(DeleteMapping.class);
		Annotation requestMapping = method.getAnnotation(RequestMapping.class);
		if (postMapping != null) {
			methodos.add("POST -- " + method.getName());
			return methodos;
		} else if (getMapping != null) {
			methodos.add("GET -- " + method.getName());
			return methodos;
		} else if (putMapping != null) {
			methodos.add("PUT -- " + method.getName());
			return methodos;
		} else if (deleteMapping != null) {
			methodos.add("DELETE -- " + method.getName());
			return methodos;
		} else if (requestMapping != null) {
			RequestMapping rm = (RequestMapping) requestMapping;
			for (RequestMethod requestMethod : rm.method()) {
				methodos.add("REQUEST -- " + requestMethod.name());
			}
			return methodos;
		} else {

		}
		return methodos;
	}

}
