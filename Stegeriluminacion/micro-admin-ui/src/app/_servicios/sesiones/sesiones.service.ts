import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from '../../../environments/environment';
import { SesionesFiltroDTO } from '../../_dto/sesiones/SesionesFiltro.Dto';
import { Respuesta } from '../../_dto/_main/Respuesta.Dto';

@Injectable({
  providedIn: 'root'
})
export class SesionesService {

  private obtenerTodosUrl: string = `${environment.host}/${environment.micro_auth.base}/${environment.micro_auth.post.obtenerSesionesFiltro}`;
  private borrarUrl: string = `${environment.host}/${environment.micro_auth.base}/${environment.micro_auth.post.eliminarToken}`;

  constructor(private http: HttpClient) { }

  obtenerTodos(sesionesFiltroDTO: SesionesFiltroDTO, pagina: number = 1, tamano: number = 10): Observable<Respuesta> {
    return this.http.post<Respuesta>(`${this.obtenerTodosUrl}?page=${pagina}&size=${tamano}`, sesionesFiltroDTO);
  }


  borrar(token: string): Observable<Respuesta> {
    return this.http.post<Respuesta>(`${this.borrarUrl}`, token);
  }


}
