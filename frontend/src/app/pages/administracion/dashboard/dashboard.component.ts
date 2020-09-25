import { Component, OnInit } from '@angular/core';
import { WebSocketAPI } from '../../../_shared/websocketapi.component';
import { MessageWebSocket } from '../../../_dto/_main/MessageWebSocket.Dto';
import { LogDTO } from '../../../_dto/log/Log.Dto';
import { DatePipe } from '@angular/common';

@Component({
  templateUrl: 'dashboard.component.html',
  providers: [DatePipe]
})
export class DashboardComponent implements OnInit {

  constructor(private webSocketAPI: WebSocketAPI, private datePipe: DatePipe) { }

  // lineChart
  public lineChartData: Array<any> = [{ data: [], model: [], label: 'Status Success' }, { data: [], model: [], label: 'Error' }];
  public lineChartLabels: Array<any> = [];
  public lineChartOptions: any = {
    animation: false,
    responsive: true,
    maintainAspectRatio: false
  };
  public lineChartColours: Array<any> = [
    { // GREEN
      backgroundColor: 'rgb(62, 137, 61, .7)',
      borderColor: 'rgb(57, 111, 56 , .7)',
      pointBackgroundColor: 'rgba(148,159,177,1)',
      pointBorderColor: '#fff',
      pointHoverBackgroundColor: 'rgb(62, 137, 61, 1)',
      pointHoverBorderColor: 'rgba(148,159,177,0.8)'
    },
    { // RED
      backgroundColor: 'rgb(229, 25, 25 , .6)',
      borderColor: 'rgb(183, 36, 36, .6)',
      pointBackgroundColor: '#fff',
      pointBorderColor: '#fff',
      pointHoverBackgroundColor: 'rgb(229, 25, 25 , 1)',
      pointHoverBorderColor: 'rgba(77,83,96,1)'
    }
  ];
  public lineChartLegend = true;
  public lineChartType = 'line';
  logDto: LogDTO;

  ngOnInit(): void {
    let _this = this;
    this.webSocketAPI.loggerTopicChannel()
      .subscribe((messageWebSocket: MessageWebSocket) => {
        let loggerDTO: LogDTO = <LogDTO>JSON.parse(messageWebSocket.mensaje);
        if (loggerDTO.statusCode >= 200 && loggerDTO.statusCode < 300) {
          _this.lineChartData[0].data.push(1);
          _this.lineChartData[0].model.push(loggerDTO);
        }
        if (loggerDTO.statusCode >= 500 && loggerDTO.statusCode < 600) {
          _this.lineChartData[1].data.push(1);
          _this.lineChartData[1].model.push(loggerDTO);
        }
        let horario = _this.datePipe.transform(new Date, 'HH:mm:ss');
        _this.lineChartLabels.push(horario);

        if (_this.lineChartLabels.length >= 20) {
          _this.lineChartLabels.shift();
          _this.lineChartData[0].data.shift();
          _this.lineChartData[1].data.shift();
        }
        _this.logDto = loggerDTO;
      });
  }


  chartClicked(e: any): void {
    if (e.active.length > 0) {
      let datasetIndex = e.active[0]._datasetIndex;
      let dataIndex = e.active[0]._index;
      let loggerDTO = this.lineChartData[datasetIndex].model[dataIndex];
      this.logDto = loggerDTO;
    }
  }

}
