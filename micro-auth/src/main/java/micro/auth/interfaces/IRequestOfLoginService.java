package micro.auth.interfaces;

import dto.main.Respuesta;
import interfaces.interfaces.ICrud;
import model.auth.usuarios.fingerprint.RequestOfLogin;

public interface IRequestOfLoginService extends ICrud<RequestOfLogin, Long> {

	Respuesta<RequestOfLogin> findByUuid(String uuid);

}
