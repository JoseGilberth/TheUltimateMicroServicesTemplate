package micro.catalogos.interfaces;

import dto.main.Respuesta;

public interface ITimeUnitsService {

	Respuesta<String[]> listAll();
}
