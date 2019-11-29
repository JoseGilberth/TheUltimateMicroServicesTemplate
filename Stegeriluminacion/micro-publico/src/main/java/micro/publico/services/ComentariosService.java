package micro.publico.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.stereotype.Service;

import dao.ComentariosDao;
import dao.auth.usuarios.publicos.UsuarioPublicoDao;
import dto.main.Respuesta;
import dto.micro.publico.comentarios.FiltroComentarioDTO;
import interfaces.ACrud;
import micro.publico._config.languaje.Translator;
import micro.publico.services.interfaces.IComentarios;
import modelo.Comentarios;
import modelo.auth.usuarios.publicos.UsuarioPublico;
import steger.excepciones.controladas.ErrorInternoControlado;

@Service
public class ComentariosService extends ACrud<Comentarios, Long> implements IComentarios{

	Logger logger = LoggerFactory.getLogger( ProductoService.class );
	
	@Autowired
	ComentariosDao comDAO;
	
	@Autowired
	UsuarioPublicoDao usuarioPublicoDao;
	
	
	@Override
	public Respuesta<Page<Comentarios>> obtenerTodos(Pageable pageable) {
		Respuesta<Page<Comentarios>> respuesta = new Respuesta<Page<Comentarios>>();
		try {
			Page<Comentarios> comentarios = comDAO.obtenerTodosPorPaginacion(pageable);
			respuesta.setCodigo( 200 );
			respuesta.setCodigoHttp( 200 );
			respuesta.setCuerpo(comentarios);
			respuesta.setEstado( true );
			respuesta.setMensaje( Translator.toLocale("comentarios.obtenertodos") );
			return respuesta;
		} catch( Exception ex) { 
			logger.error( ex.getMessage() );
			return ErrorInternoControlado.error(ex.getMessage());
		}
		
	}

	@Override
	public Respuesta<Page<Comentarios>> obtenerTodosPersonalizado(Pageable pageable, FiltroComentarioDTO filtroComentarioDTO) {
		Respuesta<Page<Comentarios>> respuesta = new Respuesta<Page<Comentarios>>();
		try {
			Page<Comentarios> comentarios = comDAO.obtenerTodosPersonalizadoPorPaginacion( pageable );
			respuesta.setCodigo( 200 );
			respuesta.setCodigoHttp( 200 );
			respuesta.setCuerpo(comentarios);
			respuesta.setEstado( true );
			respuesta.setMensaje( Translator.toLocale("comentarios.obtenertodos") );
			return respuesta;
		} catch( Exception ex) { 
			logger.error( ex.getMessage() );
			return ErrorInternoControlado.error(ex.getMessage());
		}
		
	}

	@Override
	public Respuesta<Boolean> borrarByUser(OAuth2Authentication auth, Long idComentario) {
		Respuesta<Boolean> respuesta = new Respuesta<Boolean>(); 
		try { 
			String usuario = auth.getPrincipal().toString(); // USUARIO QUE HACE LA PETICION
			
			UsuarioPublico usuarioPublico = usuarioPublicoDao.buscarPorUsuario(usuario); // SE BUSCA EL USUARIO
			Comentarios comentario = comDAO.getOne(idComentario); 
			
			if(comentario.getUsuarioPublico().getId() == usuarioPublico.getId() ) {
				comDAO.delete(comentario); 
				respuesta.setCodigo( 200 );
				respuesta.setCodigoHttp( 200 );
				respuesta.setCuerpo( null );
				respuesta.setEstado( true );
				respuesta.setMensaje( Translator.toLocale("comentario.borrado") );
			} else {
				return ErrorInternoControlado.borradoComentarioNoAutorizado(false);
			}
			
			return respuesta;
		} catch( Exception ex) { 
			logger.error( ex.getMessage() );
			return ErrorInternoControlado.error(ex.getMessage());
		}
	}

	

}
