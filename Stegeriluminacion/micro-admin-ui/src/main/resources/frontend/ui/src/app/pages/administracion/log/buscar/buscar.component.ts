import { HttpErrorResponse } from '@angular/common/http';
import { Component } from '@angular/core';
import { BsModalRef } from 'ngx-bootstrap/modal';
import { Subject } from 'rxjs';
import { LogFiltroDTO } from '../../../../_dto/log/LogFiltro.Dto';
import { UtilComponent } from '../../../../_shared/util.component';


@Component({
  templateUrl: 'buscar.component.html'
})
export class BuscarLogComponent {

  logFiltroDTO: LogFiltroDTO;
  onClose: Subject<boolean>;
  isLoading: boolean = false;
  timeUnitsDto: string[] = [];

  constructor(public bsModalRef: BsModalRef, private utilComponent: UtilComponent) {
  }

  public ngOnInit(): void {
    this.onClose = new Subject();
  }

  public onConfirm(): void {
    this.logFiltroDTO.busqueda = true;
    this.logFiltroDTO.fechaAltaInicial = this.utilComponent.convertDateToMX(this.logFiltroDTO.fechaAltaInicial);
    this.logFiltroDTO.fechaAltaFinal = this.utilComponent.convertDateToMX(this.logFiltroDTO.fechaAltaFinal);

    this.onClose.next(true);
    this.bsModalRef.hide();
  }

  public onCancel(): void {
    this.onClose.next(false);
    this.bsModalRef.hide();
  }

  limpiarBusqueda() {
    this.utilComponent.cleanProperties(this.logFiltroDTO);
  }

}
