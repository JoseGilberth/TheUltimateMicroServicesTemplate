
export class UsuariosPublicosFiltroDTO {

  id: string;
  username: string;
  correo: string;
  nombre: string;
  apellido1: string;
  apellido2: string;
  telefonoCelular: string;
  limitRequestInicial: number;
  limitRequestFinal: number;
  timeUnitToken: string;
  timeUnitRequest: string;
  tokenExpirationInicial: number;
  tokenExpirationFinal: number;
  fechaAltaInicial: string;
  fechaAltaFinal: string;
  enabled: boolean | string;
  busqueda: boolean;

  constructor() {
    this.busqueda = false;
  }

}