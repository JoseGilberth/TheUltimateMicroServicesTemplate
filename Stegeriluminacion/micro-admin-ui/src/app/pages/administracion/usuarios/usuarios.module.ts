import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';
import { CommonModule } from '@angular/common';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
//import { MaterialModule } from '../app.module';

//import { CrearComponent } from './crear/crear.component';
//import { FiltroComponent } from './filtro/filtro.component';
//import { EditarComponent } from './editar/editar.component';
import { UsuariosComponent } from './usuarios.component';
import { UsuariosRoutingModule } from './usuarios.routing';
// Dropdowns Component
import { BsDropdownModule } from 'ngx-bootstrap/dropdown';
import { PopoverModule } from 'ngx-bootstrap/popover';
import { AlertModule } from 'ngx-bootstrap/alert';
import { PaginationModule } from 'ngx-bootstrap/pagination';
import { ModalModule } from 'ngx-bootstrap/modal';
import { BuscarUsuariosComponent } from './buscar/buscar.component';

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
    ReactiveFormsModule
  ],
  entryComponents: [
    //EditarComponent,
    //CrearComponent,
    BuscarUsuariosComponent
  ],
  declarations: [
    //CrearComponent,
    UsuariosComponent,
    //EditarComponent,
    BuscarUsuariosComponent
  ]
})

export class UsuariosModule { }
