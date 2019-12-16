import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { JwtHelperService } from '@auth0/angular-jwt';
import { Observable } from 'rxjs';
import { environment } from '../../../../environments/environment';
import { UsuariosPublicosFiltroDTO } from '../../../_dto/usuarios/UsuariosPublicosFiltroDTO';
import { Respuesta } from '../../../_dto/_main/Respuesta.Dto';
import { UsuariosPublicosDTO } from '../../../_dto/usuarios/UsuariosPublicos.Dto';
import { UsuariosAdminFiltroDTO } from '../../../_dto/usuarios/UsuariosAdminFiltro.Dto';
import { UsuariosAdministradoresDTO } from '../../../_dto/usuarios/UsuariosAdministradores.Dto';

@Injectable({
  providedIn: 'root'
})
export class UsuariosPublicosService {

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

  crear(usuariosAdministradoresDTO: UsuariosAdministradoresDTO): Observable<Respuesta> {
    return this.http.post<Respuesta>(`${this.crearUrl}`, usuariosAdministradoresDTO);
  }

  actualizar(id: number, usuariosAdministradoresDTO: UsuariosAdministradoresDTO): Observable<Respuesta> {
    return this.http.put<Respuesta>(`${this.actualizarUrl}/${id}`, usuariosAdministradoresDTO);
  }

  borrar(id: number): Observable<Respuesta> {
    return this.http.delete<Respuesta>(`${this.borrarUrl}/${id}`);
  }


}
