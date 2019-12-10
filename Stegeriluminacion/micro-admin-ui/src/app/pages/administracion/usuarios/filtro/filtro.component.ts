import { Component, OnInit, AfterViewInit, Inject } from '@angular/core';

import { configuraciones } from '../../../../../../environments/configuraciones';

import { TranslateService } from '@ngx-translate/core';
import { MatDialogRef, MAT_DIALOG_DATA } from '@angular/material';
import { UsuariosService } from 'src/app/_servicios/principal/usuarios.service';
import { UsuariosFiltroDTO } from 'src/app/_dto/principal/UsuariosFiltroDTO';

declare const $: any;


@Component({
  selector: 'app-data-table-cmp',
  templateUrl: 'filtro.component.html'
})

export class FiltroComponent implements OnInit, AfterViewInit {

  filtro: UsuariosFiltroDTO;
  table: any;

  constructor( translate: TranslateService , @Inject(MAT_DIALOG_DATA) public data: any , private dialogRef: MatDialogRef<FiltroComponent> ) {
  }

  ngOnInit() { 
    this.filtro = <UsuariosFiltroDTO>this.data[0];
    this.table = this.data[1];
  }

  ngAfterViewInit() {
  }

  filtrar() {
    this.filtro.busqueda = true;
    this.cerrar();
    this.table.ajax.reload();
  }


  cerrar() {
    this.dialogRef.close();
  }
  

}
