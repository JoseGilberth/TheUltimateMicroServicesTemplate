package micro.auth.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import dao.auth.oauth.OauthClientTokenDao;
import dto.main.Respuesta;
import dto.micro.auth.FiltroOauthClientTokenDTO;
import micro.auth._config.languaje.Translator;
import micro.auth.services.interfaces.ACrud;
import micro.auth.services.interfaces.IOauthClientToken;
import modelo.auth.oauth2.OauthClientToken;

@Service
public class OauthClientTokenService extends ACrud<OauthClientToken> implements IOauthClientToken {

	Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	OauthClientTokenDao oauthClientTokenDao;

	@Override
	public Respuesta<Page<OauthClientToken>> filtrar(Pageable pageable, FiltroOauthClientTokenDTO filtroOauthClientTokenDTO) {
		Respuesta<Page<OauthClientToken>> respuesta = new Respuesta<Page<OauthClientToken>>();
		Page<OauthClientToken> sesiones = oauthClientTokenDao.obtenerTodosPorPaginacion(pageable, filtroOauthClientTokenDTO);
		respuesta.setCodigo(200);
		respuesta.setCodigoHttp(200);
		respuesta.setCuerpo(sesiones);
		respuesta.setEstado(true);
		respuesta.setMensaje(Translator.toLocale("proveedor.obtenido"));
		return respuesta;
	}

}
