import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { JwtHelperService } from '@auth0/angular-jwt';
import { Observable } from 'rxjs';
import { Respuesta } from 'src/app/_dto/Respuesta.Dto';
import { environment } from 'src/environments/environment';
import { LoginDTO } from '../_dto/Login.Dto';
import { Token } from '../_dto/Token.Dto';




@Injectable({
  providedIn: 'root'
})
export class LoginService {

  private iniciarSesionUrl: string = `${environment.host}/${environment.micro_auth.base}/${environment.micro_auth.post.iniciarSesion}`;

  constructor(private http: HttpClient, public jwtHelper: JwtHelperService) { }

  iniciarSesion(loginDTO: LoginDTO): Observable<Token> {
    let headers = new HttpHeaders({
      'Content-Type': 'application/x-www-form-urlencoded; charset=UTF-8',
      'Authorization': "Basic c3RlZ2VyaWx1bWluYXRpb246MTIz"
    });
    let body = `grant_type=password&username=${encodeURIComponent(loginDTO.username)}&password=${encodeURIComponent(loginDTO.password)}`;
    return this.http.post<Token>(this.iniciarSesionUrl, body, { headers });
  }


}
