import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from '../../../environments/environment';
import { LogFiltroDTO } from '../../_dto/log/LogFiltro.Dto';
import { Respuesta } from '../../_dto/_main/Respuesta.Dto';
import { PageableDTO } from '../../_dto/_main/Pageable.Dto';
import { LogDTO } from '../../_dto/log/Log.Dto';

@Injectable({
  providedIn: 'root'
})
export class LogService {

  private host: string = `${environment.host}`;
  private microServiceBase: string = environment.isMicroserviceOriented ? `${this.host}/${environment.micro_auth.base}` : `${this.host}`;
  private moduleBase: string = `${this.microServiceBase}/${environment.micro_auth.log.base}`;
  private filtroURL: string = `${this.moduleBase}/${environment.micro_auth.log.post.filtro}`;

  constructor(private http: HttpClient) { }

  obtenerTodos(logFiltroDTO: LogFiltroDTO, pagina: number = 1, tamano: number = 10): Observable<Respuesta<PageableDTO<LogDTO>>> {
    return this.http.post<Respuesta<PageableDTO<LogDTO>>>(`${this.filtroURL}?page=${pagina}&size=${tamano}`, logFiltroDTO);
  }


}
