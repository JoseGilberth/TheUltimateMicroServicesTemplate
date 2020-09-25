package micro.auth.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import dao.auth.oauth.OauthClientDetailsDao;
import dto.auth.FiltroOauthClientDetailsDTO;
import dto.main.Respuesta;
import interfaces.interfaces.MainCrud;
import micro.auth.interfaces.IOauthClientDetails;
import model.auth.oauth2.OauthClientDetails;
import utils._config.language.Translator;

@Service
public class OauthClientDetailsService extends MainCrud<OauthClientDetails, Long> implements IOauthClientDetails {

	Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	OauthClientDetailsDao oauthClientDetailsDao;

	@Override
	public Respuesta<Page<OauthClientDetails>> filtrar(Pageable pageable,
			FiltroOauthClientDetailsDTO filtroOauthClientDetailsDTO) {
		Page<OauthClientDetails> sesiones = oauthClientDetailsDao.filtro(pageable, filtroOauthClientDetailsDTO);
		return new Respuesta<Page<OauthClientDetails>>(200, sesiones,
				Translator.toLocale("auth.oauthclientdetailsservice.filtrar"));
	}

}
