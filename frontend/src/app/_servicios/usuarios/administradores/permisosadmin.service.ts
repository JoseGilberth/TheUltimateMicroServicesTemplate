import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from '../../../../environments/environment';
import { Respuesta } from '../../../_dto/_main/Respuesta.Dto';
import { PermisoAdministradorDTO } from '../../../_dto/usuarios/PermisoAdministrador.Dto';

@Injectable({
  providedIn: 'root'
})
export class PermisosAdminService {

  private host: string = `${environment.host}`;
  private microServiceBase: string = environment.isMicroserviceOriented ? `${this.host}/${environment.micro_usuarios.base}` : `${this.host}`;
  private moduleBase: string = `${this.microServiceBase}/${environment.micro_usuarios.permisos_admin.base}`;

  private filtroURL: string = `${this.moduleBase}`;

  constructor(private http: HttpClient) { }

  obtenerTodos(): Observable<Respuesta<PermisoAdministradorDTO[]>> {
    return this.http.get<Respuesta<PermisoAdministradorDTO[]>>(`${this.filtroURL}`);
  }


}
