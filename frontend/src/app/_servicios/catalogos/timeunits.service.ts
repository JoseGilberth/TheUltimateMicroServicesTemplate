import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from '../../../environments/environment';
import { Respuesta } from '../../_dto/_main/Respuesta.Dto';

@Injectable({
  providedIn: 'root'
})
export class TimeUnitService {

  private host: string = `${environment.host}`;
  private microServiceBase: string = environment.isMicroserviceOriented ? `${this.host}/${environment.micro_catalogos.base}` : `${this.host}`;
  private moduleBase: string = `${this.microServiceBase}/${environment.micro_catalogos.catalogos.base}`;
  private timeUnitsURL: string = `${this.moduleBase}/${environment.micro_catalogos.catalogos.timeUnits.base}`;

  constructor(private http: HttpClient) { }

  obtenerTodos(): Observable<Respuesta<string[]>> {
    return this.http.get<Respuesta<string[]>>(`${this.timeUnitsURL}`);
  }

}
