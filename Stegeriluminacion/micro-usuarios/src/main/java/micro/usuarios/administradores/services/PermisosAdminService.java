package micro.usuarios.administradores.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import interfaces.ACrud;
import micro.usuarios.administradores.services.interfaces.IPermisoAdministrador;
import modelo.auth.usuarios.administradores.PermisoAdministrador;

@Service
public class PermisosAdminService extends ACrud<PermisoAdministrador, Long> implements IPermisoAdministrador {

	Logger logger = LoggerFactory.getLogger(PermisosAdminService.class);

}
