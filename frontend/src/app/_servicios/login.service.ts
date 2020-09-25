import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { JwtHelperService } from '@auth0/angular-jwt';
import { Observable } from 'rxjs';
import { environment } from '../../environments/environment';
import { LoginDTO } from '../_dto/login/Login.Dto';
import { Token } from '../_dto/login/Token.Dto';
import { configuraciones } from '../../environments/configuraciones';




@Injectable({
  providedIn: 'root'
})
export class LoginService {


  private host: string = `${environment.host}`;
  private microServiceBase: string = environment.isMicroserviceOriented ? `${this.host}/${environment.micro_auth.base}` : `${this.host}`;
  private moduleSesionesBase: string = `${this.microServiceBase}/${environment.micro_auth.sesiones.base}`;

  private iniciarSesionUrl: string = `${this.microServiceBase}/${environment.micro_auth.post.iniciarSesion}`;
  private cerrarSesionUrl: string = `${this.moduleSesionesBase}/${environment.micro_auth.sesiones.get.cerrar}`;

  constructor(private http: HttpClient, public jwtHelper: JwtHelperService) { }

  iniciarSesion(loginDTO: LoginDTO): Observable<Token> {
    let headers = new HttpHeaders({
      'Content-Type': 'application/x-www-form-urlencoded; charset=UTF-8',
      'Authorization': "Basic " + configuraciones.auth.basic
    });
    let huella = loginDTO.huella != null ? `&huella=${encodeURIComponent(loginDTO.huella)}` : "";
    let body = `grant_type=password&username=${encodeURIComponent(loginDTO.username)}&password=${encodeURIComponent(loginDTO.password)}${huella}`;
    return this.http.post<Token>(this.iniciarSesionUrl, body, { headers });
  }

  cerrarSesion(): Observable<Token> {
    return this.http.get<Token>(this.cerrarSesionUrl);
  }
 

}
