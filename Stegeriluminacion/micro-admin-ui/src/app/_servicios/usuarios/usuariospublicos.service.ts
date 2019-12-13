import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { JwtHelperService } from '@auth0/angular-jwt';
import { Observable } from 'rxjs'; 
import { UsuariosPublicosFiltroDTO } from '../../_dto/usuarios/UsuariosPublicosFiltroDTO';
import { UsuariosPublicosDTO } from '../../_dto/usuarios/UsuariosPublicos.Dto';
import { environment } from '../../../environments/environment';
import { Respuesta } from '../../_dto/_main/Respuesta.Dto';


@Injectable({
  providedIn: 'root'
})
export class UsuariosPublicosService {

  private obtenerTodosUrl: string = `${environment.host}/${environment.micro_usuarios.base}/${environment.micro_usuarios.post.obtenerPorFiltro}`;
  private crearUrl: string = `${environment.host}/${environment.micro_usuarios.base}/${environment.micro_usuarios.post.crearUsuarioPublico}`;

  
  constructor(private http: HttpClient, public jwtHelper: JwtHelperService) { }

  obtenerTodos(usuariosFiltroDTO: UsuariosPublicosFiltroDTO, pagina: number = 1, tamano: number = 10): Observable<Respuesta> {
    return this.http.post<Respuesta>(`${this.obtenerTodosUrl}?page=${pagina}&size=${tamano}`, usuariosFiltroDTO);
  }


  obtenerPorUsuario(): Observable<Respuesta> {
    return this.http.get<Respuesta>(`${this.obtenerTodosUrl}`);
  }

  crear(usuariosPublicosDTO: UsuariosPublicosDTO): Observable<Respuesta> {
    return this.http.post<Respuesta>(`${this.crearUrl}`, usuariosPublicosDTO);
  }

  actualizar(id: number, usuariosPublicosDTO: UsuariosPublicosDTO): Observable<Respuesta> {
    return this.http.put<Respuesta>(`${this.obtenerTodosUrl}/${id}`, usuariosPublicosDTO);
  }

  activar(id: number): Observable<Respuesta> {
    return this.http.get<Respuesta>(`${this.obtenerTodosUrl}/${id}`);
  }
  borrar(id: number): Observable<Respuesta> {
    return this.http.post<Respuesta>(`${this.obtenerTodosUrl}/${id}`, null);
  }


}
