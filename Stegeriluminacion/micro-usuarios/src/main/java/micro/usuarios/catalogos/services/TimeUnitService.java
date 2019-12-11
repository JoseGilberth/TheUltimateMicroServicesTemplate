package micro.usuarios.catalogos.services;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import dto.main.Respuesta;
import micro.usuarios._config.languaje.Translator;

@Service
public class TimeUnitService {

	Logger logger = LoggerFactory.getLogger(TimeUnitService.class);

	public Respuesta<List<TimeUnit>> listAll() {
		Respuesta<List<TimeUnit>> respuesta = new Respuesta<List<TimeUnit>>();
		try {

			List<TimeUnit> lista = new ArrayList<TimeUnit>();
			// lista.add(TimeUnit.NANOSECONDS);
			// lista.add(TimeUnit.MICROSECONDS);
			lista.add(TimeUnit.MILLISECONDS);
			lista.add(TimeUnit.SECONDS);
			lista.add(TimeUnit.MINUTES);
			lista.add(TimeUnit.HOURS);
			// lista.add(TimeUnit.DAYS);

			respuesta.setCodigo(200);
			respuesta.setCodigoHttp(200);
			respuesta.setCuerpo(lista);
			respuesta.setEstado(true);
			respuesta.setMensaje(Translator.toLocale("usuarios.creado"));
			return respuesta;

		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return respuesta;
	}
}
