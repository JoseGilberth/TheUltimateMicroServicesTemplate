import { UsuariosDTO } from "./Usuarios.Dto";
import { PermisoAdministradorDTO } from "./PermisoAdministrador.Dto";
import { MotivoAltaBajaAdministrador } from "./MotivoAltaBajaAdministrador.Dto";

export class UsuariosAdminDTO extends UsuariosDTO {
    permisos: PermisoAdministradorDTO[];
    motivosAltaBaja: MotivoAltaBajaAdministrador[];
}