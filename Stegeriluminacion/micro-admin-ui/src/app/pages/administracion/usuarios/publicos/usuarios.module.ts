import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { AlertModule } from 'ngx-bootstrap/alert';
import { BsDropdownModule } from 'ngx-bootstrap/dropdown';
import { ModalModule } from 'ngx-bootstrap/modal';
import { PaginationModule } from 'ngx-bootstrap/pagination';
import { PopoverModule } from 'ngx-bootstrap/popover';
import { TreeviewModule } from 'ngx-treeview';
import { BuscarUsuariosComponent } from './buscar/buscar.component';
import { CrearUsuarioComponent } from './crear/crear.component';
import { EditarUsuarioComponent } from './editar/editar.component';
import { UsuariosComponent } from './usuarios.component';
import { UsuariosRoutingModule } from './usuarios.routing';

@NgModule({
  imports: [
    CommonModule,
    UsuariosRoutingModule,
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
    // PUBLICOS
    CrearUsuarioComponent,
    EditarUsuarioComponent,
    BuscarUsuariosComponent

  ],
  declarations: [
    // PUBLICOS
    UsuariosComponent,
    CrearUsuarioComponent,
    EditarUsuarioComponent,
    BuscarUsuariosComponent
  ]
})

export class UsuariosModule { }
