package micro.auth.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import dao.auth.oauth.OauthClientTokenDao;
import dto.auth.FiltroOauthClientTokenDTO;
import dto.main.Respuesta;
import interfaces.interfaces.MainCrud;
import micro.auth.interfaces.IOauthClientToken;
import model.auth.oauth2.OauthClientToken;
import utils._config.language.Translator;

@Service
public class OauthClientTokenService extends MainCrud<OauthClientToken, Long> implements IOauthClientToken {

	Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	OauthClientTokenDao oauthClientTokenDao;

	@Override
	public Respuesta<Page<OauthClientToken>> filtrar(Pageable pageable,
			FiltroOauthClientTokenDTO filtroOauthClientTokenDTO) {
		Page<OauthClientToken> sesiones = oauthClientTokenDao.filtro(pageable, filtroOauthClientTokenDTO);
		return new Respuesta<Page<OauthClientToken>>(200, sesiones,
				Translator.toLocale("auth.oauthclienttokenservice.filtrar"));
	}

}
