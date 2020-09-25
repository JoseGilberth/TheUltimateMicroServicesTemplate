import { UsuariosDTO } from "./Usuarios.Dto";
import { MotivoAltaBajaPublico } from "./MotivoAltaBajaPublico.Dto";
import { PermisoPublicoDTO } from "./PermisoPublico.Dto";

export class UsuariosPublicosDTO extends UsuariosDTO {

    telefonoCelular: string;
    permisos: PermisoPublicoDTO[];
    motivosAltaBaja: MotivoAltaBajaPublico[];
    limitRequest: number;
    periodRequest: number;
    timeUnitRequest: string;
    timeUnitToken: string;
    tokenExpiration: number;

}