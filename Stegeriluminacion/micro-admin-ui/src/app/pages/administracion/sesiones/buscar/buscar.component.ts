import { HttpErrorResponse } from '@angular/common/http';
import { Component } from '@angular/core';
import { BsModalRef } from 'ngx-bootstrap/modal';
import { Subject } from 'rxjs';
import { SesionesFiltroDTO } from '../../../../_dto/sesiones/SesionesFiltro.Dto';
import { UtilComponent } from '../../../../_shared/util.component';


@Component({
  templateUrl: 'buscar.component.html'
})
export class BuscarSesionesComponent {

  sesionesFiltroDTO: SesionesFiltroDTO;
  onClose: Subject<boolean>;
  isLoading: boolean = false; 

  constructor(public bsModalRef: BsModalRef, private utilComponent: UtilComponent) {
  }

  public ngOnInit(): void {
    this.onClose = new Subject();
  }

  public onConfirm(): void {
    this.sesionesFiltroDTO.busqueda = true; 
    this.onClose.next(true);
    this.bsModalRef.hide();
  }

  public onCancel(): void {
    this.onClose.next(false);
    this.bsModalRef.hide();
  }

  limpiarBusqueda() {
    this.utilComponent.cleanProperties(this.sesionesFiltroDTO);
  }

}
