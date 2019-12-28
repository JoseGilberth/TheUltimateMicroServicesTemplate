import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from '../../../../environments/environment';
import { UsuariosAdminDTO } from '../../../_dto/usuarios/UsuariosAdmin.Dto';
import { UsuariosAdminFiltroDTO } from '../../../_dto/usuarios/UsuariosAdminFiltro.Dto';
import { Respuesta } from '../../../_dto/_main/Respuesta.Dto';

@Injectable({
  providedIn: 'root'
})
export class UsuariosAdminService {

  private obtenerTodosUrl: string = `${environment.host}/${environment.micro_usuarios.base}/${environment.micro_usuarios.post.obtenerUsuariosAdminFiltro}`;
  private crearUrl: string = `${environment.host}/${environment.micro_usuarios.base}/${environment.micro_usuarios.post.crearUsuarioAdmin}`;
  private actualizarUrl: string = `${environment.host}/${environment.micro_usuarios.base}/${environment.micro_usuarios.put.actualizarUsuarioAdmin}`;
  private borrarUrl: string = `${environment.host}/${environment.micro_usuarios.base}/${environment.micro_usuarios.delete.borrarUsuarioAdmin}`;

  constructor(private http: HttpClient) { }

  obtenerTodos(usuariosFiltroDTO: UsuariosAdminFiltroDTO, pagina: number = 1, tamano: number = 10): Observable<Respuesta> {
    return this.http.post<Respuesta>(`${this.obtenerTodosUrl}?page=${pagina}&size=${tamano}`, usuariosFiltroDTO);
  }

  obtenerPorUsuario(): Observable<Respuesta> {
    return this.http.get<Respuesta>(`${this.obtenerTodosUrl}`);
  }

  crear(usuariosAdminDTO: UsuariosAdminDTO): Observable<Respuesta> {
    return this.http.post<Respuesta>(`${this.crearUrl}`, usuariosAdminDTO, { headers: { "accion": "ha creado al usuario " + usuariosAdminDTO.username } });
  }

  actualizar(id: number, usuariosAdminDTO: UsuariosAdminDTO): Observable<Respuesta> {
    return this.http.put<Respuesta>(`${this.actualizarUrl}/${id}`, usuariosAdminDTO, { headers: { "accion": "ha actualizado al usuario " + usuariosAdminDTO.username } });
  }

  borrar(id: number): Observable<Respuesta> {
    return this.http.delete<Respuesta>(`${this.borrarUrl}/${id}`, { headers: { "accion": "ha eliminado al usuario " + id } });
  }


}
