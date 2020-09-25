package micro.websocket._config.websocket;

import java.util.Set;

import model.auth.usuarios.administradores.PermisoAdministrador;

public class Permission {

	public static boolean hasPermission(Set<PermisoAdministrador> permisos, String permisoToFind) {
		return permisos.stream().anyMatch(permiso -> permiso.getEtiqueta().equals(permisoToFind));
	}
}
