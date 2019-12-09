package micro.auth.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import dao.auth.oauth.OauthAccessTokenDao;
import dto.main.Respuesta;
import dto.micro.auth.oauthaccesstoken.FiltroOauthAccessTokenDTO;
import micro.auth._config.languaje.Translator;
import micro.auth.services.interfaces.ACrud;
import micro.auth.services.interfaces.IOauthAccessToken;
import modelo.auth.oauth2.OauthAccessToken;

@Service
public class OauthAccessTokenService extends ACrud<OauthAccessToken> implements IOauthAccessToken {

	Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	OauthAccessTokenDao oauthAccessTokenDao;

	@Override
	public Respuesta<Page<OauthAccessToken>> filtrar(Pageable pageable, FiltroOauthAccessTokenDTO filtroOauthAccessTokenDTO) {
		Respuesta<Page<OauthAccessToken>> respuesta = new Respuesta<Page<OauthAccessToken>>();
		Page<OauthAccessToken> sesiones = oauthAccessTokenDao.obtenerTodosPorPaginacion(pageable, filtroOauthAccessTokenDTO);
		respuesta.setCodigo(200);
		respuesta.setCodigoHttp(200);
		respuesta.setCuerpo(sesiones);
		respuesta.setEstado(true);
		respuesta.setMensaje(Translator.toLocale("proveedor.obtenido"));
		return respuesta;
	}

}
