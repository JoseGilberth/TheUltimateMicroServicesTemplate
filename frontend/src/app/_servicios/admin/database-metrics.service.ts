import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { JwtHelperService } from '@auth0/angular-jwt';
import { Observable } from 'rxjs';
import { environment } from '../../../environments/environment';
import { Respuesta } from '../../_dto/_main/Respuesta.Dto';
import { MetricsDTO } from '../../_dto/admin/metrics.Dto';


@Injectable({
  providedIn: 'root'
})
export class DatabaseMetricsService {

  private host: string = `${environment.host}`;
  private microServiceBase: string = environment.isMicroserviceOriented ? `${this.host}/${environment.micro_auth.base}` : `${this.host}`;
  private moduleBase: string = `${this.microServiceBase}/${environment.micro_admin.database.base}`;
  private metricsURL: string = `${this.moduleBase}/${environment.micro_admin.database.get.metrics}`;

  constructor(private http: HttpClient, public jwtHelper: JwtHelperService) { }

  dataBaseMetrics(): Observable<Respuesta<MetricsDTO>> {
    return this.http.get<Respuesta<MetricsDTO>>(this.metricsURL);
  }

}
