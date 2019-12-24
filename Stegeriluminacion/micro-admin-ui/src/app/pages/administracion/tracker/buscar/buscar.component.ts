import { HttpErrorResponse } from '@angular/common/http';
import { Component } from '@angular/core';
import { BsModalRef } from 'ngx-bootstrap/modal';
import { Subject } from 'rxjs';
import { TrackerFiltroDTO } from '../../../../_dto/tracker/trackerFiltro.Dto';
import { UtilComponent } from '../../../../_shared/util.component';


@Component({
  templateUrl: 'buscar.component.html'
})
export class BuscarTrackerComponent {

  trackerFiltroDTO: TrackerFiltroDTO;
  onClose: Subject<boolean>;
  isLoading: boolean = false;
  timeUnitsDto: string[] = [];

  constructor(public bsModalRef: BsModalRef, private utilComponent: UtilComponent) {
  }

  public ngOnInit(): void {
    this.onClose = new Subject();
  }

  public onConfirm(): void {
    this.trackerFiltroDTO.busqueda = true;
    this.trackerFiltroDTO.fechaAltaInicial = this.utilComponent.convertDateToMX(this.trackerFiltroDTO.fechaAltaInicial);
    this.trackerFiltroDTO.fechaAltaFinal = this.utilComponent.convertDateToMX(this.trackerFiltroDTO.fechaAltaFinal);

    this.onClose.next(true);
    this.bsModalRef.hide();
  }

  public onCancel(): void {
    this.onClose.next(false);
    this.bsModalRef.hide();
  }

  limpiarBusqueda() {
    this.utilComponent.cleanProperties(this.TrackerFiltroDTO);
  }

}
