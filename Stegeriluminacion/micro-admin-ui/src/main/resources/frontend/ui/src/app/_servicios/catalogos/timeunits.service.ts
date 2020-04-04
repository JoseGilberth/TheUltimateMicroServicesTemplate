import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { JwtHelperService } from '@auth0/angular-jwt';
import { Observable } from 'rxjs';
import { Respuesta } from '../../_dto/_main/Respuesta.Dto';
import { environment } from '../../../environments/environment';

@Injectable({
  providedIn: 'root'
})
export class TimeUnitService {

  private obtenerTodosUrl: string = `${environment.host}/${environment.micro_usuarios.base}/${environment.micro_usuarios.catalogos.get.timeUnits}`;

  constructor(private http: HttpClient) { }

  obtenerTodos(): Observable<Respuesta> {
    return this.http.get<Respuesta>(`${this.obtenerTodosUrl}`);
  }

}
