import { UsuariosDTO } from "./Usuarios.Dto";
import { PermisoAdministradorDTO } from "./PermisoAdministrador.Dto";
import { MotivoAltaBajaAdministrador } from "./MotivoAltaBajaAdministrador.Dto";

export class UsuariosAdministradoresDTO extends UsuariosDTO {
    permisos: PermisoAdministradorDTO[];
    motivosAltaBaja: MotivoAltaBajaAdministrador[];
}