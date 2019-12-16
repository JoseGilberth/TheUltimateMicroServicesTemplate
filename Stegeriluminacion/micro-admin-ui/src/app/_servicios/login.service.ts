import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { JwtHelperService } from '@auth0/angular-jwt';
import { Observable } from 'rxjs';
import { environment } from '../../environments/environment';
import { LoginDTO } from '../_dto/login/Login.Dto';
import { Token } from '../_dto/login/Token.Dto';




@Injectable({
  providedIn: 'root'
})
export class LoginService {

  private iniciarSesionUrl: string = `${environment.host}/${environment.micro_auth.base}/${environment.micro_auth.post.iniciarSesion}`;
  private cerrarSesionUrl: string = `${environment.host}/${environment.micro_auth.base}/${environment.micro_auth.get.cerrarsesion}`;

  constructor(private http: HttpClient, public jwtHelper: JwtHelperService) { }

  iniciarSesion(loginDTO: LoginDTO): Observable<Token> {
    let headers = new HttpHeaders({
      'Content-Type': 'application/x-www-form-urlencoded; charset=UTF-8',
      'Authorization': "Basic c3RlZ2VyaWx1bWluYXRpb246MTIz"
    });
    let body = `grant_type=password&username=${encodeURIComponent(loginDTO.username)}&password=${encodeURIComponent(loginDTO.password)}`;
    return this.http.post<Token>(this.iniciarSesionUrl, body, { headers });
  }

  
  cerrarSesion(): Observable<Token> { 
     return this.http.get<Token>(this.cerrarSesionUrl);
  }

}
