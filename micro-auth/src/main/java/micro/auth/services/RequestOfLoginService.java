package micro.auth.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import dao.auth.fingerprint.RequestOfLoginDao;
import dto.main.Respuesta;
import interfaces.interfaces.MainCrud;
import micro.auth.interfaces.IRequestOfLoginService;
import model.auth.usuarios.fingerprint.RequestOfLogin;
import utils._config.language.Translator;

@Service
public class RequestOfLoginService extends MainCrud<RequestOfLogin, Long> implements IRequestOfLoginService {

	Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	RequestOfLoginDao requestOfLoginDao;

	@Override
	public Respuesta<RequestOfLogin> findByUuid(String uuid) {
		return new Respuesta<RequestOfLogin>(200, requestOfLoginDao.findByUuid(uuid),
				Translator.toLocale("auth.requestofloginservice.findByUuid"));
	}

}
