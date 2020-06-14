import { UsuariosDTO } from "./Usuarios.Dto";
import { MotivoAltaBajaPublico } from "./MotivoAltaBajaPublico.Dto";
import { PermisoPublicoDTO } from "./PermisoPublico.Dto";

export class UsuariosPublicosDTO extends UsuariosDTO {

    telefonoCelular: string;
    permisos: PermisoPublicoDTO[];
    motivosAltaBaja: MotivoAltaBajaPublico[];
    limitRequest: number;
    timeUnit: string;
    tokenExpiration: number;

}