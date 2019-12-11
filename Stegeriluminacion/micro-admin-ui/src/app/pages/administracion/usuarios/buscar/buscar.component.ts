import { Component, Input } from '@angular/core';
import { BsModalRef } from 'ngx-bootstrap/modal';
import { UsuariosPublicosFiltroDTO } from '../../../../_dto/usuarios/UsuariosPublicosFiltroDTO';
import { Subject } from 'rxjs';

@Component({
  templateUrl: 'buscar.component.html'
})
export class BuscarUsuariosComponent {
  usuariosPublicosFiltroDTO: UsuariosPublicosFiltroDTO;
  onClose: Subject<boolean>;

  constructor(public bsModalRef: BsModalRef) {
  }

  public ngOnInit(): void {
    this.onClose = new Subject();
  }

  public onConfirm(): void {
    this.onClose.next(true);
    this.bsModalRef.hide();
  }

  public onCancel(): void {
    this.onClose.next(false);
    this.bsModalRef.hide();
  }
}
