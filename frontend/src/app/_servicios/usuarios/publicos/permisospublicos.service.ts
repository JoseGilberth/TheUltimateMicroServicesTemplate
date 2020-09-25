import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from '../../../../environments/environment';
import { Respuesta } from '../../../_dto/_main/Respuesta.Dto';
import { PermisoPublicoDTO } from '../../../_dto/usuarios/PermisoPublico.Dto';

@Injectable({
  providedIn: 'root'
})
export class PermisosPublicosService {

  private host: string = `${environment.host}`;
  private microServiceBase: string = environment.isMicroserviceOriented ? `${this.host}/${environment.micro_usuarios.base}` : `${this.host}`;
  private moduleBase: string = `${this.microServiceBase}/${environment.micro_usuarios.permisos_publico.base}`;

  private filtroURL: string = `${this.moduleBase}`;

  constructor(private http: HttpClient) { }

  obtenerTodos(): Observable<Respuesta<PermisoPublicoDTO[]>> {
    return this.http.get<Respuesta<PermisoPublicoDTO[]>>(`${this.filtroURL}`);
  }


}
