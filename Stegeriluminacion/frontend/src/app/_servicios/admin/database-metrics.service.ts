import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { JwtHelperService } from '@auth0/angular-jwt';
import { Observable } from 'rxjs';
import { environment } from '../../../environments/environment';
import { Respuesta } from '../../_dto/_main/Respuesta.Dto';


@Injectable({
  providedIn: 'root'
})
export class DatabaseMetricsService {
database
  private metricsURL: string = `${environment.host}/${environment.micro_admin.base}/${environment.micro_admin.database.get.metrics}`;

  constructor(private http: HttpClient, public jwtHelper: JwtHelperService) { }

  dataBaseMetrics(): Observable<Respuesta> {
    return this.http.get<Respuesta>(this.metricsURL);
  }

}
