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
import { UsuariosAdminComponent } from './usuarios.component';
import { UsuariosRoutingModule } from './usuarios.routing';
import { FingerAdministradoresPrintComponent } from './fingerprint/fingerprint.component';

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
    BuscarUsuariosComponent,
    FingerAdministradoresPrintComponent
  ],
  declarations: [
    // PUBLICOS
    UsuariosAdminComponent,
    CrearUsuarioComponent,
    EditarUsuarioComponent,
    BuscarUsuariosComponent,
    FingerAdministradoresPrintComponent
  ]
})

export class UsuariosModule { }
