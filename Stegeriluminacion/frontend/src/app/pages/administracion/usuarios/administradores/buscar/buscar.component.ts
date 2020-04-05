import { HttpErrorResponse } from '@angular/common/http';
import { Component } from '@angular/core';
import { BsModalRef } from 'ngx-bootstrap/modal';
import { Subject } from 'rxjs';
 import { TimeUnitService } from '../../../../../_servicios/catalogos/timeunits.service';
import { UtilComponent } from '../../../../../_shared/util.component';
import { UsuariosAdminFiltroDTO } from '../../../../../_dto/usuarios/UsuariosAdminFiltro.Dto';

@Component({
  templateUrl: 'buscar.component.html'
})
export class BuscarUsuariosComponent {
  usuariosAdminFiltroDTO: UsuariosAdminFiltroDTO;
  onClose: Subject<boolean>;
  isLoading: boolean = false;
  timeUnitsDto: string[] = [];

  constructor(public bsModalRef: BsModalRef
    , private utilComponent: UtilComponent
    , private util: UtilComponent) {
  }

  public ngOnInit(): void {
    this.onClose = new Subject();
  }

  public onConfirm(): void {
    this.usuariosAdminFiltroDTO.busqueda = true;
    this.usuariosAdminFiltroDTO.fechaAltaInicial = this.utilComponent.convertDateToMX(this.usuariosAdminFiltroDTO.fechaAltaInicial);
    this.usuariosAdminFiltroDTO.fechaAltaFinal = this.utilComponent.convertDateToMX(this.usuariosAdminFiltroDTO.fechaAltaFinal);

    this.onClose.next(true);
    this.bsModalRef.hide();
  }

  public onCancel(): void {
    this.onClose.next(false);
    this.bsModalRef.hide();
  }

  limpiarBusqueda() {
    this.util.cleanProperties(this.usuariosAdminFiltroDTO);
  }
 
}
