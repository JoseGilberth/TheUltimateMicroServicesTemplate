import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from '../../../../environments/environment';
import { UsuariosAdminDTO } from '../../../_dto/usuarios/UsuariosAdmin.Dto';
import { UsuariosAdminFiltroDTO } from '../../../_dto/usuarios/UsuariosAdminFiltro.Dto';
import { Respuesta } from '../../../_dto/_main/Respuesta.Dto';
import { PageableDTO } from '../../../_dto/_main/Pageable.Dto';

@Injectable({
  providedIn: 'root'
})
export class UsuariosAdminService {

  private host: string = `${environment.host}`;
  private microServiceBase: string = environment.isMicroserviceOriented ? `${this.host}/${environment.micro_usuarios.base}` : `${this.host}`;
  private moduleBase: string = `${this.microServiceBase}/${environment.micro_usuarios.usuarios_admin.base}`;

  private filtroURL: string = `${this.moduleBase}/${environment.micro_usuarios.usuarios_admin.post.filtro}`;
  private createURL: string = `${this.moduleBase}`;
  private updateURL: string = `${this.moduleBase}`;
  private deleteURL: string = `${this.moduleBase}`;


  constructor(private http: HttpClient) { }

  obtenerTodos(usuariosFiltroDTO: UsuariosAdminFiltroDTO, pagina: number = 1, tamano: number = 10): Observable<Respuesta<PageableDTO<UsuariosAdminDTO>>> {
    return this.http.post<Respuesta<PageableDTO<UsuariosAdminDTO>>>(`${this.filtroURL}?page=${pagina}&size=${tamano}`, usuariosFiltroDTO);
  }

  obtenerPorUsuario(): Observable<Respuesta<UsuariosAdminDTO>> {
    return this.http.get<Respuesta<UsuariosAdminDTO>>(`${this.filtroURL}`);
  }

  crear(usuariosAdminDTO: UsuariosAdminDTO): Observable<Respuesta<UsuariosAdminDTO>> {
    return this.http.post<Respuesta<UsuariosAdminDTO>>(`${this.createURL}`, usuariosAdminDTO, { headers: { "accion": "ha creado al usuario " + usuariosAdminDTO.username } });
  }

  actualizar(id: number, usuariosAdminDTO: UsuariosAdminDTO): Observable<Respuesta<UsuariosAdminDTO>> {
    return this.http.put<Respuesta<UsuariosAdminDTO>>(`${this.updateURL}/${id}`, usuariosAdminDTO, { headers: { "accion": "ha actualizado al usuario " + usuariosAdminDTO.username } });
  }

  borrar(id: number): Observable<Respuesta<UsuariosAdminDTO>> {
    return this.http.delete<Respuesta<UsuariosAdminDTO>>(`${this.deleteURL}/${id}`, { headers: { "accion": "ha eliminado al usuario " + id } });
  }


}
