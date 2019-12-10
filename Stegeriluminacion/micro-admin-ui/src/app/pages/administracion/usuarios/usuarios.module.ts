import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';
import { CommonModule } from '@angular/common';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
//import { MaterialModule } from '../app.module';
 
import { UsuariosRoutes } from './usuarios.routing';
import { UsuariosComponent } from './usuarios.component';
//import { CrearComponent } from './crear/crear.component';
import { FiltroComponent } from './filtro/filtro.component';
//import { EditarComponent } from './editar/editar.component';
import { SharedModule } from 'src/app/_shared/shared.module';

@NgModule({
  imports: [
    CommonModule,
    RouterModule.forChild(UsuariosRoutes),
    FormsModule,
    ReactiveFormsModule,
    SharedModule
  ],
  entryComponents: [
    //EditarComponent,
    //CrearComponent,
    FiltroComponent
  ],
  declarations: [
    //CrearComponent,
    UsuariosComponent,
    //EditarComponent,
    FiltroComponent
  ]
})

export class UsuariosModule { }
