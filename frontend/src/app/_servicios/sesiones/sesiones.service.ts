import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from '../../../environments/environment';
import { SesionesFiltroDTO } from '../../_dto/sesiones/SesionesFiltro.Dto';
import { Respuesta } from '../../_dto/_main/Respuesta.Dto';
import { SesionesDTO } from '../../_dto/sesiones/Sesiones.Dto';
import { PageableDTO } from '../../_dto/_main/Pageable.Dto';

@Injectable({
  providedIn: 'root'
})
export class SesionesService {

  private host: string = `${environment.host}`;
  private microServiceBase: string = environment.isMicroserviceOriented ? `${this.host}/${environment.micro_auth.base}` : `${this.host}`;
  private moduleBase: string = `${this.microServiceBase}/${environment.micro_auth.sesiones.base}`;

  private filtroURL: string = `${this.moduleBase}/${environment.micro_auth.sesiones.post.filtro}`;
  private deleteURL: string = `${this.moduleBase}/${environment.micro_auth.sesiones.post.token}`;

  constructor(private http: HttpClient) { }

  obtenerTodos(sesionesFiltroDTO: SesionesFiltroDTO, pagina: number = 1, tamano: number = 10): Observable<Respuesta<PageableDTO<SesionesDTO>>> {
    return this.http.post<Respuesta<PageableDTO<SesionesDTO>>>(`${this.filtroURL}?page=${pagina}&size=${tamano}`, sesionesFiltroDTO);
  }

  borrar(token: string): Observable<Respuesta<SesionesDTO>> {
    return this.http.post<Respuesta<SesionesDTO>>(`${this.deleteURL}`, token);
  }


}
