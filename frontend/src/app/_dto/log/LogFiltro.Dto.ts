
export class LogFiltroDTO {
    id: number;
    usuario: string;
    tipoUsuario: string;
    apartado: string;
    accion: string;
    fechaAltaInicial: string;
    fechaAltaFinal: string;
    busqueda: boolean;
  
    constructor() {
      this.busqueda = false;
    }

}