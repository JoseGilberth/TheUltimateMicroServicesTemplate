
export class SesionesFiltroDTO {
  user_name: string;
  client_id: string;
  busqueda: boolean;

  constructor() {
    this.busqueda = false;
  }

}