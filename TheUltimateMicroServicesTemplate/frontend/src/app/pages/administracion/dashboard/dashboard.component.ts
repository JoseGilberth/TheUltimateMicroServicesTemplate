import { Component, OnInit } from '@angular/core';
import { getStyle, hexToRgba } from '@coreui/coreui/dist/js/coreui-utilities';
import { CustomTooltips } from '@coreui/coreui-plugin-chartjs-custom-tooltips';
import { WebSocketAPI } from '../../../_shared/websocketapi.component';
import { MessageWebSocket } from '../../../_dto/_main/MessageWebSocket.Dto';

@Component({
  templateUrl: 'dashboard.component.html'
})
export class DashboardComponent implements OnInit {

  radioModel: string = 'Month';

  constructor(private webSocketAPI: WebSocketAPI) { }


  ngOnInit(): void {

  }

}
