import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from '../../../environments/environment';
import { LogFiltroDTO } from '../../_dto/log/LogFiltro.Dto';
import { Respuesta } from '../../_dto/_main/Respuesta.Dto';

@Injectable({
  providedIn: 'root'
})
export class LogService {

  private obtenerTodosUrl: string = `${environment.host}/${environment.micro_auth.base}/${environment.micro_auth.post.obtenerLogFiltro}`;

  constructor(private http: HttpClient) { }

  obtenerTodos(logFiltroDTO: LogFiltroDTO, pagina: number = 1, tamano: number = 10): Observable<Respuesta> {
    return this.http.post<Respuesta>(`${this.obtenerTodosUrl}?page=${pagina}&size=${tamano}`, logFiltroDTO);
  }


}
