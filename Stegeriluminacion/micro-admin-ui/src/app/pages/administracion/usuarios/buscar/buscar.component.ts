import { Component, Input } from '@angular/core';
import { BsModalRef } from 'ngx-bootstrap/modal';
import { UsuariosPublicosFiltroDTO } from '../../../../_dto/usuarios/UsuariosPublicosFiltroDTO';
import { Subject } from 'rxjs';
import { TimeUnitService } from '../../../../_servicios/catalogos/timeunits.service';
import { HttpErrorResponse } from '@angular/common/http';
import { DatePipe, formatDate } from '@angular/common';
import { UtilComponent } from '../../../../_shared/util.component';

@Component({
  templateUrl: 'buscar.component.html'
})
export class BuscarUsuariosComponent {
  usuariosPublicosFiltroDTO: UsuariosPublicosFiltroDTO;
  onClose: Subject<boolean>;
  isLoading: boolean = false;
  timeUnitsDto: string[] = [];

  constructor(public bsModalRef: BsModalRef
    , private timeUnitService: TimeUnitService,private utilComponent: UtilComponent) {
  }

  public ngOnInit(): void {
    this.onClose = new Subject();
    this.listAllTimeUnits();
  }

  public onConfirm(): void {
    this.usuariosPublicosFiltroDTO.busqueda = true; 
    this.usuariosPublicosFiltroDTO.fechaAltaInicial = this.utilComponent.convertDateToMX(this.usuariosPublicosFiltroDTO.fechaAltaInicial);
    this.usuariosPublicosFiltroDTO.fechaAltaFinal = this.utilComponent.convertDateToMX(this.usuariosPublicosFiltroDTO.fechaAltaFinal);

    this.onClose.next(true);
    this.bsModalRef.hide();
  }

  public onCancel(): void {
    this.onClose.next(false);
    this.bsModalRef.hide();
  }


  /*
  ================================================================
                          OBTENER TIME UNITS
  ================================================================
  */
  listAllTimeUnits() {
    this.isLoading = true;
    this.timeUnitsDto = [];
    this.timeUnitService.obtenerTodos()
      .subscribe(resp => {
        this.isLoading = false;
        this.timeUnitsDto = resp.cuerpo;
      }, (error: HttpErrorResponse) => {
        this.isLoading = false;
      });
  }
}
