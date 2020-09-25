import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from '../../environments/environment';
import { Respuesta } from '../_dto/_main/Respuesta.Dto';
import { RequestOfLoginDTO } from '../_dto/login/RequestOfLogin.Dto';

@Injectable({
  providedIn: 'root'
})
export class RequestOfLoginService {

  private host: string = `${environment.host}`;
  private microServiceBase: string = environment.isMicroserviceOriented ? `${this.host}/${environment.micro_auth.base}` : `${this.host}`;
  private moduleBase: string = `${this.microServiceBase}/${environment.micro_auth.request_of_login.base}`;

  constructor(private http: HttpClient) { }

  crear(usuario: string): Observable<Respuesta<RequestOfLoginDTO>> {
    return this.http.post<Respuesta<RequestOfLoginDTO>>(`${this.moduleBase}/${usuario}`, null);
  }

  obtenerUuid(uuid: string): Observable<Respuesta<RequestOfLoginDTO>> {
    return this.http.get<Respuesta<RequestOfLoginDTO>>(`${this.moduleBase}/${uuid}`);
  }

}
