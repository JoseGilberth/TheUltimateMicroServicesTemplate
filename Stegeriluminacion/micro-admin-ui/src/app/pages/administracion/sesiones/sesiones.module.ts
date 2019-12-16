import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { AlertModule } from 'ngx-bootstrap/alert';
import { BsDropdownModule } from 'ngx-bootstrap/dropdown';
import { ModalModule } from 'ngx-bootstrap/modal';
import { PaginationModule } from 'ngx-bootstrap/pagination';
import { PopoverModule } from 'ngx-bootstrap/popover';
import { TreeviewModule } from 'ngx-treeview';
import { BuscarSesionesComponent } from './buscar/buscar.component';
import { SesionesRoutingModule } from './sesiones.routing';
import { SesionesComponent } from './sesiones.component';

@NgModule({
  imports: [
    CommonModule,
    SesionesRoutingModule,
    FormsModule,
    BsDropdownModule.forRoot(),
    PopoverModule.forRoot(),
    AlertModule.forRoot(),
    PaginationModule.forRoot(),
    ModalModule.forRoot(),
    TreeviewModule.forRoot(),
    ReactiveFormsModule
  ],
  entryComponents: [
    BuscarSesionesComponent
  ],
  declarations: [
    SesionesComponent,
    BuscarSesionesComponent
  ]
})

export class SesionesModule { }
