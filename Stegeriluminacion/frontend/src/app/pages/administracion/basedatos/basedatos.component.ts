import { HttpErrorResponse } from '@angular/common/http';
import { Component, OnInit } from '@angular/core';
import { MetricsDTO } from '../../../_dto/admin/metrics.Dto';
import { DatabaseMetricsService } from '../../../_servicios/admin/database-metrics.service';

@Component({
  templateUrl: 'basedatos.component.html'
})
export class BaseDatosComponent implements OnInit {

  radioModel: string = 'Month';
  metricsDTO: MetricsDTO;

  constructor(private databaseMetricsService: DatabaseMetricsService) { }

  ngOnInit(): void {
    this.metricsDTO = new MetricsDTO;
    this.databaseMetricsService.dataBaseMetrics()
      .subscribe(resp => {
        this.metricsDTO = resp.cuerpo;
        this.metricsDTO.processList.forEach(processList => {
          console.log("METRICAS: " + JSON.stringify(processList));
        });

      }, (error: HttpErrorResponse) => {
      });
  }


}
