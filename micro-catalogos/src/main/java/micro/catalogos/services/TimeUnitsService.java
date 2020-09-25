package micro.catalogos.services;

import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import dto.main.Respuesta;
import micro.catalogos.interfaces.ITimeUnitsService;
import utils._config.language.Translator;

@Service
public class TimeUnitsService implements ITimeUnitsService {

	Logger logger = LoggerFactory.getLogger(this.getClass());

	@Override
	public Respuesta<String[]> listAll() {
		String[] timeUnits = new String[4];
		timeUnits[0] = TimeUnit.DAYS.name();
		timeUnits[1] = TimeUnit.HOURS.name();
		timeUnits[2] = TimeUnit.MINUTES.name();
		timeUnits[3] = TimeUnit.SECONDS.name();
		return new Respuesta<String[]>(200, timeUnits, Translator.toLocale("catalogos.timeunitsservice.listAll"));
	}

}