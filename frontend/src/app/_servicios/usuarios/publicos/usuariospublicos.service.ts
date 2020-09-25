import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from '../../../../environments/environment';
import { UsuariosPublicosDTO } from '../../../_dto/usuarios/UsuariosPublicos.Dto';
import { UsuariosPublicosFiltroDTO } from '../../../_dto/usuarios/UsuariosPublicosFiltroDTO';
import { Respuesta } from '../../../_dto/_main/Respuesta.Dto';
import { PageableDTO } from '../../../_dto/_main/Pageable.Dto';

@Injectable({
  providedIn: 'root'
})
export class UsuariosPublicosService {

  private host: string = `${environment.host}`;
  private microServiceBase: string = environment.isMicroserviceOriented ? `${this.host}/${environment.micro_usuarios.base}` : `${this.host}`;
  private moduleBase: string = `${this.microServiceBase}/${environment.micro_usuarios.usuarios_publicos.base}`;

  private filtroURL: string = `${this.moduleBase}/${environment.micro_usuarios.usuarios_publicos.post.filtro}`;
  private createURL: string = `${this.moduleBase}`;
  private updateURL: string = `${this.moduleBase}`;
  private deleteURL: string = `${this.moduleBase}`;


  constructor(private http: HttpClient) { }

  obtenerTodos(usuariosFiltroDTO: UsuariosPublicosFiltroDTO, pagina: number = 1, tamano: number = 10): Observable<Respuesta<PageableDTO<UsuariosPublicosDTO>>> {
    return this.http.post<Respuesta<PageableDTO<UsuariosPublicosDTO>>>(`${this.filtroURL}?page=${pagina}&size=${tamano}`, usuariosFiltroDTO);
  }

  obtenerPorUsuario(): Observable<Respuesta<UsuariosPublicosDTO>> {
    return this.http.get<Respuesta<UsuariosPublicosDTO>>(`${this.filtroURL}`);
  }

  crear(usuariosPublicosDTO: UsuariosPublicosDTO): Observable<Respuesta<UsuariosPublicosDTO>> {
    return this.http.post<Respuesta<UsuariosPublicosDTO>>(`${this.createURL}`, usuariosPublicosDTO, { headers: { "accion": "ha creado al usuario " + usuariosPublicosDTO.username } });
  }

  actualizar(id: number, usuariosPublicosDTO: UsuariosPublicosDTO): Observable<Respuesta<UsuariosPublicosDTO>> {
    return this.http.put<Respuesta<UsuariosPublicosDTO>>(`${this.updateURL}/${id}`, usuariosPublicosDTO, { headers: { "accion": "ha actualizado al usuario " + usuariosPublicosDTO.username } });
  }

  borrar(id: number): Observable<Respuesta<UsuariosPublicosDTO>> {
    return this.http.delete<Respuesta<UsuariosPublicosDTO>>(`${this.deleteURL}/${id}`, { headers: { "accion": "ha borrado al usuario " + id } });
  }


}
