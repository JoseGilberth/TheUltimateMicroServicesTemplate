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
export class PermisosPublicosService {

  private obtenerTodosUrl: string = `${environment.host}/${environment.micro_usuarios.base}/${environment.micro_usuarios.get.obtenerPermisos}`;

  constructor(private http: HttpClient, public jwtHelper: JwtHelperService) { }

  obtenerTodos(): Observable<Respuesta> {
    return this.http.get<Respuesta>(`${this.obtenerTodosUrl}`);
  }
 

}
