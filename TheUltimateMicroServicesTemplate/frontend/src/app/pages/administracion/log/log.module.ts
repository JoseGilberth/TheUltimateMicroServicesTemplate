import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { AlertModule } from 'ngx-bootstrap/alert';
import { BsDropdownModule } from 'ngx-bootstrap/dropdown';
import { ModalModule } from 'ngx-bootstrap/modal';
import { PaginationModule } from 'ngx-bootstrap/pagination';
import { PopoverModule } from 'ngx-bootstrap/popover';
import { TreeviewModule } from 'ngx-treeview';
import { BuscarLogComponent } from './buscar/buscar.component';
import { LogRoutingModule } from './log.routing';
import { LogComponent } from './log.component';

@NgModule({
  imports: [
    CommonModule,
    LogRoutingModule,
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
    BuscarLogComponent
  ],
  declarations: [
    LogComponent,
    BuscarLogComponent
  ]
})

export class LogModule { }
